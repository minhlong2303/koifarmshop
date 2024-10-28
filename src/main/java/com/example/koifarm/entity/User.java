package com.example.koifarm.entity;

import com.example.koifarm.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username cannot be blank!")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters!")
    @NotBlank(message = "Password cannot be blank!")  // Thêm NotBlank cho mật khẩu
    private String password;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Invalid Email!")
    private String email;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Phone cannot be blank!")
    @Pattern(regexp = "^((\\+84)|0)[3|5|7|8|9][0-9]{8}$", message = "Invalid phone!")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
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

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Koi> kois;

    @OneToMany(mappedBy = "customer")
    private List<Orders> orders;

    @OneToMany(mappedBy = "from")
    private Set<Transactions> transactionsFrom;

    @OneToMany(mappedBy = "to")
    private Set<Transactions> transactionsTo;

    @OneToMany(mappedBy = "user")
    private Set<Koi> koiSet;

    private float balance = 0;

    @OneToMany(mappedBy = "customer")
    private Set<Feedback> customerFeedbacks;

    @OneToMany(mappedBy = "shop")
    private Set<Feedback> shopFeedbacks;

    @OneToMany(mappedBy = "customer")
    private List<Consignment> consignments;
}