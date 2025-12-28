package com.bololoko.scev.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bololoko.scev.model.dto.compra.CompraDTOCompletaRes;
import com.bololoko.scev.model.dto.compra.CompraMaterialDTOReq;
import com.bololoko.scev.model.dto.compra.CompraSimplesDTORes;
import com.bololoko.scev.model.dto.compra.ItemListaMaterialDTO;
import com.bololoko.scev.model.entity.Compra;
import com.bololoko.scev.model.entity.Fornecedor;
import com.bololoko.scev.model.entity.Material;
import com.bololoko.scev.model.entity.MaterialCompra;
import com.bololoko.scev.model.entity.MaterialCompraPK;
import com.bololoko.scev.model.exception.RecursoNaoEncontradoException;
import com.bololoko.scev.repository.CompraRepository;
import com.bololoko.scev.repository.FornecedorRepository;
import com.bololoko.scev.repository.MaterialRepository;
import com.bololoko.scev.util.StringUtils;

@Service
public class CompraMaterialService {

	@Autowired
	FornecedorRepository fornecedorRepository;
	@Autowired
	CompraRepository compraRepository;
	@Autowired
	MaterialRepository materialRepository;
	
	
	
	/****** Em uma compra eu tenho varios materiais ******/
	
	
	
	// Regista novo material
	@Transactional
	public CompraSimplesDTORes registrarCompra(CompraMaterialDTOReq compraDTOReq) {
		
		// Pesquisa o fornecedor pelo nome
		Fornecedor fornecedor = fornecedorRepository.findByNomeFornecedor(compraDTOReq.getEstabelecimento())
			.orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor '" + compraDTOReq.getEstabelecimento() + "' nao encontrado.")
		);
		
		// Cria o objeto Compra
		Compra compra = new Compra();
		compra.setDataCompra(compraDTOReq.getDataCompra());
		compra.setFornecedor(fornecedor);
		
		
		// Inicia o total da compra
		BigDecimal totalCompra = BigDecimal.ZERO;
		
		// Itera sobre cada item da lista de materiais comprados
		for(ItemListaMaterialDTO itemDTO : compraDTOReq.getItens()) {
			
			// Trata o nome do material
			String nomeMaterialTratado = StringUtils.padronizaString(itemDTO.getNomeMaterial());
			
			// Busca o material pelo nome
			Material material = materialRepository.findByNomeMaterial(nomeMaterialTratado)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Material: '" + itemDTO.getNomeMaterial() + "' nao encontrado.")
			);
			
			
			BigDecimal custoItem = BigDecimal.valueOf(itemDTO.getQtdComprada())
											.multiply(itemDTO.getPrecoMaterial());
			
			totalCompra = totalCompra.add(custoItem);
			
			
			/*********
			 * O controle de estoque esta sendo feito no momento da compra 
			 * Porque e na hora de registrar a compra que lembro o estoque de cada material
			 *********/
			
			
			// Atualiza estoque de material 
			material.setQtdEstoque(itemDTO.getEstoqueAtualMaterial());
			double novoEstoque = (material.getQtdEstoque() + itemDTO.getQtdComprada());
			material.setQtdEstoque(novoEstoque);
			
			// Cria a chave composta
			MaterialCompraPK materialCompraPK = new MaterialCompraPK();
			materialCompraPK.setMaterial(material);
			materialCompraPK.setCompra(compra);
			
			// Cria o objeto pivo MaterialCompra
			MaterialCompra materialCompra = new MaterialCompra();
			materialCompra.setIdMaterialCompra(materialCompraPK);
			materialCompra.setPrecoUnidadeCompra(itemDTO.getPrecoMaterial());
			materialCompra.setQuantidade(itemDTO.getQtdComprada());

			// Adiciona no conjunto de itens o registro "pivo" com as informacoes especificas da compra do material
			compra.getItens().add(materialCompra);
		}
		
		// Adiciona o total da compra
		compra.setTotalCompra(totalCompra);
		
		// Converte a entidade para o DTO de resposta
		Compra compraSalva = compraRepository.save(compra);
		
		CompraSimplesDTORes compraSimples = new CompraSimplesDTORes(
					compraSalva.getIdCompra(),
					compraSalva.getDataCompra(),
					compraSalva.getFornecedor().getNomeFornecedor()
				);
		
		
		return compraSimples;
	}
	
	
	// Busca todas as compras
	/*Este metodo retorna apenas um dto simple para exibir na tabela da tela de formulario*/
	@Transactional(readOnly = true)
	public List<CompraSimplesDTORes> buscaTodasCompras() {
		
		List<CompraSimplesDTORes> listaCompraSimples = compraRepository.findAll().stream()
		.map(item -> new CompraSimplesDTORes(
				item.getIdCompra(),
				item.getDataCompra(),
				item.getFornecedor().getNomeFornecedor()
		))
		.collect(Collectors.toList());
		
		
		return listaCompraSimples;
	}
	
	
	// Busca compra por id
	/*Este metodo retorna um dto completo com os itens da compra*/
	@Transactional(readOnly = true)
	public CompraDTOCompletaRes buscaCompraCompletaPorId(Long idCompra) {
		
		// Busca o item de compra se existir
		Compra compraComItens = compraRepository.findByIdCompraComItens(idCompra)
				.orElseThrow(() -> new RecursoNaoEncontradoException("A compra com o id '" +idCompra+ "' nao foi encontrada."));
		
		List<ItemListaMaterialDTO> listaMateriais = compraComItens.getItens().stream()
				.map(material -> new ItemListaMaterialDTO(
						material.getMaterial().getNomeMaterial(),
						material.getPrecoUnidadeCompra(),
						material.getMaterial().getTipoMedida(),
						material.getQuantidade(),
						material.getMaterial().getQtdEstoque(),
						material.getMaterial().getQtdPorUnidade()
				))
				.collect(Collectors.toList());
	
		// Cria e retorna o DTO de resposta completo
		return new CompraDTOCompletaRes(
				compraComItens.getIdCompra(),
				compraComItens.getDataCompra(),
				compraComItens.getFornecedor().getNomeFornecedor(),
				listaMateriais
		);
	}
	
	
	// Atualiza compra
	@Transactional
	public CompraSimplesDTORes atualizaCompraPorId(Long idCompra, CompraMaterialDTOReq compraDTOReq) {
		
		// Busca a compra existente
		Compra compraExistente = compraRepository.findById(idCompra)
				.orElseThrow(() -> new RecursoNaoEncontradoException("A compra com o id '" +idCompra+ "' nao foi encontrado." ));
		
		String nomeFornecedorPadrao = StringUtils.padronizaString(compraDTOReq.getEstabelecimento());
		
		Fornecedor fornecedorNovo = fornecedorRepository.findByNomeFornecedor(nomeFornecedorPadrao)
				.orElseThrow(() -> new RecursoNaoEncontradoException("O fornecedor com o nome '" +compraDTOReq.getEstabelecimento()+ "' nao foi encontrado."));
		
		
		
		// Começa o processo de atualizacao dos campos
		compraExistente.setDataCompra(compraDTOReq.getDataCompra());
		compraExistente.setFornecedor(fornecedorNovo);
		compraExistente.getItens().clear();
		
		BigDecimal novoTotal = BigDecimal.ZERO;
		
		for (ItemListaMaterialDTO materialDTOReq : compraDTOReq.getItens()) {
				
				Material material = materialRepository.findByNomeMaterial(materialDTOReq.getNomeMaterial())
						.orElseThrow(() -> new RecursoNaoEncontradoException("O material com o nome '" +materialDTOReq.getNomeMaterial()+ "' nao foi encontrado."));
				
				MaterialCompra materialCompraNovo = new MaterialCompra();
				materialCompraNovo.setCompra(compraExistente);
				materialCompraNovo.setMaterial(material);
				materialCompraNovo.setPrecoUnidadeCompra(materialDTOReq.getPrecoMaterial());
				materialCompraNovo.setQuantidade(materialDTOReq.getQtdComprada());
				
				compraExistente.getItens().add(materialCompraNovo);

				BigDecimal custoTotal = BigDecimal.valueOf(materialDTOReq.getQtdComprada())
						.multiply(materialDTOReq.getPrecoMaterial());
				
				novoTotal = novoTotal.add(custoTotal);
				
				material.setQtdEstoque(materialDTOReq.getEstoqueAtualMaterial());
			};
		
		compraExistente.setTotalCompra(novoTotal);
		
		return new CompraSimplesDTORes(
					compraExistente.getIdCompra(),
					compraExistente.getDataCompra(),
					compraExistente.getFornecedor().getNomeFornecedor()
				);
	}
	
	
	//Deleta uma compra
	@Transactional
	public void deletaCompra(Long idCompra) {
		
		if(!compraRepository.existsById(idCompra)) {
			throw new RecursoNaoEncontradoException("A compra com o id '" +idCompra+ "' nao foi encontrado.");
		}
		
		compraRepository.deleteById(idCompra);
	}
}
