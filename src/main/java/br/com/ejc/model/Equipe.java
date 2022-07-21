package br.com.ejc.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="equipe")
public class Equipe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@Enumerated(EnumType.STRING)
	private EquipeEnum nome;
	
	@ManyToOne
	@JoinColumn(name="codigo_coordenador_um")
	private Pessoa coordenadorUm;
	
	@ManyToOne
	@JoinColumn(name="codigo_coordenador_dois")
	private Pessoa coordenadorDois;
	
	@ManyToOne
	@JoinColumn(name="codigo_coordenador_tres")
	private Pessoa coordenadorTres;
	
	@ManyToOne
	@JoinColumn(name="codigo_ejc")
	private Ejc ejc;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="equipe_pessoa",
			joinColumns=@JoinColumn(name="codigo_equipe"),
			inverseJoinColumns=@JoinColumn(name="codigo_pessoa"))
	private List<Pessoa> encontreiros;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public EquipeEnum getNome() {
		return nome;
	}

	public void setNome(EquipeEnum nome) {
		this.nome = nome;
	}

	public Pessoa getCoordenadorUm() {
		return coordenadorUm;
	}

	public void setCoordenadorUm(Pessoa coordenadorUm) {
		this.coordenadorUm = coordenadorUm;
	}

	public Pessoa getCoordenadorDois() {
		return coordenadorDois;
	}

	public void setCoordenadorDois(Pessoa coordenadorDois) {
		this.coordenadorDois = coordenadorDois;
	}
	
	public Pessoa getCoordenadorTres() {
		return coordenadorTres;
	}

	public void setCoordenadorTres(Pessoa coordenadorTres) {
		this.coordenadorTres = coordenadorTres;
	}

	public Ejc getEjc() {
		return ejc;
	}

	public void setEjc(Ejc ejc) {
		this.ejc = ejc;
	}

	public List<Pessoa> getEncontreiros() {
		return encontreiros;
	}

	public void setEncontreiros(List<Pessoa> encontreiros) {
		this.encontreiros = encontreiros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Equipe)) {
			return false;
		}
		Equipe other = (Equipe) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}
}
