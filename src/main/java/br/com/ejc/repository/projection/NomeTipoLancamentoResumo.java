package br.com.ejc.repository.projection;

import br.com.ejc.model.TipoLancamentoEnum;

public class NomeTipoLancamentoResumo {
	
	private String nome;
	
	private String descricao;

	public NomeTipoLancamentoResumo(TipoLancamentoEnum tipoLancamentoEnum) {
		this.nome = tipoLancamentoEnum.name();
		this.descricao = tipoLancamentoEnum.getDescricao();
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
