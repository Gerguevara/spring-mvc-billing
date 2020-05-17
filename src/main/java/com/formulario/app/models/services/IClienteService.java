package com.formulario.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.formulario.app.models.entity.Cliente;
import com.formulario.app.models.entity.Factura;
import com.formulario.app.models.entity.Producto;

public interface IClienteService {

	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);
	
	public Cliente findOne(long id);
	
	public void Eliminar(long id);
	
	// metodo de autocomplete
	public List<Producto> findByNombre(String term);
	// fuardar facturas
	public void saveFactura(Factura factura);
	
	// metodos ayuda a buscar un producti por Id y con el esablecer relacion con Item
	public Producto findProductoById( long id);
	
	// metodo para ver detalle de las factura
	public Factura findFacturaById(Long id);
	
	// metodo borrar una factura
	public void deleteFactura(long id);
	
	
}
