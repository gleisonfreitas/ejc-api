package br.com.ejc.repository.ejc;

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

import br.com.ejc.model.Ejc;
import br.com.ejc.model.Ejc_;
import br.com.ejc.model.Igreja_;
import br.com.ejc.repository.filter.EjcFilter;
import br.com.ejc.repository.projection.EjcResumo;

public class EjcRepositoryImpl implements EjcRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Ejc> pesquisar(EjcFilter ejcFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Ejc> criteriaQuery = builder.createQuery(Ejc.class);
		Root<Ejc> root = criteriaQuery.from(Ejc.class);
		
		Predicate[] predicates = criarRestricoes(ejcFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Ejc> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(ejcFilter));
		
	}
	
	@Override
	public Page<EjcResumo> resumir(EjcFilter ejcFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EjcResumo> criteriaQuery = builder.createQuery(EjcResumo.class);
		Root<Ejc> root = criteriaQuery.from(Ejc.class);
		
		criteriaQuery.select(builder.construct(EjcResumo.class,
				root.get(Ejc_.codigo), root.get(Ejc_.numero),
				root.get(Ejc_.inicio), root.get(Ejc_.fim), 
				root.get(Ejc_.ativo), root.get(Ejc_.igreja).get(Igreja_.nome)));
		
		Predicate[] predicates = criarRestricoes(ejcFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<EjcResumo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(ejcFilter));
	}
	
	@Override
	public List<EjcResumo> listarResumoAtivos() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EjcResumo> criteriaQuery = builder.createQuery(EjcResumo.class);
		Root<Ejc> root = criteriaQuery.from(Ejc.class);
		
		criteriaQuery.select(builder.construct(EjcResumo.class,
				root.get(Ejc_.codigo), root.get(Ejc_.numero),
				root.get(Ejc_.inicio), root.get(Ejc_.fim), 
				root.get(Ejc_.ativo), root.get(Ejc_.igreja).get(Igreja_.nome)));
		
		criteriaQuery.where(builder.equal(root.get(Ejc_.ativo), Boolean.TRUE));
		
		TypedQuery<EjcResumo> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	private Long total(EjcFilter ejcFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Ejc> root = criteriaQuery.from(Ejc.class);
		
		Predicate[] predicates = criarRestricoes(ejcFilter, builder, root);
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
	
	private Predicate[] criarRestricoes(EjcFilter ejcFilter, CriteriaBuilder builder,
			Root<Ejc> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(ejcFilter.getIgreja())) {
			predicates.add(builder.like(
					builder.lower(
							root.get(Ejc_.igreja).get(Igreja_.nome)), "%"+ejcFilter.getIgreja()+"%"));
		}
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
