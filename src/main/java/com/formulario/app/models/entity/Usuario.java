package com.formulario.app.models.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class Usuario  implements Serializable {

  

	/**************
     * Declarations
     **************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(length = 25, unique = true)
    private String username;

    @Column(length = 60)
    private String password;

    private  Boolean enabled;

    // Establece la relacion con los roles
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id") // indica la relacion al apartado user id en la tabla de roles
    private List<Role> roles;

    /********************
    * Getters and Setters
    *********************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enabled;
    }

    public void setEnable(Boolean enable) {
        this.enabled = enable;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    /**
  	 * 
  	 */
  	private static final long serialVersionUID = 1L;

}
