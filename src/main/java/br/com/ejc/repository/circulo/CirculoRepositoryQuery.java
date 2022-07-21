package br.com.ejc.repository.circulo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.dto.Agenda;
import br.com.ejc.dto.Aniversariante;
import br.com.ejc.dto.CamisasPorEquipe;
import br.com.ejc.dto.CirculoEstatisticaPorPessoa;
import br.com.ejc.dto.Etiqueta;
import br.com.ejc.model.Circulo;
import br.com.ejc.repository.filter.CirculoFilter;
import br.com.ejc.repository.projection.CirculoResumo;

public interface CirculoRepositoryQuery {
	
	public Page<Circulo> pesquisar(CirculoFilter circuloFilter, Pageable pageable);
	
	public Page<CirculoResumo> resumir(CirculoFilter circuloFilter, Pageable pageable);
	
	public List<CirculoEstatisticaPorPessoa> porPessoa(Long codigoEjc);
	
	public List<Agenda> pesquisarAgenda(CirculoFilter filter);
	
	public List<CamisasPorEquipe> listarCamisasPorCirculo(Long codigoEjc);
	
	public List<Aniversariante> listarAniversariantes(Long codigoEjc);
	
	public List<Etiqueta> listarEtiquetas(Long codigoEjc);

}
