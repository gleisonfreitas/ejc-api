package br.com.ejc.repository.projection;

import java.time.LocalDate;

public class PessoaResumo {
	
	private Long codigo;
	
	private String nome;
	
	private String nomeGuerra;
	
	private LocalDate dataNasc;
	
	private String cep;
	
	private String igreja;
	
	private Boolean encontreiro;

	public PessoaResumo(Long codigo, String nome, String nomeGuerra, LocalDate dataNasc, 
			String cep, String igreja, Boolean encontreiro) {
		this.codigo = codigo;
		this.nome = nome;
		this.nomeGuerra = nomeGuerra;
		this.dataNasc = dataNasc;
		this.cep = cep;
		this.igreja = igreja;
		this.encontreiro = encontreiro;
	}
	
	public Long getCodigo() {
		return codigo;
	}



	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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
	
	public LocalDate getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(LocalDate dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getIgreja() {
		return igreja;
	}

	public void setIgreja(String igreja) {
		this.igreja = igreja;
	}

	public Boolean getEncontreiro() {
		return encontreiro;
	}

	public void setEncontreiro(Boolean encontreiro) {
		this.encontreiro = encontreiro;
	}
}
