package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
public class UserDomain implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Column(nullable = false)
    @NotBlank(message = "Role is mandatory")
    private String role;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserDomain user = (UserDomain) obj;
        return id != 0 && id.equals(user.id); // Comparer par ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retourne le rôle de l'utilisateur avec le préfixe "ROLE_"
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return username; // Utiliser le nom d'utilisateur
    }

    @Override
    public String getPassword() {
        return password; // Retourne le mot de passe
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // L'account n'est pas expiré
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // L'account n'est pas verrouillé
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Les credentials ne sont pas expirés
    }

    @Override
    public boolean isEnabled() {
        return true; // L'account est activé
    }
}
