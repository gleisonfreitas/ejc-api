package br.com.ejc.repository.projection;

import br.com.ejc.model.CorEnum;

public class CirculoResumo {
	
	private Long codigo;
	
	private CorEnum cor;
	
	private String coordenadorUm;
	
	private String coordenadorDois;
	
	private String coordenadorTres;
	
	public CirculoResumo(Long codigo, CorEnum cor, String coordenadorUm, 
			String coordenadorDois, String coordenadorTres) {
		this.codigo = codigo;
		this.cor = cor;
		this.coordenadorUm = coordenadorUm;
		this.coordenadorDois = coordenadorDois;
		this.coordenadorTres = coordenadorTres;
	}
	
	public Long getCodigo() {
		return codigo;
	}



	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public CorEnum getCor() {
		return cor;
	}

	public void setCor(CorEnum cor) {
		this.cor = cor;
	}
	
	public String getNomeCor() {
		return this.cor.getCor();
	}

	public String getCoordenadorUm() {
		return coordenadorUm;
	}

	public void setCoordenadorUm(String coordenadorUm) {
		this.coordenadorUm = coordenadorUm;
	}

	public String getCoordenadorDois() {
		return coordenadorDois;
	}

	public void setCoordenadorDois(String coordenadorDois) {
		this.coordenadorDois = coordenadorDois;
	}

	public String getCoordenadorTres() {
		return coordenadorTres;
	}

	public void setCoordenadorTres(String coordenadorTres) {
		this.coordenadorTres = coordenadorTres;
	}
}
