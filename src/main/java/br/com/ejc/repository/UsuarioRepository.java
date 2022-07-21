package br.com.ejc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Usuario;
import br.com.ejc.repository.usuario.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery{

	public Optional<Usuario> findByEmailAndEjcAtivo(String email, Boolean ativo);
	
	public List<Usuario> findByPermissoesDescricao(String permissaoDescricao);
}
