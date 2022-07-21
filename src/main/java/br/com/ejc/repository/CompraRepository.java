package br.com.ejc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Compra;
import br.com.ejc.repository.compra.CompraRepositoryQuery;

public interface CompraRepository extends JpaRepository<Compra, Long>, CompraRepositoryQuery{

}
