package br.com.ejc.repository.projection;

import br.com.ejc.model.EquipeEnum;

public class NomeEquipeResumo {
	
	private String nome;
	
	private String descricao;

	public NomeEquipeResumo(EquipeEnum equipeEnum) {
		this.nome = equipeEnum.name();
		this.descricao = equipeEnum.getDescricao();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
