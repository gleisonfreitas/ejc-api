package br.com.ejc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Historico.class)
public abstract class Historico_ {

	public static volatile SingularAttribute<Historico, Long> codigo;
	public static volatile SingularAttribute<Historico, Pessoa> pessoa;
	public static volatile SingularAttribute<Historico, Boolean> coordenador;
	public static volatile SingularAttribute<Historico, Integer> quantidade;
	public static volatile SingularAttribute<Historico, EquipeEnum> equipe;

}

