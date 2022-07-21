package br.com.ejc.dto;

import java.util.List;

import br.com.ejc.model.Pessoa;

public class Agenda {
	
	private String nome;
	
	private String nomeCoordenadorUm;
	
	private String nomeGuerraCoordenadorUm;
	
	private String telefoneCoordenadorUm;
	
	private String nomeCoordenadorDois;
	
	private String nomeGuerraCoordenadorDois;
	
	private String telefoneCoordenadorDois;
	
	private String nomeCoordenadorTres;
	
	private String nomeGuerraCoordenadorTres;
	
	private String telefoneCoordenadorTres;
	
	private List<Pessoa> encontreiros;

	public Agenda(String nome, String nomeCoordenadorUm, String nomeGuerraCoordenadorUm, String telefoneCoordenadorUm,
			String nomeCoordenadorDois, String nomeGuerraCoordenadorDois, String telefoneCoordenadorDois,
			List<Pessoa> encontreiros) {
		this.nome = nome;
		this.nomeCoordenadorUm = nomeCoordenadorUm;
		this.nomeGuerraCoordenadorUm = nomeGuerraCoordenadorUm;
		this.telefoneCoordenadorUm = telefoneCoordenadorUm;
		this.nomeCoordenadorDois = nomeCoordenadorDois;
		this.nomeGuerraCoordenadorDois = nomeGuerraCoordenadorDois;
		this.telefoneCoordenadorDois = telefoneCoordenadorDois;
		this.encontreiros = encontreiros;
	}
	
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCoordenadorUm() {
		return nomeCoordenadorUm;
	}

	public void setNomeCoordenadorUm(String nomeCoordenadorUm) {
		this.nomeCoordenadorUm = nomeCoordenadorUm;
	}

	public String getNomeGuerraCoordenadorUm() {
		return nomeGuerraCoordenadorUm;
	}

	public void setNomeGuerraCoordenadorUm(String nomeGuerraCoordenadorUm) {
		this.nomeGuerraCoordenadorUm = nomeGuerraCoordenadorUm;
	}

	public String getTelefoneCoordenadorUm() {
		return telefoneCoordenadorUm;
	}

	public void setTelefoneCoordenadorUm(String telefoneCoordenadorUm) {
		this.telefoneCoordenadorUm = telefoneCoordenadorUm;
	}

	public String getNomeCoordenadorDois() {
		return nomeCoordenadorDois;
	}

	public void setNomeCoordenadorDois(String nomeCoordenadorDois) {
		this.nomeCoordenadorDois = nomeCoordenadorDois;
	}

	public String getNomeGuerraCoordenadorDois() {
		return nomeGuerraCoordenadorDois;
	}

	public void setNomeGuerraCoordenadorDois(String nomeGuerraCoordenadorDois) {
		this.nomeGuerraCoordenadorDois = nomeGuerraCoordenadorDois;
	}

	public String getTelefoneCoordenadorDois() {
		return telefoneCoordenadorDois;
	}

	public void setTelefoneCoordenadorDois(String telefoneCoordenadorDois) {
		this.telefoneCoordenadorDois = telefoneCoordenadorDois;
	}

	public String getNomeCoordenadorTres() {
		return nomeCoordenadorTres;
	}

	public void setNomeCoordenadorTres(String nomeCoordenadorTres) {
		this.nomeCoordenadorTres = nomeCoordenadorTres;
	}

	public String getNomeGuerraCoordenadorTres() {
		return nomeGuerraCoordenadorTres;
	}

	public void setNomeGuerraCoordenadorTres(String nomeGuerraCoordenadorTres) {
		this.nomeGuerraCoordenadorTres = nomeGuerraCoordenadorTres;
	}

	public String getTelefoneCoordenadorTres() {
		return telefoneCoordenadorTres;
	}

	public void setTelefoneCoordenadorTres(String telefoneCoordenadorTres) {
		this.telefoneCoordenadorTres = telefoneCoordenadorTres;
	}

	public List<Pessoa> getEncontreiros() {
		return encontreiros;
	}

	public void setEncontreiros(List<Pessoa> encontreiros) {
		this.encontreiros = encontreiros;
	}
}
