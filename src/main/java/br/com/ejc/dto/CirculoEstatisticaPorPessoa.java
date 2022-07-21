package br.com.ejc.dto;

import br.com.ejc.model.CorEnum;

public class CirculoEstatisticaPorPessoa {
	
	private CorEnum cor;
	
	private Integer total;

	public CirculoEstatisticaPorPessoa(CorEnum cor, Integer total) {
		this.cor = cor;
		this.total = total;
	}

	public CorEnum getCor() {
		return cor;
	}
	
	public String getNomeCor() {
		return cor.getCor();
	}
	
	public String getHex() {
		return cor.getHex();
	}

	public void setCor(CorEnum cor) {
		this.cor = cor;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public void add(Integer valor) {
		this.total += valor;
	}
}
