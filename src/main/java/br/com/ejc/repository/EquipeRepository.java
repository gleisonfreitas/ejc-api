package br.com.ejc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Equipe;
import br.com.ejc.repository.equipe.EquipeRepositoryQuery;

public interface EquipeRepository extends JpaRepository<Equipe, Long>, EquipeRepositoryQuery{
	
	public List<Equipe> findByEjcCodigo(Long codigoEjc);
}
