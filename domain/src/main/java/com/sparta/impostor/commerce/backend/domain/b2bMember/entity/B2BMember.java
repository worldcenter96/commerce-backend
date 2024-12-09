package com.sparta.impostor.commerce.backend.domain.b2bMember.entity;


import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private B2BMemberStatus b2BMemberStatus;

    public B2BMember(String email, String password, String name, B2BMemberStatus b2BMemberStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.b2BMemberStatus = b2BMemberStatus;
    }

    public static B2BMember createMember(String email, String password, String name) {
        return new B2BMember(email, password, name, B2BMemberStatus.PENDING);
    }

    public B2BMember changeStatus(B2BMemberStatus newStatus) {
        return new B2BMember(email,password,name,newStatus);
    }
}
