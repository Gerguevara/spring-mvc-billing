package com.formulario.app;

import com.formulario.app.models.services.JpaUserDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


	//Se inyecta el servisio del usar
	@Autowired
	
	JpaUserDetailServices userDetailsService;
	// inyeccion del bcripte
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// configura los accesos a rutas
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//En este metodo se agregam las rutas	
		http.authorizeRequests().antMatchers("/", "/css/**","js/**","/images/**"
		,"/listar","/buscar/**","/assets/**").permitAll() //define rutas publics
		.antMatchers("/ver/**").hasAnyRole("USER")		
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")	
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/ver/**").hasAnyRole("USER")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()// ejecuta las autenticaciones
		.and()
		.formLogin().loginPage("/login")// permite personalisar la ruta y vista del login
		.permitAll() // redirige hacia el formularia y es permitido para todos
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	
	



// Realiza la consulta JPA para hacer login
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {			
		// se pasa el usuario Service para construir
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);		

	}
	
	
	// metodo ignora estas rutas por cualquier peticion residual
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/js/**","/css/**","/assets/**");
	}
	

}
