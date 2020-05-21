package com.formulario.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
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

	/* query personalizado para ver clientes y facturas en una sola consulta
	 * se utiliza left para que traiga toda la data del cliente y a esta se 
	 * le una la data de factura si existe consulta para un solo cliente*/
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);
	
	
	// busqueda por nombre, apellido o email
	@Query("select c from Cliente c where c.nombre like %?1%"
		+ " OR c.email like %?1% OR c.apellido like %?1%")
    public List<Cliente> buscarPorNombre(String nombre);
	
}
