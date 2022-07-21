package br.com.ejc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ejc.model.Circulo;
import br.com.ejc.repository.circulo.CirculoRepositoryQuery;

public interface CirculoRepository extends JpaRepository<Circulo, Long>, CirculoRepositoryQuery{

	public List<Circulo> findByEjcCodigo(Long codigoEjc);
}
