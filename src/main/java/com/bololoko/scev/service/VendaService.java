package com.bololoko.scev.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bololoko.scev.model.dto.venda.ItemVendaDTOReq;
import com.bololoko.scev.model.dto.venda.ItemVendaDTORes;
import com.bololoko.scev.model.dto.venda.VendaItemDTOReq;
import com.bololoko.scev.model.dto.venda.VendaItemDTORes;
import com.bololoko.scev.model.entity.ItemVenda;
import com.bololoko.scev.model.entity.ItemVendaPK;
import com.bololoko.scev.model.entity.Produto;
import com.bololoko.scev.model.entity.Venda;
import com.bololoko.scev.model.exception.RecursoNaoEncontradoException;
import com.bololoko.scev.repository.ProdutoRepository;
import com.bololoko.scev.repository.VendaRepository;
import com.bololoko.scev.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VendaService {

	private final VendaRepository vendaRepository;
	private final ProdutoRepository produtoRepository;
	
	
	
	// Funcao de conversao de Venda para vendaDTO
	private VendaItemDTORes convertToDTORes(Venda venda) {
		List<ItemVendaDTORes> itensDTO = venda.getItens().stream()
			.map(item -> new ItemVendaDTORes(
					item.getProduto().getIdProduto(),
					item.getProduto().getNomeProduto(),
					item.getQuantidade(),
					item.getProduto().getPrecoUnidade(),
					item.getProduto().getSabor()
			))
			.collect(Collectors.toList()); // fim construcao da lista de item 
	
	return new VendaItemDTORes(
				venda.getIdVenda(),
				venda.getDataVenda(),
				venda.getTotalVenda(),
				itensDTO
			);
	}
		
		
	
	// Registro de venda
	@Transactional
	public VendaItemDTORes registrarVenda(VendaItemDTOReq vendaDTO) {
		
		// Calculo do total vendido
		BigDecimal totalVendido = vendaDTO.itens().stream()
				.map(ItemVendaDTOReq::getSubtotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// Criacao do objeto de venda
		Venda venda = new Venda();
		venda.setDataVenda(vendaDTO.dataVenda());
		venda.setTotalVenda(totalVendido);

		// Busca os produtos pelo nome
		Set<ItemVenda> itensVendidos = vendaDTO.itens().stream()
				.map(itemDTO -> {
					
					// Padroniza o nome do produto para fazer a busca
					String nomeProdutoTratado = StringUtils.padronizaString(itemDTO.nomeProduto());
					
					
					Produto produto = produtoRepository.findByNomeProduto(nomeProdutoTratado)
							.orElseThrow(() -> new RecursoNaoEncontradoException("O produto com nome '" + itemDTO.nomeProduto() + "' nao foi encontrado."));
					
					// Criacao da chave composta
					ItemVendaPK itemPK = new ItemVendaPK();
					itemPK.setProduto(produto);
					itemPK.setVenda(venda);
					
					// Criacao do objeto ItemVenda
					ItemVenda itemVenda = new ItemVenda();
					itemVenda.setIdItemVenda(itemPK);
					itemVenda.setProduto(produto);
					itemVenda.setVenda(venda);
					itemVenda.setPrecoUnidadeVenda(itemDTO.precoUnitario());
					itemVenda.setQuantidade(itemDTO.quantidade());
					itemVenda.setSaborProduto(itemDTO.sabor());
					
					
					return itemVenda;
				}).collect(Collectors.toSet());
		
		// Adiciona a lista de registro pivo na venda
		venda.setItens(itensVendidos);
		
		// Salva a venda no banco
		Venda vendaSalva = vendaRepository.save(venda);
		
		return convertToDTORes(vendaSalva); 
		
	}
	
	
	// Busca de todas as vendas
	@Transactional(readOnly = true)
	public List<VendaItemDTORes> buscaTodasVendas() {
		
		return vendaRepository.findAllVendasComItemsEProdutos()
					.stream()
					.map(this::convertToDTORes)
					.collect(Collectors.toList());
	}
	
	
	
	// Atualizacao de uma venda 
	@Transactional
	public VendaItemDTORes atualizaVenda(Long id, VendaItemDTOReq vendaDTOReq) {
		Venda vendaExistente = vendaRepository.findByIdVendaComItemEProduto(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("A venda com id: '" + id +  "' nao foi encontrado."));
				
		vendaExistente.setDataVenda(vendaDTOReq.dataVenda());
		
		vendaExistente.getItens().clear();
		
		BigDecimal novoTotal = vendaDTOReq.itens().stream()
				.map(ItemVendaDTOReq::getSubtotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		vendaExistente.setTotalVenda(novoTotal);
		
		for(ItemVendaDTOReq itemDTO : vendaDTOReq.itens()) {
			Produto produto = produtoRepository.findByNomeProduto(itemDTO.nomeProduto())
					.orElseThrow(() -> new RecursoNaoEncontradoException("O produto com nome '" + itemDTO.nomeProduto() + "' nao foi encontrado." ));
			
			// Criacao da chave composta
			ItemVendaPK itemPK = new ItemVendaPK();
			itemPK.setProduto(produto);
			itemPK.setVenda(vendaExistente);
			
			// Criacao do objeto ItemVenda
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setIdItemVenda(itemPK);
			itemVenda.setPrecoUnidadeVenda(itemDTO.precoUnitario());
			itemVenda.setQuantidade(itemDTO.quantidade());
			itemVenda.setSaborProduto(itemDTO.sabor());
			
		}// fim for
		
		
		Venda vendaSalva = vendaRepository.save(vendaExistente);
		return convertToDTORes(vendaSalva);
	}
	
	
	
	// Deletar venda
	@Transactional
	public void deletarVenda (Long id) {
		Venda vendaExistente = vendaRepository.findByIdVendaComItemEProduto(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("A venda com id: '" + id +  "' nao foi encontrado."));
		
		vendaRepository.delete(vendaExistente);
	}
	
}
