package br.com.ejc.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.ejc.model.TipoLancamentoEnum;

public class LancamentoResumo {
	
	private Long codigo;
	
	private String descricao;
	
	private LocalDate data;
	
	private BigDecimal valor;
	
	private TipoLancamentoEnum tipo;

	public LancamentoResumo(Long codigo, String descricao, LocalDate data, BigDecimal valor,
			TipoLancamentoEnum tipo) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		this.tipo = tipo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoLancamentoEnum getTipo() {
		return tipo;
	}
	
	public String getNomeTipo() {
		return tipo.getDescricao();
	}

	public void setTipo(TipoLancamentoEnum tipo) {
		this.tipo = tipo;
	}

}
