package br.com.ejc.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ejc.class)
public abstract class Ejc_ {

	public static volatile SingularAttribute<Ejc, Long> codigo;
	public static volatile SingularAttribute<Ejc, String> enderecoEscola;
	public static volatile SingularAttribute<Ejc, Boolean> ativo;
	public static volatile SingularAttribute<Ejc, String> tema;
	public static volatile SingularAttribute<Ejc, String> escola;
	public static volatile SingularAttribute<Ejc, String> numero;
	public static volatile SingularAttribute<Ejc, Igreja> igreja;
	public static volatile SingularAttribute<Ejc, LocalDate> inicio;
	public static volatile SingularAttribute<Ejc, byte[]> logo;
	public static volatile SingularAttribute<Ejc, LocalDate> fim;

}

