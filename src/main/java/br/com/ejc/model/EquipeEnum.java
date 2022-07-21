package br.com.ejc.model;

public enum EquipeEnum {

	APRESENTADORES("Apresentadores","Verde"),
	CASAL_PASTA("Casal Pasta","Verde"),
	APOIO("Apoio","Vermelho"),
	BANDINHA("Bandinha","Amarelo"),
	BOA_VONTADE("Boa Vontade","Verde"),
	CAFEZINHO("Cafezinho","Amarelo"),
	CERIMONIAL("Cerimônial","Vermelho"),
	COMPRAS("Compras","Vermelho"),
	CIRCULOS("Círculos","Verde"),
	COORDENACAO_GERAL("Coordenação Geral","Verde"),
	CORREIOS("Correios","Amarelo"),
	COZINHA("Cozinha","Vermelho"),
	DIRIGENTE_ESPIRITUAL("Dirigente Espiritual","Verde"),
	GARCONS("Garçons","Amarelo"),
	LANCHONETE("Lanchonete","Vermelho"),
	LIVRARIA("Livraria","Amarelo"),
	SAUDE("Saúde","Amarelo"),
	ORAÇÃO("Oração","Vermelho"),
	ORDEM("Ordem","Vermelho"),
	RECEPCAO_PALESTRANTES("Recepção dos palestrantes","Verde"),
	SECRETARIA("Secretaria","Vermelho"),
	SOM_ILUMINACAO("Som e Iluminação","Verde"),
	TRANSITO("Trânsito","Amarelo"),
	VISITACAO_EXTERNA("Visitação Externa","Vermelho"),
	TEATRO("Teatro","Amarelo");
	
	private String descricao;
	
	private String acesso;
	
	private EquipeEnum(String descricao, String acesso) {
		this.descricao = descricao;
		this.acesso = acesso;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getAcesso() {
		return acesso;
	}
}
