package br.com.ejc.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="pessoa")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank
	@Column(name="nome_guerra")
	private String nomeGuerra;
	
	@NotBlank
    private String nome;
    
	@NotNull
	@Column(name="data_nasc")
    private LocalDate dataNasc;
	
	@Enumerated(EnumType.STRING)
    private SexoEnum sexo;
	
	@Enumerated(EnumType.STRING)
    private EstadoCivilEnum estadoCivil;
	
	private String email;
	
	private String telefone;
    
	@Embedded
	private Endereco endereco;
	
	private Boolean trabalhando;
	
	private Boolean encontrista;
	
	private Boolean encontreiro;
	
	@Column(name="email_enviado")
	private Boolean emailEnviado;
	
	@Column(name="coordenador")
	private Character coordenador;
	
	@Column(name="edg")
	private Character edg;
	
	@Column(name="dirigente_circulo")
	private Character dirigenteCirculo;
	
	@Valid
	@JsonIgnoreProperties("pessoa")
	@OneToOne(mappedBy="pessoa", cascade=CascadeType.ALL)
	private DadosComplementares dadosComplementares;
	
	@Valid
	@JsonIgnoreProperties("pessoa")
	@OneToOne(mappedBy="pessoa", cascade=CascadeType.ALL)
	private Filiacao filiacao;
	
	@Valid
	@JsonIgnoreProperties("pessoa")
	@OneToOne(mappedBy="pessoa", cascade=CascadeType.ALL)
	private Padrinho padrinho;
	
	@Valid
	@JsonIgnoreProperties("pessoa")
	@OneToMany(mappedBy="pessoa", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Historico> historicos;


	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNomeGuerra() {
		return nomeGuerra;
	}

	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(LocalDate dataNasc) {
		this.dataNasc = dataNasc;
	}

	public SexoEnum getSexo() {
		return sexo;
	}

	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Boolean getTrabalhando() {
		return trabalhando;
	}

	public void setTrabalhando(Boolean trabalhando) {
		this.trabalhando = trabalhando;
	}
	
	public Boolean getEncontrista() {
		return encontrista;
	}

	public void setEncontrista(Boolean encontrista) {
		this.encontrista = encontrista;
	}

	public Boolean getEncontreiro() {
		return encontreiro;
	}

	public void setEncontreiro(Boolean encontreiro) {
		this.encontreiro = encontreiro;
	}
	
	public Boolean getEmailEnviado() {
		return emailEnviado;
	}

	public void setEmailEnviado(Boolean emailEnviado) {
		this.emailEnviado = emailEnviado;
	}

	public DadosComplementares getDadosComplementares() {
		return dadosComplementares;
	}

	public void setDadosComplementares(DadosComplementares dadosComplementares) {
		this.dadosComplementares = dadosComplementares;
	}
	
	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}
	
	public Filiacao getFiliacao() {
		return filiacao;
	}

	public void setFiliacao(Filiacao filiacao) {
		this.filiacao = filiacao;
	}

	public Padrinho getPadrinho() {
		return padrinho;
	}

	public void setPadrinho(Padrinho padrinho) {
		this.padrinho = padrinho;
	}

	public Character getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Character coordenador) {
		this.coordenador = coordenador;
	}

	public Character getEdg() {
		return edg;
	}

	public void setEdg(Character edg) {
		this.edg = edg;
	}
	
	public Character getDirigenteCirculo() {
		return dirigenteCirculo;
	}

	public void setDirigenteCirculo(Character dirigenteCirculo) {
		this.dirigenteCirculo = dirigenteCirculo;
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
		if (!(obj instanceof Pessoa)) {
			return false;
		}
		Pessoa other = (Pessoa) obj;
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
