package br.com.ejc.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.ejc.model.Circulo;
import br.com.ejc.model.Ejc;
import br.com.ejc.model.Equipe;
import br.com.ejc.model.Pessoa;
import br.com.ejc.repository.CirculoRepository;
import br.com.ejc.repository.EjcRepository;
import br.com.ejc.repository.EquipeRepository;
import br.com.ejc.repository.PessoaRepository;

@Service
public class EjcService {

	@Autowired
	private EjcRepository ejcRepository;
	
	@Autowired
	private CirculoRepository circuloRepository;
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Ejc atualizar(Long codigo, Ejc ejc) {
		
		Ejc ejcSalva = buscarPeloCodigo(codigo);
		
		if(ejcSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(ejc, ejcSalva, "codigo");
		
		return ejcRepository.save(ejcSalva);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Ejc ejcSalva = buscarPeloCodigo(codigo);
		
		if(ejcSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		ejcSalva.setAtivo(ativo);
		ejcRepository.save(ejcSalva);
		
		if (!ativo) {
			atualizarEncontreirosEncontristas(codigo);
		}
		
	}

	private void atualizarEncontreirosEncontristas(Long codigo) {
		List<Circulo> circulos = circuloRepository.findByEjcCodigo(codigo);
		for (Circulo circulo : circulos) {
			
			configurarPessoa(circulo.getCoordenadorUm());
			configurarPessoa(circulo.getCoordenadorDois());
			configurarPessoa(circulo.getCoordenadorTres());
			for (Pessoa pessoa : circulo.getEncontristas()) {
				configurarPessoa(pessoa);
			}
			
			circuloRepository.delete(circulo);
		}
		
		List<Equipe> equipes = equipeRepository.findByEjcCodigo(codigo);
		for (Equipe equipe : equipes) {
			
			configurarPessoa(equipe.getCoordenadorUm());
			configurarPessoa(equipe.getCoordenadorDois());
			if (equipe.getCoordenadorTres() != null) {
				configurarPessoa(equipe.getCoordenadorTres());
			}
			
			for (Pessoa pessoa : equipe.getEncontreiros()) {
				configurarPessoa(pessoa);
			}
			
			equipeRepository.delete(equipe);
		}
	}

	private void configurarPessoa(Pessoa pessoa) {
		pessoa.setEncontreiro(true);
		pessoa.setEncontrista(false);
		pessoa.setEmailEnviado(false);
		pessoa.setTrabalhando(false);
		
		pessoaRepository.save(pessoa);
	}
	
	private Ejc buscarPeloCodigo(Long codigo) {
		Ejc ejcSalva = ejcRepository.findOne(codigo);
		if(ejcSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return ejcSalva;
	}

	public void atualizarLogo(Long codigo, MultipartFile file) throws IOException {
		Ejc ejcSalva = buscarPeloCodigo(codigo);
		ejcSalva.setLogo(file.getBytes());
		ejcRepository.save(ejcSalva);
		
	}

}
