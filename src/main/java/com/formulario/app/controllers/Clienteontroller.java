package com.formulario.app.controllers;


import java.io.IOException;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.formulario.app.models.dao.IclienteDao;
import com.formulario.app.models.entity.Cliente;
import com.formulario.app.models.services.IClienteService;
import com.formulario.app.models.services.IUploadFileService;
import com.formulario.app.utils.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class Clienteontroller {

	@Autowired
	private IUploadFileService uploadService;

	@Autowired // implmentendo la interfaz
	private IClienteService clienteService;

	/******************
	 **** METODOS***
	 ***************/

	// ver el perfil de un cliente
	@RequestMapping("/ver/{id}")
	public String getProfile(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "Cliente no encontrado");
			return "redirect:/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Perfil del cliente");

		return "ver";
	}

// metodo para la vista que lista todos los registros con paginacion
	@RequestMapping({"/listar","/"})
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		// se declara el apginable con su metodo estatico
		Pageable pageRequest = PageRequest.of(page, 5);

		// se utiliza el metodo de el servicio
		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		// se crea el objeto paginador que realiza el calculo de paginas
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		// titulo
		model.addAttribute("titulo", "Listado de clientes");

		// se pasa el objeto pageRender que realiza los calculos a la vista
		model.addAttribute("page", pageRender);

		/*
		 * se reemplaza el metodo clienteService.findAll()
		 * model.addAttribute("clientes", clienteService.findAll());
		 */
		model.addAttribute("clientes", clientes);

		return "listar";
	}

// metodo para buscar un cliente	
	@PostMapping("/buscar")
	public String buscar( @RequestParam(name = "nombre") String nombre, Model model) {

		List<Cliente> clientes = clienteService.buscarPorNombre(nombre);

		// titulo
		model.addAttribute("titulo", "Resultado de busqueda");
		model.addAttribute("clientes", clientes);

		return "/resultados";
	}

// en este caso se utiliza map solo para variar
	@RequestMapping("/form")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		/*
		 * seirve como objeto de negocio tanto para el formulario como para la clase
		 * mapeada a la BD
		 */
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de clientes"); // se usa .put pq se esta utilizando Map

		return "form";
	}

	/*
	 * metodo que se encarga de procesar los datos enviador por el formulario,
	 * basicamente solo recibe el objeto guardado del formulario y utiliza el metodo
	 * guardar
	 */

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		// verifica errores en el formulario sino retorna la vista con mensajes de error
		// activos
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de clientes");
			return "form";
		}

		// verifica que exista la foto y la guarda (pero son varios if anidados)
		if (!foto.isEmpty()) {
			// verifica si existe la foto y la elimina
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0 && cliente.getFoto() != "") {
				// Elimina la foto
				uploadService.delete(cliente.getFoto());
			}

			String uniqueFilename = null;
			try {
				// intenta guardar la foto
				uniqueFilename = uploadService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			cliente.setFoto(uniqueFilename);
		} else {
			// si no viene la foto asigna este valor por defecto
			cliente.setFoto("");
		} // fin

		// determina si es una edicion o creacion y asigna se usar para el mensaje flash
		String mensajeFlah = (cliente.getId() != null) ? "Editado Correctamente" : "Guardado Correctamente";

		// aca se guarda la data
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlah);
		return "redirect:listar";
	}

	// metodo handler para editar un cliente
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Cliente cliente = null;

		if (id > 0) {

			cliente = clienteService.findOne(id);
		} else {

			return "rediect:/listar";
		}

		model.put("cliente", cliente);
		model.put("titulo", "Editar de clientes");

		return "/form";
	}

	// borrar un cliente
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			Cliente cliente = clienteService.findOne(id);
			clienteService.Eliminar(id);
			flash.addFlashAttribute("danger", "Usuario Eliminado");
			uploadService.delete(cliente.getFoto());

		}

		return "redirect:/listar";
	}

}