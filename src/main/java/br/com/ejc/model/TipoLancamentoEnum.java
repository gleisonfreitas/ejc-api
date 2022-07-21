package br.com.ejc.model;

public enum TipoLancamentoEnum {
	
	RECEITA("Receita"),
	DESPESA("Despesa");
	
	private String descricao;

	private TipoLancamentoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
