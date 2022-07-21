package br.com.ejc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.ejc.model.TipoLancamentoEnum;

public class LancamentoResumoTipoPorDia {
	
	private TipoLancamentoEnum tipo;
	
	private LocalDate data;
	
	private BigDecimal valor;
	
	public LancamentoResumoTipoPorDia(TipoLancamentoEnum tipo, LocalDate data, BigDecimal valor) {
		this.tipo = tipo;
		this.data = data;
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

}
