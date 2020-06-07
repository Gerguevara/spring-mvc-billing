package com.formulario.app.models.services;

import com.formulario.app.models.dao.IUsuarioDao;
import com.formulario.app.models.entity.Role;
import com.formulario.app.models.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// Esta clase es la que maneja la iformacion para el login
@Service("jpaUserDetailServices")
public class JpaUserDetailServices implements UserDetailsService {

    //Se inyecta el usuario a travez de su objeto DAO  accesando a su entity
    private IUsuarioDao usuarioDao;

    //User Datail Respresenta un usuario autenticado
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //optenemos el Usuario
        Usuario usuario = usuarioDao.findByUsername(username);
        // se valida que el usuario Exista
        if(usuario == null){
            throw new UsernameNotFoundException("El usuario no esta no existe");
        }

        // obtenemos sus roles , primero declarando un lista para luego llenarla
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        // lo llenamos con un for_each   estos roles estan siendo regristrados al tipo de spring security
        for (Role role : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        // se valida que tenga los roles
        if(authorities.isEmpty()){
              throw  new UsernameNotFoundException("Usuario son roles asignados");
            
        }


        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnable(),
                true, true, true, authorities);
    }
}
