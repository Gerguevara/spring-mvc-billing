package com.formulario.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	
// habilita la vista de el formulario	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false)String error,
						Model model, Principal principal, RedirectAttributes flash){
		
		/* principal representa al usuairo como un objeto
		 * si este exciste, es que ya esta logeado*/
		
		if (principal != null) {				
			flash.addFlashAttribute("success", "Ya haz iniciado session");
			return "redirect:/listar";
		}		
		
		if (error != null) {
			model.addAttribute("error", "Usuario o Password incorrecto");
		}
		
		return "login";
	}
	
	
}
