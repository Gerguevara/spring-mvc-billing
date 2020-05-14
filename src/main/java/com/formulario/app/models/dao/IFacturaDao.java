package com.formulario.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.formulario.app.models.entity.Factura;

// parametro Factura hace referencia al entity y long al id
public interface IFacturaDao extends CrudRepository<Factura, Long> {

}
