package com.sparta.impostor.commerce.backend.domain.b2bMember.entity;


import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class B2BMember extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private B2BMemberStatus b2bMemberStatus;
}
