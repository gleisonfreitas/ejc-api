package br.com.ejc.repository.filter;

import br.com.ejc.model.EstadoConservacaoEnum;

public class PatrimonioFilter {
	
	private Long codigoIgreja;
	
	private String numeroSerie;
	
	private String descricao;
	
	private EstadoConservacaoEnum estado;
	
	public Long getCodigoIgreja() {
		return codigoIgreja;
	}

	public void setCodigoIgreja(Long codigoIgreja) {
		this.codigoIgreja = codigoIgreja;
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

	public void setEstado(EstadoConservacaoEnum estado) {
		this.estado = estado;
	}
}
