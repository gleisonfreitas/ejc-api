package br.com.ejc.repository.igreja;

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

import br.com.ejc.model.Igreja;
import br.com.ejc.model.Igreja_;
import br.com.ejc.repository.filter.IgrejaFilter;

public class IgrejaRepositoryImpl implements IgrejaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Igreja> pesquisar(IgrejaFilter igrejaFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Igreja> criteriaQuery = builder.createQuery(Igreja.class);
		Root<Igreja> root = criteriaQuery.from(Igreja.class);
		
		Predicate[] predicates = criarRestricoes(igrejaFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Igreja> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(igrejaFilter));
		
	}
	
	
	private Long total(IgrejaFilter igrejaFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Igreja> root = criteriaQuery.from(Igreja.class);
		
		Predicate[] predicates = criarRestricoes(igrejaFilter, builder, root);
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
	
	private Predicate[] criarRestricoes(IgrejaFilter igrejaFilter, CriteriaBuilder builder,
			Root<Igreja> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(igrejaFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Igreja_.nome)), "%"+igrejaFilter.getNome()+"%"));
		}
		
		if(!StringUtils.isEmpty(igrejaFilter.getPastor())) {
			predicates.add(builder.like(
					builder.lower(root.get(Igreja_.pastor)), "%"+igrejaFilter.getPastor()+"%"));
		}				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
