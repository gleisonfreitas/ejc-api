package br.com.ejc.repository.projection;

import br.com.ejc.model.EquipeEnum;

public class EquipeResumo {
	
	private Long codigo;
	
	private EquipeEnum nome;
	
	private String coordenadorUm;
	
	private String coordenadorDois;
	
	private String telefoneUm;
	
	private String telefoneDois;
	
	public EquipeResumo(Long codigo, EquipeEnum nome, String coordenadorUm, 
			String coordenadorDois, String telefoneUm, String telefoneDois) {
		this.codigo = codigo;
		this.nome = nome;
		this.coordenadorUm = coordenadorUm;
		this.coordenadorDois = coordenadorDois;
		this.telefoneUm = telefoneUm;
		this.telefoneDois = telefoneDois;
	}
	
	public Long getCodigo() {
		return codigo;
	}



	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public EquipeEnum getNome() {
		return nome;
	}
	
	public String getNomeEquipe() {
		return nome.getDescricao();
	}

	public void setNome(EquipeEnum nome) {
		this.nome = nome;
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

	public String getTelefoneUm() {
		return telefoneUm;
	}

	public void setTelefoneUm(String telefoneUm) {
		this.telefoneUm = telefoneUm;
	}

	public String getTelefoneDois() {
		return telefoneDois;
	}

	public void setTelefoneDois(String telefoneDois) {
		this.telefoneDois = telefoneDois;
	}
}
	
