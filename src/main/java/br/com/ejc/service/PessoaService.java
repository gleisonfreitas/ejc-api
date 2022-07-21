package br.com.ejc.service;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.ejc.model.Pessoa;
import br.com.ejc.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;	
	
	public Pessoa salvar(Pessoa pessoa) {
		
		pessoa.getDadosComplementares().setPessoa(pessoa);
		if(pessoa.getHistoricos() != null) {
			pessoa.getHistoricos().forEach(h -> h.setPessoa(pessoa));						
		}
		if(pessoa.getFiliacao() != null) {
			pessoa.getFiliacao().setPessoa(pessoa);			
		}
		if(pessoa.getPadrinho() != null) {
			pessoa.getPadrinho().setPessoa(pessoa);			
		}
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		return pessoaSalva;
		
	}
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		pessoa.getDadosComplementares().setPessoa(pessoaSalva);
		
		if(pessoa.getHistoricos() != null) {
			pessoaSalva.getHistoricos().clear();
			pessoaSalva.getHistoricos().addAll(pessoa.getHistoricos());
			pessoaSalva.getHistoricos().forEach(h -> h.setPessoa(pessoaSalva));
		}
		
		if(pessoa.getFiliacao() != null) {
			pessoa.getFiliacao().setPessoa(pessoaSalva);
		}
		
		if(pessoa.getPadrinho() != null) {
			pessoa.getPadrinho().setPessoa(pessoaSalva);
		}
		
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo", "historicos");
		
		return pessoaRepository.save(pessoaSalva);
		
	}
	
	public Pessoa atualizarTrabalhando(Long codigo, Boolean situacao) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		pessoaSalva.setTrabalhando(situacao);
		
		return pessoaRepository.save(pessoaSalva);
	}
	
	public void atualizarEnvioEmail(List<Pessoa> listaPessoasEmailEnviado, boolean envio) {
		
		for (Pessoa pessoa : listaPessoasEmailEnviado) {
			Pessoa pessoaSalva = pessoaRepository.findOne(pessoa.getCodigo());
			pessoaSalva.setEmailEnviado(envio);
			
			pessoaRepository.save(pessoaSalva);
		}		
	}
}
