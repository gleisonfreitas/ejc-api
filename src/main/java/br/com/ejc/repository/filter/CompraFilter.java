package br.com.ejc.repository.filter;

public class CompraFilter {
	
	private Long codigoEjc;
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCodigoEjc() {
		return codigoEjc;
	}

	public void setCodigoEjc(Long codigoEjc) {
		this.codigoEjc = codigoEjc;
	}
}
