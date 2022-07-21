package br.com.ejc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

}
