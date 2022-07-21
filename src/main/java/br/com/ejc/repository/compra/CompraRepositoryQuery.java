package br.com.ejc.repository.compra;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.model.Compra;
import br.com.ejc.repository.filter.CompraFilter;
import br.com.ejc.repository.projection.CompraResumo;

public interface CompraRepositoryQuery {
	
	public Page<Compra> pesquisar(CompraFilter compraFilter, Pageable pageable);
	
	public Page<CompraResumo> resumir(CompraFilter compraFilter, Pageable pageable);
	
	public List<Compra> gerarRelatorio(Long codigoEjc);

}
