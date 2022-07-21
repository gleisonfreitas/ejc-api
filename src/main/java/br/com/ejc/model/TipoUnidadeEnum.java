package br.com.ejc.model;

public enum TipoUnidadeEnum {
	
	UNIDADE("Und"),
	KILO("Kg"),
	METRO("M"),
	PACOTE("Pct"),
	PECA("Peça");
	
	private String descricao;

	private TipoUnidadeEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
