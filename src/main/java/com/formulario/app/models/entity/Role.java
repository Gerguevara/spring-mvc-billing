package com.formulario.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "authorities", uniqueConstraints
        = {@UniqueConstraint(columnNames = {"user_id","authority"})})
public class Role implements Serializable {

    /*user_id no es declarado porque este ya esta declarado en la relacion de Usuario,
    * UniqueConstrain indica que ambos campos seran unicos en su relacion es decir
    * no habran repedito un conjunto de user_id y authority */



	/**************
     * Declarations
     **************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String authority;

    /*********************
      getters and setters
     ********************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    

  
	private static final long serialVersionUID = 1L;
}
