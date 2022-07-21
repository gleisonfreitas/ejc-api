package br.com.ejc.repository.projection;

import br.com.ejc.model.FuncaoEdgEnum;

public class NomeFuncaoResumo {
	
	private String nome;
	
	private String descricao;

	public NomeFuncaoResumo(FuncaoEdgEnum funcaoEdgEnum) {
		this.nome = funcaoEdgEnum.name();
		this.descricao = funcaoEdgEnum.getDescricao();
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
