package br.com.ejc.repository.circulo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.ejc.comparator.PessoaNomeGuerraComparator;
import br.com.ejc.dto.Agenda;
import br.com.ejc.dto.Aniversariante;
import br.com.ejc.dto.CamisasPorEquipe;
import br.com.ejc.dto.CirculoEstatisticaPorPessoa;
import br.com.ejc.dto.Etiqueta;
import br.com.ejc.model.Circulo;
import br.com.ejc.model.Circulo_;
import br.com.ejc.model.Ejc_;
import br.com.ejc.model.Pessoa;
import br.com.ejc.model.Pessoa_;
import br.com.ejc.repository.filter.CirculoFilter;
import br.com.ejc.repository.projection.CirculoResumo;

public class CirculoRepositoryImpl implements CirculoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Etiqueta> listarEtiquetas(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Circulo> criteriaQuery = builder.createQuery(Circulo.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		criteriaQuery.where(builder.equal(root.get(Circulo_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Circulo_.cor));
		
		TypedQuery<Circulo> query = manager.createQuery(criteriaQuery);
		
		List<Circulo> circulos = query.getResultList();
		
		List<Etiqueta> lista = new ArrayList<>();
		
		Etiqueta etiqueta = new Etiqueta();
		
		for (Circulo circulo : circulos) {

			if(!etiqueta.isCompleto()) {
				if(etiqueta.getCirculoUm() == null) {
					etiqueta.setCirculoUm(circulo.getCor().getCor());
					etiqueta.setEncontristaUm(circulo.getCoordenadorUm().getNomeGuerra().trim().toUpperCase());
				}
				if(etiqueta.getCirculoDois() == null) {
					etiqueta.setCirculoDois(circulo.getCor().getCor());
					etiqueta.setEncontristaDois(circulo.getCoordenadorDois().getNomeGuerra().trim().toUpperCase());
				}
				if(etiqueta.getCirculoTres() == null && circulo.getCoordenadorTres() != null) {
					etiqueta.setCirculoTres(circulo.getCor().getCor());
					etiqueta.setEncontristaTres(circulo.getCoordenadorTres().getNomeGuerra().trim().toUpperCase());
					lista.add(etiqueta);
					etiqueta = new Etiqueta();
				}
			}else {
				etiqueta = new Etiqueta();
				if(etiqueta.getCirculoUm() == null) {
					etiqueta.setCirculoUm(circulo.getCor().getCor());
					etiqueta.setEncontristaUm(circulo.getCoordenadorUm().getNomeGuerra().trim().toUpperCase());
				}
				if(etiqueta.getCirculoDois() == null) {
					etiqueta.setCirculoDois(circulo.getCor().getCor());
					etiqueta.setEncontristaDois(circulo.getCoordenadorDois().getNomeGuerra().trim().toUpperCase());
				}
				if(etiqueta.getCirculoTres() == null && circulo.getCoordenadorTres() != null) {
					etiqueta.setCirculoTres(circulo.getCor().getCor());
					etiqueta.setEncontristaTres(circulo.getCoordenadorTres().getNomeGuerra().trim().toUpperCase());
					lista.add(etiqueta);
					etiqueta = new Etiqueta();
				}
			}
			
			for (Pessoa encontrista : circulo.getEncontristas()) {
				if(!etiqueta.isCompleto()) {
					if(etiqueta.getCirculoUm() == null) {
						etiqueta.setCirculoUm(circulo.getCor().getCor());
						etiqueta.setEncontristaUm(encontrista.getNomeGuerra().trim().toUpperCase());
						etiqueta.setPadrinhoUm(encontrista.getPadrinho().getNome());
						continue;
					}
					if(etiqueta.getCirculoDois() == null) {
						etiqueta.setCirculoDois(circulo.getCor().getCor());
						etiqueta.setEncontristaDois(encontrista.getNomeGuerra().trim().toUpperCase());
						etiqueta.setPadrinhoDois(encontrista.getPadrinho().getNome());
						continue;
					}
					if(etiqueta.getCirculoTres() == null) {
						etiqueta.setCirculoTres(circulo.getCor().getCor());
						etiqueta.setEncontristaTres(encontrista.getNomeGuerra().trim().toUpperCase());
						etiqueta.setPadrinhoTres(encontrista.getPadrinho().getNome());
						lista.add(etiqueta);
						etiqueta = new Etiqueta();
					}
				}
			}
		}
		lista.add(etiqueta);
		return lista;
	}
	
	@Override
	public List<Aniversariante> listarAniversariantes(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Circulo> criteriaQuery = builder.createQuery(Circulo.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		criteriaQuery.where(builder.equal(root.get(Circulo_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Circulo_.cor));
		
		TypedQuery<Circulo> query = manager.createQuery(criteriaQuery);
		
		List<Circulo> circulos = query.getResultList();
		
		List<Aniversariante> lista = new ArrayList<>();
		
		int monthValue = LocalDate.now().getMonthValue();
		
		for (Circulo circulo : circulos) {
			if(circulo.getCoordenadorUm() != null && 
					circulo.getCoordenadorUm().getDataNasc().getMonthValue() == monthValue) {
				lista.add(
						new Aniversariante(
								circulo.getCor().getCor(),
								circulo.getCoordenadorUm().getNome(), 
								circulo.getCoordenadorUm().getNomeGuerra(), 
								circulo.getCoordenadorUm().getDataNasc().getDayOfMonth()));
			}
			
			if(circulo.getCoordenadorDois() != null && 
					circulo.getCoordenadorDois().getDataNasc().getMonthValue() == monthValue) {
				lista.add(
						new Aniversariante(
								circulo.getCor().getCor(),
								circulo.getCoordenadorDois().getNome(), 
								circulo.getCoordenadorDois().getNomeGuerra(), 
								circulo.getCoordenadorDois().getDataNasc().getDayOfMonth()));
			}
			
			if(circulo.getCoordenadorTres() != null && 
					circulo.getCoordenadorTres().getDataNasc().getMonthValue() == monthValue) {
				lista.add(
						new Aniversariante(
								circulo.getCor().getCor(),
								circulo.getCoordenadorTres().getNome(), 
								circulo.getCoordenadorTres().getNomeGuerra(), 
								circulo.getCoordenadorTres().getDataNasc().getDayOfMonth()));
			}
			
			for (Pessoa encontrista : circulo.getEncontristas()) {
				if(encontrista.getDataNasc().getMonthValue() == monthValue) {
					lista.add(
							new Aniversariante(
									circulo.getCor().getCor(),
									encontrista.getNome(), 
									encontrista.getNomeGuerra(), 
									encontrista.getDataNasc().getDayOfMonth()));
				}
			}
		}
		
		
		Collections.sort(lista);
		
		return lista;
	}
	
	@Override
	public List<CamisasPorEquipe> listarCamisasPorCirculo(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Circulo> criteriaQuery = builder.createQuery(Circulo.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		criteriaQuery.where(builder.equal(root.get(Circulo_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Circulo_.cor));
		
		TypedQuery<Circulo> query = manager.createQuery(criteriaQuery);
		
		List<Circulo> circulos= query.getResultList();
		
		List<CamisasPorEquipe> lista = new ArrayList<>();
		
		for (Circulo c : circulos) {
			
			lista.add(new CamisasPorEquipe(c.getCor().getCor(),
				c.getCoordenadorUm().getNomeGuerra()+" (Coordenador)",
				c.getCoordenadorUm().getDadosComplementares().getTamanhoBlusa().name()));
			
			lista.add(new CamisasPorEquipe(c.getCor().getCor(),
				c.getCoordenadorDois().getNomeGuerra()+" (Coordenador)",
				c.getCoordenadorDois().getDadosComplementares().getTamanhoBlusa().name()));
			
			if (c.getCoordenadorTres() != null) {
				lista.add(new CamisasPorEquipe(c.getCor().getCor(),
					c.getCoordenadorTres().getNomeGuerra()+" (Coordenador)",
					c.getCoordenadorTres().getDadosComplementares().getTamanhoBlusa().name()));
			}
		}
		
		for (Circulo c : circulos) {
			for (Pessoa p : c.getEncontristas()) {
				if(p.getDadosComplementares().getTamanhoBlusa() != null) {
					lista.add(
							new CamisasPorEquipe(c.getCor().getCor(), p.getNomeGuerra(), 
									p.getDadosComplementares().getTamanhoBlusa().name()));
				}
			}
		}
		
		Collections.sort(lista);
		
		return lista;
	}
	
	@Override
	public List<Agenda> pesquisarAgenda(CirculoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Circulo> criteriaQuery = builder.createQuery(Circulo.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.groupBy(root.get(Circulo_.cor));
		
		TypedQuery<Circulo> query = manager.createQuery(criteriaQuery);
		
		List<Circulo> circulos = query.getResultList();
		
		List<Agenda> lista = new ArrayList<>();
		
		for (Circulo c : circulos) {
			
			Agenda agenda = new Agenda("Círculo: "+c.getCor().name(), 
					c.getCoordenadorUm().getNome(), c.getCoordenadorUm().getNomeGuerra(), c.getCoordenadorUm().getTelefone(), 
					c.getCoordenadorDois().getNome(), c.getCoordenadorDois().getNomeGuerra(), c.getCoordenadorDois().getTelefone(),
					c.getEncontristas());
			
			Collections.sort(agenda.getEncontreiros(), new PessoaNomeGuerraComparator());
			
			lista.add(agenda);
					
			
			if(c.getCoordenadorTres() != null) {
				agenda.setNomeCoordenadorTres(c.getCoordenadorTres().getNome());
				agenda.setNomeGuerraCoordenadorTres(c.getCoordenadorTres().getNomeGuerra());
				agenda.setTelefoneCoordenadorTres(c.getCoordenadorTres().getTelefone());
			}
		} 
		
		return lista;
	}
	
	@Override
	public List<CirculoEstatisticaPorPessoa> porPessoa(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Circulo> criteriaQuery = builder.createQuery(Circulo.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		criteriaQuery.where(builder.equal(root.get(Circulo_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Circulo_.cor));
		
		TypedQuery<Circulo> query = manager.createQuery(criteriaQuery);
		
		List<Circulo> circulos = query.getResultList();
		
		List<CirculoEstatisticaPorPessoa> lista = new ArrayList<>();
		
		for (Circulo circulo : circulos) {
			
			CirculoEstatisticaPorPessoa circuloEstatisticaPorPessoa = 
					new CirculoEstatisticaPorPessoa(circulo.getCor(), 0);
						
			if(circulo.getEncontristas() != null && !circulo.getEncontristas().isEmpty()) {
				circuloEstatisticaPorPessoa.add(circulo.getEncontristas().size());
			}
			
			lista.add(circuloEstatisticaPorPessoa);
		}
		
		return lista;
	}

	@Override
	public Page<Circulo> pesquisar(CirculoFilter circuloFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Circulo> criteriaQuery = builder.createQuery(Circulo.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		Predicate[] predicates = criarRestricoes(circuloFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Circulo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(circuloFilter));
		
	}
	
	@Override
	public Page<CirculoResumo> resumir(CirculoFilter circuloFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<CirculoResumo> criteriaQuery = builder.createQuery(CirculoResumo.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		criteriaQuery.select(builder.construct(CirculoResumo.class,
				root.get(Circulo_.codigo), root.get(Circulo_.cor),
				root.get(Circulo_.coordenadorUm).get(Pessoa_.nomeGuerra), 
				root.get(Circulo_.coordenadorDois).get(Pessoa_.nomeGuerra),
				root.get(Circulo_.coordenadorTres).get(Pessoa_.nomeGuerra)));
		
		Predicate[] predicates = criarRestricoes(circuloFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<CirculoResumo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(circuloFilter));
	}
	
	private Long total(CirculoFilter circuloFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Circulo> root = criteriaQuery.from(Circulo.class);
		
		Predicate[] predicates = criarRestricoes(circuloFilter, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.select(builder.count(root));
		
		
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Predicate[] criarRestricoes(CirculoFilter circuloFilter, CriteriaBuilder builder,
			Root<Circulo> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(circuloFilter.getCodigo() != null) {
			predicates.add(builder.equal(root.get(Circulo_.codigo), 
					circuloFilter.getCodigo()));
		}
		
		if(circuloFilter.getCodigoEjc() != null) {
			predicates.add(builder.equal(root.get(Circulo_.ejc).get(Ejc_.codigo), 
					circuloFilter.getCodigoEjc()));
		}
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
