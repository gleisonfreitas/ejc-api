package br.com.ejc.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Patrimonio.class)
public abstract class Patrimonio_ {

	public static volatile SingularAttribute<Patrimonio, String> numeroSerie;
	public static volatile SingularAttribute<Patrimonio, Long> codigo;
	public static volatile SingularAttribute<Patrimonio, EstadoConservacaoEnum> estado;
	public static volatile SingularAttribute<Patrimonio, Igreja> igreja;
	public static volatile SingularAttribute<Patrimonio, BigDecimal> valor;
	public static volatile SingularAttribute<Patrimonio, String> responsavel;
	public static volatile SingularAttribute<Patrimonio, Integer> quantidade;
	public static volatile SingularAttribute<Patrimonio, String> local;
	public static volatile SingularAttribute<Patrimonio, String> descricao;
	public static volatile SingularAttribute<Patrimonio, LocalDate> dataCompra;

}

