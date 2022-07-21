package br.com.ejc.repository.compra;

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

import br.com.ejc.model.Compra;
import br.com.ejc.model.Compra_;
import br.com.ejc.model.Ejc_;
import br.com.ejc.repository.filter.CompraFilter;
import br.com.ejc.repository.projection.CompraResumo;

public class CompraRepositoryImpl implements CompraRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Compra> gerarRelatorio(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Compra> criteriaQuery = builder.createQuery(Compra.class);
		Root<Compra> root = criteriaQuery.from(Compra.class);
		
		criteriaQuery.where(builder.equal(root.get(Compra_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(root.get(Compra_.descricao));
		
		TypedQuery<Compra> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
		
	}
	
	@Override
	public Page<Compra> pesquisar(CompraFilter compraFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Compra> criteriaQuery = builder.createQuery(Compra.class);
		Root<Compra> root = criteriaQuery.from(Compra.class);
		
		Predicate[] predicates = criarRestricoes(compraFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Compra> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(compraFilter));
		
	}
	
	@Override
	public Page<CompraResumo> resumir(CompraFilter compraFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<CompraResumo> criteriaQuery = builder.createQuery(CompraResumo.class);
		Root<Compra> root = criteriaQuery.from(Compra.class);
		
		criteriaQuery.select(builder.construct(CompraResumo.class,
				root.get(Compra_.codigo), root.get(Compra_.descricao), 
				root.get(Compra_.unidade), root.get(Compra_.valor),
				root.get(Compra_.quantidade)));
		
		Predicate[] predicates = criarRestricoes(compraFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<CompraResumo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(compraFilter));
	}
	
	private Long total(CompraFilter compraFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Compra> root = criteriaQuery.from(Compra.class);
		
		Predicate[] predicates = criarRestricoes(compraFilter, builder, root);
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
	
	private Predicate[] criarRestricoes(CompraFilter compraFilter, CriteriaBuilder builder,
			Root<Compra> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(compraFilter.getCodigoEjc() != null) {
			predicates.add(builder.equal(
					root.get(Compra_.ejc).get(Ejc_.codigo), compraFilter.getCodigoEjc()));
		}
		
		if(!StringUtils.isEmpty(compraFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Compra_.descricao)), "%"+compraFilter.getDescricao()+"%"));
		}
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
