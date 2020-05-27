package com.formulario.app;

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
	
	
	@Bean // intancia el Bcrype para la version 5 de Spring usado en el metodo configureGlobal
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// crea en memoria 2 usuarios con sus respectivos roles
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{		
		
		PasswordEncoder encoder = passwordEncoder();		
		//Este metodo encripta cada vez que se cre un password	
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		//crea el usuario en memoria
		build.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		.withUser(users.username("gerardo").password("12345").roles("USER"));
	}
	
	// metodo ignora estas rutas por cualquier peticion residual
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/js/**","/css/**","/assets/**");
	}
	

}
