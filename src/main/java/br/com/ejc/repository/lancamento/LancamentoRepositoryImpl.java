package br.com.ejc.repository.lancamento;

import java.math.BigDecimal;
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

import br.com.ejc.dto.LancamentoEstatistica;
import br.com.ejc.dto.LancamentoResumoTipo;
import br.com.ejc.dto.LancamentoResumoTipoPorDia;
import br.com.ejc.model.Ejc_;
import br.com.ejc.model.Lancamento;
import br.com.ejc.model.Lancamento_;
import br.com.ejc.model.TipoLancamentoEnum;
import br.com.ejc.repository.filter.LancamentoFilter;
import br.com.ejc.repository.projection.LancamentoResumo;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<LancamentoResumoTipo> pesquisarResumoTipo(LancamentoFilter lancamentoFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoResumoTipo> criteriaQuery = 
				builder.createQuery(LancamentoResumoTipo.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		
		criteriaQuery.select(builder.construct(LancamentoResumoTipo.class, 
				root.get(Lancamento_.tipo),
				builder.sum(root.get(Lancamento_.valor))));
		
				
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.groupBy(root.get(Lancamento_.tipo));
		
		criteriaQuery.orderBy();
		
		TypedQuery<LancamentoResumoTipo> typedQuery = manager.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoResumoTipoPorDia> pesquisarResumoTipoDia(LancamentoFilter lancamentoFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoResumoTipoPorDia> criteriaQuery = 
				builder.createQuery(LancamentoResumoTipoPorDia.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		
		criteriaQuery.select(builder.construct(LancamentoResumoTipoPorDia.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.data),
				builder.sum(root.get(Lancamento_.valor))));
		
				
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.groupBy(root.get(Lancamento_.data), root.get(Lancamento_.tipo));
		
		criteriaQuery.orderBy();
		
		TypedQuery<LancamentoResumoTipoPorDia> typedQuery = manager.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatistica> estatisticaLancamento(Long codigoEjc) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatistica> criteriaQuery = 
				builder.createQuery(LancamentoEstatistica.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(builder.construct(LancamentoEstatistica.class,
				root.get(Lancamento_.data),
				root.get(Lancamento_.descricao),
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.valor)));
		
		criteriaQuery.where(
				builder.equal(root.get(Lancamento_.ejc).get(Ejc_.codigo), codigoEjc));
		
		criteriaQuery.groupBy(
				root.get(Lancamento_.data));
		
		TypedQuery<LancamentoEstatistica> typedQuery = manager.createQuery(criteriaQuery);
		
		List<LancamentoEstatistica> lista = typedQuery.getResultList();
		
		BigDecimal saldoTemp = BigDecimal.ZERO;

		for (LancamentoEstatistica l : lista) {
			if(l.getTipo().equals(TipoLancamentoEnum.RECEITA)) {
				saldoTemp = saldoTemp.add(l.getValor());
			}else {
				saldoTemp = saldoTemp.subtract(l.getValor());
			}
			l.setSaldo(saldoTemp);
		}
		
		return lista;
	}
	
	@Override
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
		
	}
	
	@Override
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoResumo> criteriaQuery = builder.createQuery(LancamentoResumo.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(builder.construct(LancamentoResumo.class,
				root.get(Lancamento_.codigo), root.get(Lancamento_.descricao), 
				root.get(Lancamento_.data), root.get(Lancamento_.valor),
				root.get(Lancamento_.tipo)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<LancamentoResumo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
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
	
	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(lancamentoFilter.getCodigoEjc() != null) {
			predicates.add(builder.equal(
					root.get(Lancamento_.ejc).get(Ejc_.codigo), lancamentoFilter.getCodigoEjc()));
		}
		
		if(lancamentoFilter.getTipo() != null) {
			predicates.add(builder.equal(
					root.get(Lancamento_.tipo), lancamentoFilter.getTipo()));
		}
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Lancamento_.descricao)), "%"+lancamentoFilter.getDescricao()+"%"));
		}
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
