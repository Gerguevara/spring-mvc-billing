package com.formulario.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	
	/* permite ver el detalle de la factura
	 * tomando com parametro su id y devolviendo la vista del detalle*/
	@GetMapping("/ver/{id}")
	public String getFactura(@PathVariable(value="id")Long id,	Model model,
	RedirectAttributes flash) {		
		
		Factura factura = clienteService.fetchByIdWithClienteWhithItemFacturaWithProducto(id);	
		
		if (factura == null) {			
			flash.addFlashAttribute("error", "Factura no encontrada");
			return "redirect:/listar";
		}
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Detalle Factura: ".concat(factura.getDescripcion()));
		return "factura/ver";
	}
	
	
	/* metodo que perimite la vista del formulario para crear la factura
	 * basado en el id de usuario que recibe, es un metodo de vist*/
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
	
	
	
	// Da una respuesta Rest, que es utilizada por el autocomplete y jquery
	// produces indica que no regresa una vista sino otra cosa como un json
	// response body indica que no regresara una vista de thymeleaf
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public 	@ResponseBody List <Producto> cargarProductos(@PathVariable String term){
			return clienteService.findByNombre(term);
	}
	
	
	
	
	/* este es el metodo de guardado, recibe los parametros a travez de name pq algunos
	 * parametros no estan mapeados los input contienen esos atributos
	 * por medio de un REST y la cantidad esos input tiene un mane y esos
	 * son los que reciben*/	
	
	@PostMapping("/form")
	public String guardar(@Valid Factura factura,
			BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, 
			RedirectAttributes flash, SessionStatus status) {
		
		// validando errores
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Error creacion de factura");
			return "factura/form";
		}
		
		if (itemId == null || itemId.length == 0  ) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "Debe incluir al menos un producto");
			return "factura/form";
		}
				
				
				
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
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		
		if(factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito!");
			return "redirect:/ver/" + factura.getCliente().getId();
		}
		
		flash.addFlashAttribute("danger", "La factura no existe en la base de datos!");		
		return "redirect:/listar";
	}
	
	
	
}
