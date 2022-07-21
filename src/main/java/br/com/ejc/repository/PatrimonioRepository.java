package br.com.ejc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Patrimonio;
import br.com.ejc.repository.patrimonio.PatrimonioRepositoryQuery;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long>, PatrimonioRepositoryQuery{

}
