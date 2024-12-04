package com.sparta.domain.b2bMember;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class B2bMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private ApproveStatus approveStatus;

}
