package br.com.ejc.repository.igreja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.model.Igreja;
import br.com.ejc.repository.filter.IgrejaFilter;

public interface IgrejaRepositoryQuery {
	
	public Page<Igreja> pesquisar(IgrejaFilter igrejaFilter, Pageable pageable);
}
