package com.sparta.impostor.commerce.backend.domain.adminMember;

import com.sparta.impostor.commerce.backend.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AdminMember extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;

}
