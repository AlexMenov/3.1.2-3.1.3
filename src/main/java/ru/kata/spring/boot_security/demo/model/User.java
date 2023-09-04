package ru.kata.spring.boot_security.demo.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor()
@Table(name = "users", catalog = "users")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 30, min = 3)
    @NotNull
    private String username;

    @Column(name = "last_name")
    @Size(max = 58)
    private String lastName;

    @Column
    @Min(0)
    private Integer age;

    @Column
    @Email
    @NotNull
    private String email;

    @Column
    @Min(8)
    @NotNull
    private String password;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns =
            @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Collection<String> getUserRoles() {
        return this
                .getRoles()
                .stream()
                .sorted(Comparator.comparing(Role::getRole))
                .map(role -> role.getRole().toLowerCase())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
