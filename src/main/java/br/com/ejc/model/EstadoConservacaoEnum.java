package br.com.ejc.model;

public enum EstadoConservacaoEnum {
	
	RUIM("Ruim"),
	BOA("Bom"),
	OTIMA("Ótimo");
	
	private String descricao;

	private EstadoConservacaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
