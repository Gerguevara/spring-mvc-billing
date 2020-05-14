package com.formulario.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.formulario.app.models.entity.Cliente;
import com.formulario.app.models.entity.Factura;
import com.formulario.app.models.entity.ItemFactura;
import com.formulario.app.models.entity.Producto;
import com.formulario.app.models.services.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura") // persiste el obj factura tomado de model hasta envio el formulario
public class FacturaController {
	
	@Autowired
	private IClienteService clienteService;
	
	
	
	@GetMapping("/form/{clienteId}")
	public String crearFactura	(@PathVariable(value="clienteId")long clienteId,
								Model model,RedirectAttributes flash) {
		
		// bucando al cliente
		Cliente cliente = clienteService.findOne(clienteId);
		// verificando que el cliente exista
		if (cliente == null) {			
			flash.addFlashAttribute("error", "Cliente no encontrado");
			return "redirect:listar";
		}
		
		// sabiendo que el cliente existe se puede crear la factura		
		Factura factura = new Factura();
		
		// relaciona la factura con el cliente
		factura.setCliente(cliente);
		
		// pasando a la vista
		model.addAttribute("factura",factura);
		model.addAttribute("titulo","Crear Factura");
		
		return "factura/form";
	}
	
	// busqueda de auto complete
	// produces indica que no regresa una vista sino otra cosa como un json
	// response body indica que no regresara una vista de thymeleaf
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public 	@ResponseBody List <Producto> cargarProductos(@PathVariable String term){
			return clienteService.findByNombre(term);
	}
	
	
	
	
	/* este metodo recibe los parametros a travez de name pq algunos
	 * parametros no estan mapeados los input contienen esos atributos
	 * por medio de un REST y la cantidad esos input tiene un mane y esos
	 * son los que reciben*/	
	
	@PostMapping("/form")
	public String guardar(Factura factura,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, 
			RedirectAttributes flash, SessionStatus status) {
				
				
				for (int i = 0; i < itemId.length; i++) {
					
					// se busca el producto en la base de datos
					Producto producto = clienteService.findProductoById(itemId[i]);
					
					// se crea una linea este elemento esta compuesto por el id y la cantidad
					ItemFactura linea = new ItemFactura();
					
					// se setea al objeto de tipo ItemFactura
					linea.setCantidad(cantidad[i]);
					linea.setProducto(producto);
					
					//se pasa al objeto general que lo alacena					
					factura.addItemFactura(linea);			
					
				}
				
				// se guarda en la base de datos				
				clienteService.saveFactura(factura);
				
				// se finaliza la session pq el objeto ya se envio a la BD
				status.setComplete();
				
				// mensaje flash
				flash.addFlashAttribute("success", "Factura Creada");

				
				
				return	"redirect:/ver/" + factura.getCliente().getId();
	}
	
	
	
}
