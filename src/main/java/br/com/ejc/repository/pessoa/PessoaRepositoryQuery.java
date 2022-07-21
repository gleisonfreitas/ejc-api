package br.com.ejc.repository.pessoa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.model.Pessoa;
import br.com.ejc.repository.filter.PessoaFilter;
import br.com.ejc.repository.projection.PessoaResumo;

public interface PessoaRepositoryQuery {
	
	public Page<Pessoa> pesquisar(PessoaFilter pessoaFilter, Pageable pageable);
	
	public Page<PessoaResumo> resumir(PessoaFilter pessoaFilter, Pageable pageable);
	
	public List<PessoaResumo> listarPessoasEdg();
	
	public List<PessoaResumo> listarPessoasCoordenador();
	
	public List<PessoaResumo> listarPessoasDirigenteCirculo();
	
	public List<PessoaResumo> listarEncontreirosDisponiveis();
	
	public List<PessoaResumo> listarEncontristasDisponiveis();

}
