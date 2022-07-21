package br.com.ejc.dto;

public class CamisasPorEquipe implements Comparable<CamisasPorEquipe>{
	
	private String equipe;
	
	private String nomeEncontreiro;
	
	private String tamanho;
	
	public CamisasPorEquipe(String equipe, String nomeEncontreiro, String tamanho) {
		this.equipe = equipe;
		this.nomeEncontreiro = nomeEncontreiro;
		this.tamanho = tamanho;
	}

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	public String getNomeEncontreiro() {
		return nomeEncontreiro;
	}

	public void setNomeEncontreiro(String nomeEncontreiro) {
		this.nomeEncontreiro = nomeEncontreiro;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	@Override
	public int compareTo(CamisasPorEquipe o) {
		return this.equipe.compareTo(o.getEquipe());
	}
}
