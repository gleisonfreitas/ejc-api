package br.com.ejc.repository.edg;

import java.util.List;

import br.com.ejc.repository.projection.EdgResumo;

public interface EdgRepositoryQuery {
	
	public List<EdgResumo> pesquisarPorEjc(Long codigoEjc);

}
