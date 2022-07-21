package br.com.ejc.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.dto.LancamentoEstatistica;
import br.com.ejc.dto.LancamentoResumoTipo;
import br.com.ejc.dto.LancamentoResumoTipoPorDia;
import br.com.ejc.model.Lancamento;
import br.com.ejc.repository.filter.LancamentoFilter;
import br.com.ejc.repository.projection.LancamentoResumo;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public List<LancamentoEstatistica> estatisticaLancamento(Long codigoEjc);
	
	public List<LancamentoResumoTipoPorDia> pesquisarResumoTipoDia(LancamentoFilter lancamentoFilter);
	
	public List<LancamentoResumoTipo> pesquisarResumoTipo(LancamentoFilter lancamentoFilter);

}
