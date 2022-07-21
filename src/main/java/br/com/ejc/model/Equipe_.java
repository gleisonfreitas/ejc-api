package br.com.ejc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Equipe.class)
public abstract class Equipe_ {

	public static volatile SingularAttribute<Equipe, Pessoa> coordenadorDois;
	public static volatile SingularAttribute<Equipe, Pessoa> coordenadorTres;
	public static volatile ListAttribute<Equipe, Pessoa> encontreiros;
	public static volatile SingularAttribute<Equipe, Long> codigo;
	public static volatile SingularAttribute<Equipe, Pessoa> coordenadorUm;
	public static volatile SingularAttribute<Equipe, EquipeEnum> nome;
	public static volatile SingularAttribute<Equipe, Ejc> ejc;

}

