package br.com.ejc.repository.patrimonio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.model.Patrimonio;
import br.com.ejc.repository.filter.PatrimonioFilter;
import br.com.ejc.repository.projection.PatrimonioResumo;

public interface PatrimonioRepositoryQuery {
	
	public Page<Patrimonio> pesquisar(PatrimonioFilter patrimonioFilter, Pageable pageable);
	
	public Page<PatrimonioResumo> resumir(PatrimonioFilter patrimonioFilter, Pageable pageable);
	
	public List<Patrimonio> gerarRelatorio(Long codigoIgreja);

}
