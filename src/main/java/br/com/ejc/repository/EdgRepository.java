package br.com.ejc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Edg;
import br.com.ejc.repository.edg.EdgRepositoryQuery;

public interface EdgRepository extends JpaRepository<Edg, Long>, EdgRepositoryQuery{
	
}
