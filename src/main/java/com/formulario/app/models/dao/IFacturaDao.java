package com.formulario.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formulario.app.models.entity.Factura;

// parametro Factura hace referencia al entity y long al id
public interface IFacturaDao extends CrudRepository<Factura, Long> {

// reduce los querys a una sola consulta	
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Factura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id);
	
}
