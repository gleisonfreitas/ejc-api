package br.com.ejc.model;

public enum FuncaoEdgEnum {

	
	DIRIGENTE_ESPIRITUAL("Digirente Espiritual"),
	PRESIDENTE("Presidente"),
	FINANCAS("Finanças"),
	SECRETARIA("Secretaria"),
	PATRIMONIO("Patrimônio"),
	MONTAGEM("Montagem"),
	POS_ENCONTRO("Pós encontro"),
	ROTEIRISTA("Roteirista"),
	COORDENADOR_CIRCULOS("Coordenador Círculos");
	
	private String descricao;
	
	private FuncaoEdgEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
