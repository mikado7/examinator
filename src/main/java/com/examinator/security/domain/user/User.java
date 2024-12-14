package com.examinator.security.domain.user;

import com.examinator.security.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email
    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.authority = user.getAuthority();
    }

    public User(String username, Authority authority) {
        this.username = username;
        this.authority = authority;
    }
}
