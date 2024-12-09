package com.sparta.impostor.commerce.backend.domain.b2bMember.entity;


import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sparta.impostor.commerce.backend.domain.b2bMember.enums.ApproveStatus.APPROVED;
import static com.sparta.impostor.commerce.backend.domain.b2bMember.enums.ApproveStatus.PENDING;

@Entity
@Getter
@NoArgsConstructor
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

    public B2BMember(String email, String password, String name, B2BMemberStatus b2bMemberStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.b2bMemberStatus = b2bMemberStatus;
    }

    public static B2BMember createMember(String email, String password, String name) {
        return new B2BMember(email, password, name, B2BMemberStatus.PENDING);
    }
}
