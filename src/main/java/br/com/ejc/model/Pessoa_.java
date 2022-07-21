package br.com.ejc.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ {

	public static volatile SingularAttribute<Pessoa, Boolean> encontreiro;
	public static volatile ListAttribute<Pessoa, Historico> historicos;
	public static volatile SingularAttribute<Pessoa, Long> codigo;
	public static volatile SingularAttribute<Pessoa, String> telefone;
	public static volatile SingularAttribute<Pessoa, Endereco> endereco;
	public static volatile SingularAttribute<Pessoa, Character> dirigenteCirculo;
	public static volatile SingularAttribute<Pessoa, Character> edg;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, EstadoCivilEnum> estadoCivil;
	public static volatile SingularAttribute<Pessoa, LocalDate> dataNasc;
	public static volatile SingularAttribute<Pessoa, Boolean> trabalhando;
	public static volatile SingularAttribute<Pessoa, DadosComplementares> dadosComplementares;
	public static volatile SingularAttribute<Pessoa, Boolean> encontrista;
	public static volatile SingularAttribute<Pessoa, Filiacao> filiacao;
	public static volatile SingularAttribute<Pessoa, Padrinho> padrinho;
	public static volatile SingularAttribute<Pessoa, Boolean> emailEnviado;
	public static volatile SingularAttribute<Pessoa, String> nomeGuerra;
	public static volatile SingularAttribute<Pessoa, Character> coordenador;
	public static volatile SingularAttribute<Pessoa, SexoEnum> sexo;
	public static volatile SingularAttribute<Pessoa, String> email;

}

