package br.com.ejc.dto;


import br.com.ejc.model.EquipeEnum;

public class EquipeEstatisticaPorPessoa {
	
	private EquipeEnum equipe;
	
	private Integer total;

	public EquipeEstatisticaPorPessoa(EquipeEnum equipe, Integer total) {
		this.equipe = equipe;
		this.total = total;
	}

	public EquipeEnum getEquipe() {
		return equipe;
	}
	
	public String getNomeEquipe() {
		return equipe.getDescricao();
	}

	public void setEquipe(EquipeEnum equipe) {
		this.equipe = equipe;
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
