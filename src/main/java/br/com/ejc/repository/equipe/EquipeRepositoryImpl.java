package br.com.ejc.repository.equipe;

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
import br.com.ejc.dto.EquipeEstatisticaPorPessoa;
import br.com.ejc.dto.Etiqueta;
import br.com.ejc.model.Ejc_;
import br.com.ejc.model.Equipe;
import br.com.ejc.model.Equipe_;
import br.com.ejc.model.Pessoa;
import br.com.ejc.model.Pessoa_;
import br.com.ejc.repository.filter.EquipeFilter;
import br.com.ejc.repository.projection.EquipeResumo;

public class EquipeRepositoryImpl implements EquipeRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Etiqueta> listarEtiquetas(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Equipe> criteriaQuery = builder.createQuery(Equipe.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		criteriaQuery.where(builder.equal(root.get(Equipe_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Equipe_.nome));
		
		TypedQuery<Equipe> query = manager.createQuery(criteriaQuery);
		
		List<Equipe> equipes = query.getResultList();
		
		List<Etiqueta> lista = new ArrayList<>();
		
		Etiqueta etiqueta = new Etiqueta();
		
		for (Equipe equipe : equipes) {

			if(!etiqueta.isCompleto()) {
				if(etiqueta.getCirculoUm() == null) {
					etiqueta.setCirculoUm(equipe.getNome().getDescricao());
					etiqueta.setEncontristaUm(equipe.getCoordenadorUm().getNomeGuerra().trim().toUpperCase());
					etiqueta.setAcessoUm(equipe.getNome().getAcesso());
				}
				if(etiqueta.getCirculoDois() == null) {
					etiqueta.setCirculoDois(equipe.getNome().getDescricao());
					etiqueta.setEncontristaDois(equipe.getCoordenadorDois().getNomeGuerra().trim().toUpperCase());
					etiqueta.setAcessoDois(equipe.getNome().getAcesso());
				}
				if(etiqueta.getCirculoTres() == null && equipe.getCoordenadorTres() != null) {
					etiqueta.setCirculoTres(equipe.getNome().getDescricao());
					etiqueta.setEncontristaTres(equipe.getCoordenadorTres().getNomeGuerra().trim().toUpperCase());
					etiqueta.setAcessoTres(equipe.getNome().getAcesso());
					lista.add(etiqueta);
					etiqueta = new Etiqueta();
				}
			}else {
				etiqueta = new Etiqueta();
				if(etiqueta.getCirculoUm() == null) {
					etiqueta.setCirculoUm(equipe.getNome().getDescricao());
					etiqueta.setEncontristaUm(equipe.getCoordenadorUm().getNomeGuerra().trim().toUpperCase());
					etiqueta.setAcessoUm(equipe.getNome().getAcesso());
				}
				if(etiqueta.getCirculoDois() == null) {
					etiqueta.setCirculoDois(equipe.getNome().getDescricao());
					etiqueta.setEncontristaDois(equipe.getCoordenadorDois().getNomeGuerra().trim().toUpperCase());
					etiqueta.setAcessoDois(equipe.getNome().getAcesso());
				}
				if(etiqueta.getCirculoTres() == null && equipe.getCoordenadorTres() != null) {
					etiqueta.setCirculoTres(equipe.getNome().getDescricao());
					etiqueta.setEncontristaTres(equipe.getCoordenadorTres().getNomeGuerra().trim().toUpperCase());
					etiqueta.setAcessoTres(equipe.getNome().getAcesso());
					lista.add(etiqueta);
					etiqueta = new Etiqueta();
				}
			}
			
			for (Pessoa encontrista : equipe.getEncontreiros()) {
				if(!etiqueta.isCompleto()) {
					if(etiqueta.getCirculoUm() == null) {
						etiqueta.setCirculoUm(equipe.getNome().getDescricao());
						etiqueta.setEncontristaUm(encontrista.getNomeGuerra().trim().toUpperCase());
						etiqueta.setAcessoUm(equipe.getNome().getAcesso());
						continue;
					}
					if(etiqueta.getCirculoDois() == null) {
						etiqueta.setCirculoDois(equipe.getNome().getDescricao());
						etiqueta.setEncontristaDois(encontrista.getNomeGuerra().trim().toUpperCase());
						etiqueta.setAcessoDois(equipe.getNome().getAcesso());
						continue;
					}
					if(etiqueta.getCirculoTres() == null) {
						etiqueta.setCirculoTres(equipe.getNome().getDescricao());
						etiqueta.setEncontristaTres(encontrista.getNomeGuerra().trim().toUpperCase());
						etiqueta.setAcessoTres(equipe.getNome().getAcesso());
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
	public List<Agenda> pesquisarAgenda(EquipeFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Equipe> criteriaQuery = builder.createQuery(Equipe.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.groupBy(root.get(Equipe_.nome));
		
		TypedQuery<Equipe> query = manager.createQuery(criteriaQuery);
		
		List<Equipe> equipes = query.getResultList();
		
		List<Agenda> lista = new ArrayList<>();
		
		for (Equipe e : equipes) {
			
			Agenda agenda = new Agenda("Equipe: "+e.getNome().getDescricao().toUpperCase(), 
					e.getCoordenadorUm().getNome(), e.getCoordenadorUm().getNomeGuerra(), e.getCoordenadorUm().getTelefone(), 
					e.getCoordenadorDois().getNome(), e.getCoordenadorDois().getNomeGuerra(), e.getCoordenadorDois().getTelefone(),
					e.getEncontreiros());
			
			Collections.sort(agenda.getEncontreiros(), new PessoaNomeGuerraComparator());
			
			lista.add(agenda);
					
			
			if(e.getCoordenadorTres() != null) {
				agenda.setNomeCoordenadorTres(e.getCoordenadorTres().getNome());
				agenda.setNomeGuerraCoordenadorTres(e.getCoordenadorTres().getNomeGuerra());
				agenda.setTelefoneCoordenadorTres(e.getCoordenadorTres().getTelefone());
			}
		}
		
		
		return lista;
	}
	
	@Override
	public List<Aniversariante> listarAniversariantes(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Equipe> criteriaQuery = builder.createQuery(Equipe.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		criteriaQuery.where(builder.equal(root.get(Equipe_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Equipe_.nome));
		
		TypedQuery<Equipe> query = manager.createQuery(criteriaQuery);
		
		List<Equipe> equipes = query.getResultList();
		
		List<Aniversariante> lista = new ArrayList<>();
		
		int monthValue = LocalDate.now().getMonthValue();
		
		for (Equipe equipe : equipes) {
			if(equipe.getCoordenadorUm() != null && 
					equipe.getCoordenadorUm().getDataNasc().getMonthValue() == monthValue) {
				lista.add(
						new Aniversariante(
								equipe.getNome().getDescricao(),
								equipe.getCoordenadorUm().getNome(), 
								equipe.getCoordenadorUm().getNomeGuerra(), 
								equipe.getCoordenadorUm().getDataNasc().getDayOfMonth()));
			}
			
			if(equipe.getCoordenadorDois() != null && 
					equipe.getCoordenadorDois().getDataNasc().getMonthValue() == monthValue) {
				lista.add(
						new Aniversariante(
								equipe.getNome().getDescricao(),
								equipe.getCoordenadorDois().getNome(), 
								equipe.getCoordenadorDois().getNomeGuerra(), 
								equipe.getCoordenadorDois().getDataNasc().getDayOfMonth()));
			}
			
			if(equipe.getCoordenadorTres() != null && 
					equipe.getCoordenadorTres().getDataNasc().getMonthValue() == monthValue) {
				lista.add(
						new Aniversariante(
								equipe.getNome().getDescricao(),
								equipe.getCoordenadorTres().getNome(), 
								equipe.getCoordenadorTres().getNomeGuerra(), 
								equipe.getCoordenadorTres().getDataNasc().getDayOfMonth()));
			}
			
			for (Pessoa encontreiro : equipe.getEncontreiros()) {
				if(encontreiro.getDataNasc().getMonthValue() == monthValue) {
					lista.add(
							new Aniversariante(
									equipe.getNome().getDescricao(),
									encontreiro.getNome(), 
									encontreiro.getNomeGuerra(), 
									encontreiro.getDataNasc().getDayOfMonth()));
				}
			}
		}
		
		
		Collections.sort(lista);
		
		return lista;
	}
	
	@Override
	public List<CamisasPorEquipe> listarCamisasPorEquipe(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Equipe> criteriaQuery = builder.createQuery(Equipe.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		criteriaQuery.where(builder.equal(root.get(Equipe_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Equipe_.nome));
		
		TypedQuery<Equipe> query = manager.createQuery(criteriaQuery);
		
		List<Equipe> equipes = query.getResultList();
		
		List<CamisasPorEquipe> lista = new ArrayList<>();
		
		for (Equipe e : equipes) {
			
			lista.add(new CamisasPorEquipe(e.getNome().getDescricao(),
				e.getCoordenadorUm().getNomeGuerra()+" (Coordenador)",
				e.getCoordenadorUm().getDadosComplementares().getTamanhoBlusa().name()));
			
			lista.add(new CamisasPorEquipe(e.getNome().getDescricao(),
				e.getCoordenadorDois().getNomeGuerra()+" (Coordenador)",
				e.getCoordenadorDois().getDadosComplementares().getTamanhoBlusa().name()));
			
			if (e.getCoordenadorTres() != null) {
				lista.add(new CamisasPorEquipe(e.getNome().getDescricao(),
					e.getCoordenadorTres().getNomeGuerra()+" (Coordenador)",
					e.getCoordenadorTres().getDadosComplementares().getTamanhoBlusa().name()));
			}
		}
		
		for (Equipe equipe : equipes) {
			for (Pessoa p : equipe.getEncontreiros()) {
				if(p.getDadosComplementares().getTamanhoBlusa() != null) {
					lista.add(
							new CamisasPorEquipe(equipe.getNome().getDescricao(), p.getNomeGuerra(), 
									p.getDadosComplementares().getTamanhoBlusa().name()));
				}
			}
		}
		
		Collections.sort(lista);
		
		return lista;
	}
	
	@Override
	public List<EquipeEstatisticaPorPessoa> porPessoa(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Equipe> criteriaQuery = builder.createQuery(Equipe.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		criteriaQuery.where(builder.equal(root.get(Equipe_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Equipe_.nome));
		
		TypedQuery<Equipe> query = manager.createQuery(criteriaQuery);
		
		List<Equipe> equipes = query.getResultList();
		
		List<EquipeEstatisticaPorPessoa> lista = new ArrayList<>();
		
		for (Equipe equipe : equipes) {
			
			EquipeEstatisticaPorPessoa equipeEstatisticaPorPessoa = 
					new EquipeEstatisticaPorPessoa(equipe.getNome(), 0);
			if(equipe.getCoordenadorUm() != null) {
				equipeEstatisticaPorPessoa.add(1);
			}
			if(equipe.getCoordenadorDois() != null) {
				equipeEstatisticaPorPessoa.add(1);
			}
			if(equipe.getCoordenadorTres() != null) {
				equipeEstatisticaPorPessoa.add(1);
			}
			
			if(equipe.getEncontreiros() != null && !equipe.getEncontreiros().isEmpty()) {
				equipeEstatisticaPorPessoa.add(equipe.getEncontreiros().size());
			}
			
			lista.add(equipeEstatisticaPorPessoa);
		}
		
		return lista;
	}

	@Override
	public Page<Equipe> pesquisar(EquipeFilter equipeFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Equipe> criteriaQuery = builder.createQuery(Equipe.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		Predicate[] predicates = criarRestricoes(equipeFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Equipe> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(equipeFilter));
		
	}
	
	@Override
	public Page<EquipeResumo> resumir(EquipeFilter equipeFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EquipeResumo> criteriaQuery = builder.createQuery(EquipeResumo.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		criteriaQuery.select(builder.construct(EquipeResumo.class,
				root.get(Equipe_.codigo), root.get(Equipe_.nome),
				root.get(Equipe_.coordenadorUm).get(Pessoa_.nomeGuerra),
				root.get(Equipe_.coordenadorDois).get(Pessoa_.nomeGuerra),
				root.get(Equipe_.coordenadorUm).get(Pessoa_.telefone),
				root.get(Equipe_.coordenadorDois).get(Pessoa_.telefone)));
			
		
		Predicate[] predicates = criarRestricoes(equipeFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<EquipeResumo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(equipeFilter));
	}
	
	private Long total(EquipeFilter equipeFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Equipe> root = criteriaQuery.from(Equipe.class);
		
		Predicate[] predicates = criarRestricoes(equipeFilter, builder, root);
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
	
	private Predicate[] criarRestricoes(EquipeFilter equipeFilter, CriteriaBuilder builder,
			Root<Equipe> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(equipeFilter.getCodigoEjc() != null) {
			predicates.add(builder.equal(root.get(Equipe_.ejc).get(Ejc_.codigo), 
					equipeFilter.getCodigoEjc()));
		}
		
		if(equipeFilter.getCodigo() != null) {
			predicates.add(builder.equal(root.get(Equipe_.codigo), equipeFilter.getCodigo()));
		}
		
		if(equipeFilter.getNome() != null) {
			predicates.add(builder.equal(root.get(Equipe_.nome), equipeFilter.getNome()));
		}
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
