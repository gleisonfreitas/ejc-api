package br.com.ejc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Circulo.class)
public abstract class Circulo_ {

	public static volatile SingularAttribute<Circulo, Pessoa> coordenadorDois;
	public static volatile SingularAttribute<Circulo, Pessoa> coordenadorTres;
	public static volatile SingularAttribute<Circulo, Long> codigo;
	public static volatile SingularAttribute<Circulo, CorEnum> cor;
	public static volatile SingularAttribute<Circulo, Pessoa> coordenadorUm;
	public static volatile ListAttribute<Circulo, Pessoa> encontristas;
	public static volatile SingularAttribute<Circulo, Ejc> ejc;

}

