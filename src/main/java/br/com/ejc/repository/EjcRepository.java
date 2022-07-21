package br.com.ejc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Ejc;
import br.com.ejc.repository.ejc.EjcRepositoryQuery;

public interface EjcRepository extends JpaRepository<Ejc, Long>, EjcRepositoryQuery{

}
