package com.bololoko.scev.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bololoko.scev.model.dto.material.MaterialDTOReq;
import com.bololoko.scev.model.dto.material.MaterialDTORes;
import com.bololoko.scev.model.entity.Material;
import com.bololoko.scev.model.exception.ListaVaziaException;
import com.bololoko.scev.model.exception.RecursoExistenteException;
import com.bololoko.scev.model.exception.RecursoNaoEncontradoException;
import com.bololoko.scev.repository.MaterialRepository;
import com.bololoko.scev.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MaterialService {
	
	private final MaterialRepository materialRepository;
	
	
	// Cadastra novo material com nome tratado
	@Transactional
	public MaterialDTORes cadastraMaterial(MaterialDTOReq materialDTOReq) {
		
		// Trata o nome para cadastrar
		String nomeMaterialTratado = StringUtils.padronizaString(materialDTOReq.nomeMaterial());
		
		if(materialRepository.existsByNomeMaterial(nomeMaterialTratado)) { 
			throw new RecursoExistenteException("O material com o nome '" +materialDTOReq.nomeMaterial()+ "' ja existe.");
		}
		
		
		// Cria um novo Material
		Material novoMaterial = new Material();
		novoMaterial.setNomeMaterial(nomeMaterialTratado);
		novoMaterial.setTipoMedida(materialDTOReq.tipoMedida());
		novoMaterial.setQtdEstoque(materialDTOReq.qtdEstoque());
		novoMaterial.setQtdPorUnidade(materialDTOReq.qtdPorUnidade());
		novoMaterial.setPrecoPago(materialDTOReq.precoPago());
		
		
		// Salva o Material criado
		Material materialSalvo = materialRepository.save(novoMaterial);
		
		return new MaterialDTORes(
					materialSalvo.getIdMaterial(),
					materialSalvo.getNomeMaterial(),
					materialSalvo.getTipoMedida(),
					materialSalvo.getQtdEstoque(),
					materialSalvo.getQtdPorUnidade(),
					materialSalvo.getPrecoPago()
				);
	}
	
	
	// Busca todos materiais 
	@Transactional
	public List<MaterialDTORes> buscaTodosMateriais() {
		
		// Busca todos os materiais e converte para DTO de resposta
		List<MaterialDTORes> listaMateriais = materialRepository.findAll().stream()
				.map(material -> new MaterialDTORes(
							material.getIdMaterial(),
							material.getNomeMaterial(),
							material.getTipoMedida(),
							material.getQtdPorUnidade(),
							material.getQtdEstoque(),
							material.getPrecoPago()
						)).collect(Collectors.toList());
		
		// Verifica se existe materiais cadastrados
		if(listaMateriais.size() < 1 ) {
			throw new ListaVaziaException("Nao existe materiais cadastrados. Por favor cadastre algum para poder comprar.");
		}
		
		
		return listaMateriais;
	}
	
	
	
	// Busca material por id
	@Transactional
	public MaterialDTORes buscaMaterialPorId(Long idMaterial) {
		
		// Busca e converte o material para DTO de resposta
		MaterialDTORes materialDTORes = materialRepository.findById(idMaterial)
				.map(materialBanco -> new MaterialDTORes(
						materialBanco.getIdMaterial(),
						materialBanco.getNomeMaterial(),
						materialBanco.getTipoMedida(),
						materialBanco.getQtdPorUnidade(),
						materialBanco.getQtdEstoque(),
						materialBanco.getPrecoPago()
				))
				.orElseThrow(
					() -> new RecursoNaoEncontradoException("O material com o id '" +idMaterial+ "' nao foi encontrado.")
				);
		
		return materialDTORes;
	}
	
	
	
	// Atualiza o material por id
	@Transactional
	public MaterialDTORes atualizaMaterialPorId(Long idMaterial, MaterialDTOReq materialDTO) {
		
		// busca o material
		Material materialExistente = materialRepository.findById(idMaterial)
				.orElseThrow(
					() -> new RecursoNaoEncontradoException("O material com o id '" +idMaterial+ "' nao foi encontrado.")
				);
		
		
		// comeca a atualizacao
		materialExistente.setNomeMaterial(materialDTO.nomeMaterial());
		materialExistente.setTipoMedida(materialDTO.tipoMedida());
		materialExistente.setQtdPorUnidade(materialDTO.qtdPorUnidade());
		materialExistente.setQtdEstoque(materialDTO.qtdEstoque());
		materialExistente.setPrecoPago(materialDTO.precoPago());
		
		
		// Salva e retorna
		Material materialSalvo = materialRepository.save(materialExistente);
		
		return new MaterialDTORes(
					materialSalvo.getIdMaterial(),
					materialSalvo.getNomeMaterial(),
					materialSalvo.getTipoMedida(),
					materialSalvo.getQtdPorUnidade(),
					materialSalvo.getQtdEstoque(),
					materialSalvo.getPrecoPago()
				);
	}
	
	
	
	// Delete um material
	@Transactional
	public void deletaMaterial(Long idMaterial) {
		
		// Verifica se existe
		if(!materialRepository.existsById(idMaterial)) {
			throw new RecursoNaoEncontradoException("O material com o id '" +idMaterial+ "' nao existe.");
		}
		
		
		materialRepository.deleteById(idMaterial);
		
	}
	
	
}
