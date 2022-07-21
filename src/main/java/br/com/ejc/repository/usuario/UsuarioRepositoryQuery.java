package br.com.ejc.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ejc.model.Usuario;
import br.com.ejc.repository.filter.UsuarioFilter;


public interface UsuarioRepositoryQuery {
	
	public Page<Usuario> pesquisar(UsuarioFilter filter, Pageable pageable);

}
