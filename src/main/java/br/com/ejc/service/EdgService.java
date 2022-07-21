package br.com.ejc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ejc.model.Edg;
import br.com.ejc.model.Ejc;
import br.com.ejc.repository.EdgRepository;
import br.com.ejc.repository.EjcRepository;
import br.com.ejc.repository.PessoaRepository;

@Service
public class EdgService {

	@Autowired
	private EdgRepository edgRepository;
	
	@Autowired
	private EjcRepository ejcRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Edg salvar(Edg edg) {
		
		Ejc ejc = ejcRepository.findOne(edg.getEjc().getCodigo());
		
		edg.setEjc(ejc);
		edg.setPessoa(pessoaRepository.findOne(edg.getPessoa().getCodigo()));
		
		return edgRepository.save(edg);
	}
}
