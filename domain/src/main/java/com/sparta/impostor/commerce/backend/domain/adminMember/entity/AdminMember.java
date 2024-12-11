package com.sparta.impostor.commerce.backend.domain.adminMember.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AdminMember extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    public AdminMember(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static AdminMember createMember(String email, String password, String name) {
        return new AdminMember(email, password, name);
    }
}
