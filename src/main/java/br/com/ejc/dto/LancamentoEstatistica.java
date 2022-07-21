package br.com.ejc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.ejc.model.TipoLancamentoEnum;

public class LancamentoEstatistica {
	
	private LocalDate data;
	
	private String descricao;
	
	private TipoLancamentoEnum tipo;
	
	private BigDecimal valor;
	
	private BigDecimal saldo;
	
	public LancamentoEstatistica(LocalDate data, String descricao, TipoLancamentoEnum tipo, BigDecimal valor) {
		this.data = data;
		this.descricao = descricao;
		this.tipo = tipo;
		this.valor = valor;
		this.saldo = BigDecimal.ZERO;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public TipoLancamentoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamentoEnum tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
}
