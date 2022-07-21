package br.com.ejc.dto;

public class Aniversariante implements Comparable<Aniversariante>{
	
	private String equipe;
	private String nome;
	private String nomeGuerra;
	private Integer dia;
	
	public Aniversariante(String equipe, String nome, String nomeGuerra, Integer dia) {
		this.equipe = equipe;
		this.nome = nome;
		this.nomeGuerra = nomeGuerra;
		this.dia = dia;
	}
	
	@Override
	public int compareTo(Aniversariante o) {
		return this.dia.compareTo(o.getDia());
	}

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeGuerra() {
		return nomeGuerra;
	}

	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

}
