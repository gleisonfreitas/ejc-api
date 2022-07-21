package br.com.ejc.dto;

import java.math.BigDecimal;

import br.com.ejc.model.TipoLancamentoEnum;

public class LancamentoResumoTipo {
	
	private TipoLancamentoEnum tipo;
	
	private BigDecimal valor;
	
	public LancamentoResumoTipo(TipoLancamentoEnum tipo, BigDecimal valor) {
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoLancamentoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamentoEnum tipo) {
		this.tipo = tipo;
	}

}
