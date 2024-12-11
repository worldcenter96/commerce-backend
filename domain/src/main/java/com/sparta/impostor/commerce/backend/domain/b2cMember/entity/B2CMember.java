package com.sparta.impostor.commerce.backend.domain.b2cMember.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class B2CMember extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private B2CMemberStatus status;

    public B2CMember(String email, String password, String name, B2CMemberStatus b2CMemberStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.status = b2CMemberStatus;
    }

    public static B2CMember createMember(String email, String password, String name) {
        return new B2CMember(email, password, name, B2CMemberStatus.ACTIVE);
    }
}
