package br.com.ejc.repository.filter;

import br.com.ejc.model.TipoLancamentoEnum;

public class LancamentoFilter {
	
	private Long codigoEjc;
	
	private String descricao;
	
	private TipoLancamentoEnum tipo;
	
	public TipoLancamentoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamentoEnum tipo) {
		this.tipo = tipo;
	}

	public Long getCodigoEjc() {
		return codigoEjc;
	}

	public void setCodigoEjc(Long codigoEjc) {
		this.codigoEjc = codigoEjc;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
