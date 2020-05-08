INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00001', 'Gerardo', 'Guevara', 'email@master', '2020-10-18','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto ) VALUES('00002', 'Ignacio', 'Guevara', 'email@master2', '2020-10-19','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00003', 'Jaimito', 'Guevara', 'email@master3', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00004', 'Mario', 'Guevara', 'email@master4', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00005', 'Luis', 'Guevara', 'email@master5', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00006', 'Roberto', 'Guevara', 'email@master6', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00007', 'Bertha', 'Guevara', 'email@master5', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00008', 'Moises', 'Guevara', 'email@master6', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00009', 'Gerardo', 'Guevara', 'email@master', '2020-10-18','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00010', 'Ignacio', 'Guevara', 'email@master2', '2020-10-19','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00011', 'Jaimito', 'Guevara', 'email@master3', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00012', 'Mario', 'Guevara', 'email@master4', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00013', 'Luis', 'Guevara', 'email@master5', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00014', 'Roberto', 'Guevara', 'email@master6', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00015', 'Bertha', 'Guevara', 'email@master5', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00016', 'Moises', 'Guevara', 'email@master6', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00017', 'Gerardo', 'Guevara', 'email@master', '2020-10-18','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00018', 'Ignacio', 'Guevara', 'email@master2', '2020-10-19','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00019', 'Jaimito', 'Guevara', 'email@master3', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00020', 'Mario', 'Guevara', 'email@master4', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00021', 'Luis', 'Guevara', 'email@master5', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00022', 'Roberto', 'Guevara', 'email@master6', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00023', 'Bertha', 'Guevara', 'email@master5', '2020-10-20','');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES('00024', 'Moises', 'Guevara', 'email@master6', '2020-10-20','');

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);


INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);