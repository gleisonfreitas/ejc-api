package br.com.ejc.repository.pessoa;

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

import br.com.ejc.model.DadosComplementares_;
import br.com.ejc.model.Endereco_;
import br.com.ejc.model.Pessoa;
import br.com.ejc.model.Pessoa_;
import br.com.ejc.repository.filter.PessoaFilter;
import br.com.ejc.repository.projection.PessoaResumo;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Pessoa> pesquisar(PessoaFilter pessoaFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Pessoa> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
		
	}
	
	@Override
	public Page<PessoaResumo> resumir(PessoaFilter pessoaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoaResumo> criteriaQuery = builder.createQuery(PessoaResumo.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(builder.construct(PessoaResumo.class,
				root.get(Pessoa_.codigo),
				root.get(Pessoa_.nome), 
				root.get(Pessoa_.nomeGuerra),
				root.get(Pessoa_.dataNasc),
				root.get(Pessoa_.endereco).get(Endereco_.cep),
				root.get(Pessoa_.dadosComplementares).get(DadosComplementares_.igreja),
				root.get(Pessoa_.encontreiro)));
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<PessoaResumo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
	}
	
	@Override
	public List<PessoaResumo> listarPessoasEdg() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoaResumo> criteriaQuery = builder.createQuery(PessoaResumo.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(builder.construct(PessoaResumo.class,
				root.get(Pessoa_.codigo),
				root.get(Pessoa_.nome), root.get(Pessoa_.nomeGuerra),
				root.get(Pessoa_.dadosComplementares).get(DadosComplementares_.igreja),
				root.get(Pessoa_.encontreiro)));
		
		criteriaQuery.where(builder.equal(root.get(Pessoa_.edg), 'S'));
		
		TypedQuery<PessoaResumo> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	@Override
	public List<PessoaResumo> listarPessoasCoordenador() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoaResumo> criteriaQuery = builder.createQuery(PessoaResumo.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(builder.construct(PessoaResumo.class,
				root.get(Pessoa_.codigo),
				root.get(Pessoa_.nome), root.get(Pessoa_.nomeGuerra),
				root.get(Pessoa_.dadosComplementares).get(DadosComplementares_.igreja),
				root.get(Pessoa_.encontreiro)));
		
		criteriaQuery.where(builder.equal(root.get(Pessoa_.coordenador), 'S'));
		
		TypedQuery<PessoaResumo> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	@Override
	public List<PessoaResumo> listarPessoasDirigenteCirculo() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoaResumo> criteriaQuery = builder.createQuery(PessoaResumo.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(builder.construct(PessoaResumo.class,
				root.get(Pessoa_.codigo),
				root.get(Pessoa_.nome), root.get(Pessoa_.nomeGuerra),
				root.get(Pessoa_.dadosComplementares).get(DadosComplementares_.igreja),
				root.get(Pessoa_.encontreiro)));
		
		criteriaQuery.where(builder.equal(root.get(Pessoa_.dirigenteCirculo), 'S'));
		
		TypedQuery<PessoaResumo> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	@Override
	public List<PessoaResumo> listarEncontreirosDisponiveis() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoaResumo> criteriaQuery = builder.createQuery(PessoaResumo.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(builder.construct(PessoaResumo.class,
				root.get(Pessoa_.codigo),
				root.get(Pessoa_.nome), root.get(Pessoa_.nomeGuerra),
				root.get(Pessoa_.dadosComplementares).get(DadosComplementares_.igreja),
				root.get(Pessoa_.encontreiro)));
		
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(builder.equal(root.get(Pessoa_.trabalhando), Boolean.FALSE));
		predicates.add(builder.equal(root.get(Pessoa_.encontreiro), Boolean.TRUE));
		
		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		
		TypedQuery<PessoaResumo> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	@Override
	public List<PessoaResumo> listarEncontristasDisponiveis() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoaResumo> criteriaQuery = builder.createQuery(PessoaResumo.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(builder.construct(PessoaResumo.class,
				root.get(Pessoa_.codigo),
				root.get(Pessoa_.nome), root.get(Pessoa_.nomeGuerra),
				root.get(Pessoa_.dadosComplementares).get(DadosComplementares_.igreja),
				root.get(Pessoa_.encontreiro)));
		
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(builder.equal(root.get(Pessoa_.trabalhando), Boolean.FALSE));
		predicates.add(builder.equal(root.get(Pessoa_.encontrista), Boolean.TRUE));
		
		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		
		TypedQuery<PessoaResumo> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	private Long total(PessoaFilter pessoaFilter) {
		
		CriteriaBuilder  builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
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
	
	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder,
			Root<Pessoa> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(pessoaFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.nome)), "%"+pessoaFilter.getNome()+"%"));
		}
		
		if(!StringUtils.isEmpty(pessoaFilter.getNomeGuerra())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.nomeGuerra)), "%"+pessoaFilter.getNomeGuerra()+"%"));
		}
		
		if(pessoaFilter.getTrabalhando() != null) {
			predicates.add(builder.equal(root.get(Pessoa_.trabalhando), pessoaFilter.getTrabalhando()));
		}
		
		if(pessoaFilter.getEncontreiro() != null) {
			predicates.add(builder.equal(root.get(Pessoa_.encontreiro), pessoaFilter.getEncontreiro()));
		}
		
		if(pessoaFilter.getEncontrista() != null) {
			predicates.add(builder.equal(root.get(Pessoa_.encontrista), pessoaFilter.getEncontrista()));
		}
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
