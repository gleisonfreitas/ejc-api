package br.com.ejc.repository.projection;

import java.math.BigDecimal;

import br.com.ejc.model.TipoUnidadeEnum;

public class CompraResumo {
	
	private Long codigo;
	
	private String descricao;
	
	private TipoUnidadeEnum unidade;
	
	private BigDecimal valor;
	
	private BigDecimal quantidade;

	public CompraResumo(Long codigo, String descricao, TipoUnidadeEnum unidade, BigDecimal valor,
			BigDecimal quantidade) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.unidade = unidade;
		this.valor = valor;
		this.quantidade = quantidade;
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

	public TipoUnidadeEnum getUnidade() {
		return unidade;
	}
	
	public String getNomeUnidade() {
		return unidade.getDescricao();
	}

	public void setUnidade(TipoUnidadeEnum unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
}
