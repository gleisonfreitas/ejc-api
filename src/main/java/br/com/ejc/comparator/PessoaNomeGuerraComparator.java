package br.com.ejc.comparator;

import java.util.Comparator;

import br.com.ejc.model.Pessoa;

public class PessoaNomeGuerraComparator implements Comparator<Pessoa>{
	
	public int compare(Pessoa p1, Pessoa p2) {
		return p1.getNomeGuerra().compareTo(p2.getNomeGuerra());
	}

}
