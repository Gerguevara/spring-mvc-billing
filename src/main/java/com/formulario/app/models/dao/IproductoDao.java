package com.formulario.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formulario.app.models.entity.Producto;

public interface IproductoDao extends CrudRepository<Producto, Long> {

	// retorna un list de productos
	// se realiza una consulta per a nivel de objetos
	// p es un alias que recibe
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
	
}
