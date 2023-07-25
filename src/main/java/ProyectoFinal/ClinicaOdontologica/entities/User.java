package ProyectoFinal.ClinicaOdontologica.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String userName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private UserRole userRole;
    public User(String nombre, String apellido ,String userName, String email, String password, UserRole userRole) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }
    public User() {
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority= new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(grantedAuthority);
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
