package br.com.ejc.repository.filter;

public class PessoaFilter {
	
	private String nome;
	
	private String nomeGuerra;
	
	private Boolean encontrista;
	
	private Boolean encontreiro;
	
	private Boolean trabalhando;

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

	public Boolean getEncontrista() {
		return encontrista;
	}

	public void setEncontrista(Boolean encontrista) {
		this.encontrista = encontrista;
	}

	public Boolean getEncontreiro() {
		return encontreiro;
	}

	public void setEncontreiro(Boolean encontreiro) {
		this.encontreiro = encontreiro;
	}

	public Boolean getTrabalhando() {
		return trabalhando;
	}

	public void setTrabalhando(Boolean trabalhando) {
		this.trabalhando = trabalhando;
	}
}
