package br.com.ejc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Igreja;
import br.com.ejc.repository.igreja.IgrejaRepositoryQuery;

public interface IgrejaRepository extends JpaRepository<Igreja, Long>, IgrejaRepositoryQuery{

}
