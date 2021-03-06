package com.formulario.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formulario.app.models.dao.IFacturaDao;
import com.formulario.app.models.dao.IclienteDao;
import com.formulario.app.models.dao.IproductoDao;
import com.formulario.app.models.entity.Cliente;
import com.formulario.app.models.entity.Factura;
import com.formulario.app.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {

	/*************
	 * Injecciones
	 *************/

	@Autowired
	private IclienteDao clienteDao;

	@Autowired
	private IproductoDao productoDao;

	@Autowired
	private IFacturaDao facturaDao;

	/***************************
	 * Metodos CRUD implementador
	 **************************/

	@Override
	@Transactional(readOnly = true) // especifica que solo es una consulta de lectura si no no usar
	public List<Cliente> findAll() {

		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true) // especifica que solo es una consulta de lectura si no no usar
	public Cliente findOne(long id) {

		return clienteDao.findById(id).orElse(null);
	}

	// utiliza un query personalidado para devolver la vista unida del cliente con
	// la factura
	@Override
	public Cliente fetchByIdWithFacturas(Long id) {
		return clienteDao.fetchByIdWithFacturas(id);
	}

	// metodo que aplica el pageAble
	@Override
	@Transactional(readOnly = true)
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

	// buscador de clientes
	@Override
	public List<Cliente> buscarPorNombre(String nombre) {
		return clienteDao.buscarPorNombre(nombre);
	}

	// metodo que sirve para buscar producto
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombre(term);
	}

	// guardado de factura
	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	// metodos ayuda a buscar un producti por Id y con el esablecer relacion con
	// Item
	// retorna un opcional es decir retornar algo si no se encontro
	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(long id) {
		return productoDao.findById(id).orElse(null);
	}

	// metodo para ver el detalle de la factura
	@Override
	public Factura findFacturaById(Long id) {

		return facturaDao.findById(id).orElse(null);
	}

	// utiliza un query personalizado pra devolver la data de la factura , cliente y
	// lineas
	@Override
	public Factura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id) {
		return facturaDao.fetchByIdWithClienteWhithItemFacturaWithProducto(id);
	}

	// metodo borrar una factura
	@Override
	@Transactional
	public void deleteFactura(long id) {
		facturaDao.deleteById(id);
	}

}
