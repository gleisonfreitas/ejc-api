package br.com.ejc.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Compra.class)
public abstract class Compra_ {

	public static volatile SingularAttribute<Compra, Long> codigo;
	public static volatile SingularAttribute<Compra, BigDecimal> valor;
	public static volatile SingularAttribute<Compra, TipoUnidadeEnum> unidade;
	public static volatile SingularAttribute<Compra, BigDecimal> quantidade;
	public static volatile SingularAttribute<Compra, String> descricao;
	public static volatile SingularAttribute<Compra, Ejc> ejc;

}

