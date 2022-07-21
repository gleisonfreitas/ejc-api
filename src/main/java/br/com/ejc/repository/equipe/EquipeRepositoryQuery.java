package br.com.ejc.repository.equipe;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.dto.Agenda;
import br.com.ejc.dto.Aniversariante;
import br.com.ejc.dto.CamisasPorEquipe;
import br.com.ejc.dto.EquipeEstatisticaPorPessoa;
import br.com.ejc.dto.Etiqueta;
import br.com.ejc.model.Equipe;
import br.com.ejc.repository.filter.EquipeFilter;
import br.com.ejc.repository.projection.EquipeResumo;

public interface EquipeRepositoryQuery {
	
	public Page<Equipe> pesquisar(EquipeFilter equipeFilter, Pageable pageable);
	
	public Page<EquipeResumo> resumir(EquipeFilter equipeFilter, Pageable pageable);
	
	public List<EquipeEstatisticaPorPessoa> porPessoa(Long codigoEjc);
	
	public List<Agenda> pesquisarAgenda(EquipeFilter filter);
	
	public List<CamisasPorEquipe> listarCamisasPorEquipe(Long codigoEjc);
	
	public List<Aniversariante> listarAniversariantes(Long codigoEjc);
	
	public List<Etiqueta> listarEtiquetas(Long codigoEjc);
}
