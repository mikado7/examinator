package com.examinator.security.domain.userCredentials;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_credentials_id")
    private Long id;

    private String password;

    private Date createdDate;

    @Column(unique = true)
    private String activateToken;

    private Date activateTokenExpiryDate;

    @Column(unique = true)
    private String resetToken;

    private Date resetTokenExpiryDate;

    private Long userId;

    private boolean enabled;
}
