package com.formulario.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.formulario.app.models.entity.Cliente;

public interface IclienteDao extends PagingAndSortingRepository<Cliente, Long>{
	
/* Esta implmentacion de CrudRepository es necesaria para utilizar
 * queris propios de Sprin, sus parametros es primero el tipo 
 * del entity y el segundo el dipo de llava es decir el id y 
 * este es de tipo long, declarado en los parametros dela clase cliente */
	
	/*Para trabajar con trabajar con paginacion se reemplaza la implementacion
	 *  CrudRepository  por PagingAndSortingRepository, que contiene los metodos del
	 *   CrudRepository +  los suyos propios */

	
}
