package com.formulario.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.formulario.app.models.entity.Cliente;
import com.formulario.app.models.entity.Factura;
import com.formulario.app.models.entity.Producto;

public interface IClienteService {

	
	// muestra todos los clientes
	public List<Cliente> findAll();
	
	// trabaja con el paginador y muestra todos los clientes de forma paginada
	public Page<Cliente> findAll(Pageable pageable);
	
	// guarda al alciente
	public void save(Cliente cliente);
	
	// encuentra a un cliente
	public Cliente findOne(long id);
	
	// borra un cliente
	public void Eliminar(long id);
	
	//buscador de clientes
	 public List<Cliente> buscarPorNombre(String nombre);
	
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
	
	//crea una sola consulta de facturas que hace joins con cliente he items
	public Factura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id);
	
	// crea una sola consulta para ver clientes y sus facturas
	public Cliente fetchByIdWithFacturas(Long id);
	
	
	
}
