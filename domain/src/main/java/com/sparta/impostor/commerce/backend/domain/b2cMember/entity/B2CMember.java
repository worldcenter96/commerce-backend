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

  @Getter
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private B2CMemberStatus b2cMemberStatus; // PENDING, ACTIVE, INACTIVE


  public static B2CMember createMember(String email, String password, String name) {
    B2CMember member = new B2CMember();
    member.email = email;
    member.password = password;
    member.name = name;
    return member;
  }

  // 상태 변경 메서드
  public B2CMember changeStatus(B2CMemberStatus status) {
    this.b2cMemberStatus = status;
    return this;
  }

  // 명시적 추가
  public B2CMemberStatus getStatus() {
    return this.b2cMemberStatus;
  }

}
