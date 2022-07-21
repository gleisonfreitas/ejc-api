package br.com.ejc.repository.patrimonio;

import java.util.ArrayList;
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
import org.springframework.util.StringUtils;

import br.com.ejc.model.Igreja_;
import br.com.ejc.model.Patrimonio;
import br.com.ejc.model.Patrimonio_;
import br.com.ejc.repository.filter.PatrimonioFilter;
import br.com.ejc.repository.projection.PatrimonioResumo;

public class PatrimonioRepositoryImpl implements PatrimonioRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Patrimonio> gerarRelatorio(Long codigoIgreja) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Patrimonio> criteriaQuery = builder.createQuery(Patrimonio.class);
		Root<Patrimonio> root = criteriaQuery.from(Patrimonio.class);
		
		criteriaQuery.where(builder.equal(root.get(Patrimonio_.igreja).get(Igreja_.codigo), codigoIgreja));
		
		criteriaQuery.groupBy(root.get(Patrimonio_.descricao));
		
		TypedQuery<Patrimonio> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
		
	}
	
	@Override
	public Page<Patrimonio> pesquisar(PatrimonioFilter patrimonioFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Patrimonio> criteriaQuery = builder.createQuery(Patrimonio.class);
		Root<Patrimonio> root = criteriaQuery.from(Patrimonio.class);
		
		Predicate[] predicates = criarRestricoes(patrimonioFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Patrimonio> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(patrimonioFilter));
		
	}
	
	@Override
	public Page<PatrimonioResumo> resumir(PatrimonioFilter patrimonioFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PatrimonioResumo> criteriaQuery = builder.createQuery(PatrimonioResumo.class);
		Root<Patrimonio> root = criteriaQuery.from(Patrimonio.class);
		
		criteriaQuery.select(builder.construct(PatrimonioResumo.class,
				root.get(Patrimonio_.codigo), root.get(Patrimonio_.numeroSerie), 
				root.get(Patrimonio_.descricao), root.get(Patrimonio_.estado),
				root.get(Patrimonio_.valor)));
		
		Predicate[] predicates = criarRestricoes(patrimonioFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<PatrimonioResumo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(patrimonioFilter));
	}
	
	private Long total(PatrimonioFilter patrimonioFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Patrimonio> root = criteriaQuery.from(Patrimonio.class);
		
		Predicate[] predicates = criarRestricoes(patrimonioFilter, builder, root);
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
	
	private Predicate[] criarRestricoes(PatrimonioFilter patrimonioFilter, CriteriaBuilder builder,
			Root<Patrimonio> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(patrimonioFilter.getCodigoIgreja() != null) {
			predicates.add(builder.equal(
					root.get(Patrimonio_.igreja).get(Igreja_.codigo), patrimonioFilter.getCodigoIgreja()));
		}
		
		if(!StringUtils.isEmpty(patrimonioFilter.getNumeroSerie())) {
			predicates.add(builder.like(
					builder.lower(root.get(Patrimonio_.numeroSerie)), "%"+patrimonioFilter.getNumeroSerie()+"%"));
		}
		
		if(!StringUtils.isEmpty(patrimonioFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Patrimonio_.descricao)), "%"+patrimonioFilter.getDescricao()+"%"));
		}
		
		if(patrimonioFilter.getEstado() != null) {
			predicates.add(builder.equal(root.get(Patrimonio_.estado), patrimonioFilter.getEstado()));
		}
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
