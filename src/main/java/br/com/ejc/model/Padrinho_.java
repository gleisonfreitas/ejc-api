package br.com.ejc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Padrinho.class)
public abstract class Padrinho_ {

	public static volatile SingularAttribute<Padrinho, Long> codigo;
	public static volatile SingularAttribute<Padrinho, String> telefone;
	public static volatile SingularAttribute<Padrinho, Boolean> trabalhando;
	public static volatile SingularAttribute<Padrinho, Pessoa> pessoa;
	public static volatile SingularAttribute<Padrinho, String> nome;

}

