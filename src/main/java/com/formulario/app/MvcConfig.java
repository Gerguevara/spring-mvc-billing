package com.formulario.app;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*Esta clase sirve para configurar un nuevo directorio de recursos para guardar
 * imagenes*/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	
/*Es metodo sirve para agregar nuevos directrorios recursos*/
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		//consigue la ruta concatenando ruta relativa y absoluta
		String resoursePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		
		// registra un tipo de ruta chachada por dare un nombre
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations(resoursePath );
		// registra la ruta fisica, la verdadera en el equipo
		
	}
	
	// metodo que devuelve vistas simples sin mayo logica
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	
	//intanciacion del bycrypte
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	
}
