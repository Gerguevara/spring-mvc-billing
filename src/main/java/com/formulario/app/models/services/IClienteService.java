package com.formulario.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.formulario.app.models.entity.Cliente;
import com.formulario.app.models.entity.Producto;

public interface IClienteService {

	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);
	
	public Cliente findOne(long id);
	
	public void Eliminar(long id);
	
	// metodo de autocomplete
	public List<Producto> findByNombre(String term);
	
}
