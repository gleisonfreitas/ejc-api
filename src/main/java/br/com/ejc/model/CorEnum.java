package br.com.ejc.model;

public enum CorEnum {
	
	AZUL("Azul","#0080ff"),
	AMARELO("Amarelo","#ffff00"),
	ROXO("Roxo","#bf00ff"),
	VERDE("Verde","#00ff40"),
	VERMELHO("Vermelho","#ff0000");
	
	private String cor;
	
	private String hex;
	
	private CorEnum(String cor, String hex) {
		this.cor = cor;
		this.hex = hex;		
	}

	public String getCor() {
		return cor;
	}

	public String getHex() {
		return hex;
	}
}
