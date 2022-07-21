package br.com.ejc.repository.ejc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.model.Ejc;
import br.com.ejc.repository.filter.EjcFilter;
import br.com.ejc.repository.projection.EjcResumo;

public interface EjcRepositoryQuery {
	
	public Page<Ejc> pesquisar(EjcFilter ejcFilter, Pageable pageable);
	
	public Page<EjcResumo> resumir(EjcFilter ejcFilter, Pageable pageable);
	
	public List<EjcResumo> listarResumoAtivos();

}
