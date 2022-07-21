package br.com.ejc.dto;

public class Etiqueta {
	
	private String circuloUm;
	
	private String AcessoUm;
	
	private String encontristaUm;
	
	private String padrinhoUm;
	
	private String circuloDois;
	
	private String AcessoDois;
	
	private String encontristaDois;
	
	private String padrinhoDois;
	
	private String circuloTres;
	
	private String AcessoTres;
	
	private String encontristaTres;
	
	private String padrinhoTres;
	
	public Boolean isCompleto() {
		return this.circuloUm != null &&
				this.circuloDois != null &&
				this.circuloTres != null;
	}

	public String getCirculoUm() {
		return circuloUm;
	}

	public void setCirculoUm(String circuloUm) {
		this.circuloUm = circuloUm;
	}

	public String getEncontristaUm() {
		return encontristaUm;
	}

	public void setEncontristaUm(String encontristaUm) {
		this.encontristaUm = encontristaUm;
	}

	public String getPadrinhoUm() {
		return padrinhoUm;
	}

	public void setPadrinhoUm(String padrinhoUm) {
		this.padrinhoUm = padrinhoUm;
	}

	public String getCirculoDois() {
		return circuloDois;
	}

	public void setCirculoDois(String circuloDois) {
		this.circuloDois = circuloDois;
	}

	public String getEncontristaDois() {
		return encontristaDois;
	}

	public void setEncontristaDois(String encontristaDois) {
		this.encontristaDois = encontristaDois;
	}

	public String getPadrinhoDois() {
		return padrinhoDois;
	}

	public void setPadrinhoDois(String padrinhoDois) {
		this.padrinhoDois = padrinhoDois;
	}

	public String getCirculoTres() {
		return circuloTres;
	}

	public void setCirculoTres(String circuloTres) {
		this.circuloTres = circuloTres;
	}

	public String getEncontristaTres() {
		return encontristaTres;
	}

	public void setEncontristaTres(String encontristaTres) {
		this.encontristaTres = encontristaTres;
	}

	public String getPadrinhoTres() {
		return padrinhoTres;
	}

	public void setPadrinhoTres(String padrinhoTres) {
		this.padrinhoTres = padrinhoTres;
	}

	public String getAcessoUm() {
		return AcessoUm;
	}

	public void setAcessoUm(String acessoUm) {
		AcessoUm = acessoUm;
	}

	public String getAcessoDois() {
		return AcessoDois;
	}

	public void setAcessoDois(String acessoDois) {
		AcessoDois = acessoDois;
	}

	public String getAcessoTres() {
		return AcessoTres;
	}

	public void setAcessoTres(String acessoTres) {
		AcessoTres = acessoTres;
	}
}
