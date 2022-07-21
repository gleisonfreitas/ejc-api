package br.com.ejc.repository.projection;

import br.com.ejc.model.FuncaoEdgEnum;

public class EdgResumo {
	
	private Long codigo;
	
	private String nome;
	
	private FuncaoEdgEnum funcao;
	
	private String telefone;

	public EdgResumo(Long codigo, String nome, FuncaoEdgEnum funcao, String telefone) {
		this.codigo = codigo;
		this.nome = nome;
		this.funcao = funcao;
		this.telefone = telefone;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public FuncaoEdgEnum getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoEdgEnum funcao) {
		this.funcao = funcao;
	}
	
	public String getNomeFuncao() {
		return funcao.getDescricao();
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
