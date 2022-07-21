package br.com.ejc.repository.projection;

import br.com.ejc.model.CorEnum;

public class NomeCorResumo {
	
	private CorEnum corEnum;
	
	public NomeCorResumo(CorEnum corEnum) {
		this.corEnum = corEnum;
	}

	public CorEnum getCorEnum() {
		return corEnum;
	}

	public void setCorEnum(CorEnum corEnum) {
		this.corEnum = corEnum;
	}
	
	public String getNomeCor() {
		return corEnum.getCor();
	}
	
	public String getHex() {
		return corEnum.getHex();
	}
	
	

}
