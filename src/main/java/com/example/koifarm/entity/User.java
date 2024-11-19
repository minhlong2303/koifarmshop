package com.example.koifarm.entity;

import com.example.koifarm.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
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
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    String username;

    @Size(min = 6, message = "Password must be at least 6 character!")
    String password;

    String firstName;

    String lastName;

    @Column(unique = true)
    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Invalid Email!")
    String email;

    @Column(unique = true)
    @NotBlank(message = "Phone cannot be blank!")
    @Pattern(regexp = "^((\\+84)|0)[3|5|7|8|9][0-9]{8}$", message = "Invalid phone!")
    String phone;

    @Enumerated(EnumType.STRING)
    Role role;

    @JsonIgnore
    boolean isDeleted = false;

    //dung de phan quyen
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (this.role != null)
            authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
//        return this.phone;
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

    @OneToMany(mappedBy = "user")
    @JsonIgnore // Đảm bảo tránh vòng lặp khi trả JSON
    private List<Koi> kois;

    @OneToMany(mappedBy = "customer")
    List<Orders> orders;

    @OneToMany(mappedBy = "from")
    Set<Transactions> transactionsFrom;

    @OneToMany(mappedBy = "to")
    Set<Transactions> transactionsTo;

    @OneToMany(mappedBy = "user")
    Set<Koi> koiSet ;

    @OneToMany(mappedBy = "customer")
    private Set<Feedback> customerFeedbacks;

    float balance = 0;

    @OneToMany(mappedBy = "user")
    private List<Consignment> consignments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<BatchKoi> batchKois;



}