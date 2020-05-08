package com.formulario.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formulario.app.models.dao.IclienteDao;
import com.formulario.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Autowired
	private IclienteDao clienteDao;	
	

	@Override
	@Transactional(readOnly=true) //especifica que solo es una consulta de lectura si no no usar
	public List<Cliente> findAll() {
		
		return (List<Cliente>) clienteDao.findAll()  ;
	}

	
	@Override
	@Transactional(readOnly=true) //especifica que solo es una consulta de lectura si no no usar
	public Cliente findOne(long id) {
		
		return clienteDao.findById(id).orElse(null);
	}
	
	
	// metodo que aplica el pageAble
	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {		
		return clienteDao.findAll(pageable);
	}
	
	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		
		clienteDao.save(cliente);
		
	}


	@Override
	@Transactional
	public void Eliminar(long id) {
		
		clienteDao.deleteById(id);
	}

}
