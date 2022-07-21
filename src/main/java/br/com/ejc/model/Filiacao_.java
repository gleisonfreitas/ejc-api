package br.com.ejc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Filiacao.class)
public abstract class Filiacao_ {

	public static volatile SingularAttribute<Filiacao, String> mae;
	public static volatile SingularAttribute<Filiacao, Long> codigo;
	public static volatile SingularAttribute<Filiacao, Pessoa> pessoa;
	public static volatile SingularAttribute<Filiacao, String> telefoneMae;
	public static volatile SingularAttribute<Filiacao, String> pai;
	public static volatile SingularAttribute<Filiacao, String> telefonePai;

}

