package br.com.ejc.repository.edg;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import br.com.ejc.model.Edg;
import br.com.ejc.model.Edg_;
import br.com.ejc.model.Ejc_;
import br.com.ejc.model.Pessoa_;
import br.com.ejc.repository.projection.EdgResumo;

public class EdgRepositoryImpl implements EdgRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<EdgResumo> pesquisarPorEjc(Long codigoEjc) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EdgResumo> criteriaQuery = builder.createQuery(EdgResumo.class);
		Root<Edg> root = criteriaQuery.from(Edg.class);
		
		Predicate[] predicates = criarRestricoes(codigoEjc, builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.select(builder.construct(EdgResumo.class,
				root.get(Edg_.codigo),
				root.get(Edg_.pessoa).get(Pessoa_.nomeGuerra), 
				root.get(Edg_.funcao), root.get(Edg_.pessoa).get(Pessoa_.telefone)));
		
		TypedQuery<EdgResumo> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(Long codigoEjc, CriteriaBuilder builder,
			Root<Edg> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(builder.equal(
				root.get(Edg_.ejc).get(Ejc_.codigo), codigoEjc));
				
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
