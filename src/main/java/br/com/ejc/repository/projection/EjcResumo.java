package br.com.ejc.repository.projection;

import java.time.LocalDate;

public class EjcResumo {
	
	private Long codigo;
	
	private String numero;
	
	private LocalDate inicio;
	
	private LocalDate fim;
	
	private Boolean ativo;
	
	private String igreja;

	public EjcResumo(Long codigo, String numero, LocalDate inicio, LocalDate fim, Boolean ativo, String igreja) {
		this.codigo = codigo;
		this.numero = numero;
		this.inicio = inicio;
		this.fim = fim;
		this.ativo = ativo;
		this.igreja = igreja;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFim() {
		return fim;
	}

	public void setFim(LocalDate fim) {
		this.fim = fim;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getIgreja() {
		return igreja;
	}

	public void setIgreja(String igreja) {
		this.igreja = igreja;
	}

}
