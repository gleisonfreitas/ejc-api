package br.com.ejc.repository.projection;

import java.math.BigDecimal;

import br.com.ejc.model.EstadoConservacaoEnum;

public class PatrimonioResumo {
	
	private Long codigo;
	
	private String numeroSerie;
	
	private String descricao;
	
	private EstadoConservacaoEnum estado;
	
	private BigDecimal valor;

	public PatrimonioResumo(Long codigo, String numeroSerie, String descricao, 
			EstadoConservacaoEnum estado, BigDecimal valor) {
		this.codigo = codigo;
		this.numeroSerie = numeroSerie;
		this.descricao = descricao;
		this.estado = estado;
		this.valor = valor;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EstadoConservacaoEnum getEstado() {
		return estado;
	}
	
	public String getDescricaoEstado() {
		return this.estado.getDescricao();
	}

	public void setEstado(EstadoConservacaoEnum estado) {
		this.estado = estado;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
