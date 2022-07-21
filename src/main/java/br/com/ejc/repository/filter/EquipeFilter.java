package br.com.ejc.repository.filter;

import br.com.ejc.model.EquipeEnum;

public class EquipeFilter {
	
	private Long codigo;
	
	private Long codigoEjc;
	
	private EquipeEnum nome;
	
	private String coordenador;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Long getCodigoEjc() {
		return codigoEjc;
	}

	public void setCodigoEjc(Long codigoEjc) {
		this.codigoEjc = codigoEjc;
	}

	public EquipeEnum getNome() {
		return nome;
	}

	public void setNome(EquipeEnum nome) {
		this.nome = nome;
	}

	public String getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(String coordenador) {
		this.coordenador = coordenador;
	}

}
