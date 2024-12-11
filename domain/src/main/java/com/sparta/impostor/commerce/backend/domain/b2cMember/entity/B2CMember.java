package com.sparta.impostor.commerce.backend.domain.b2cMember.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
  private B2CMemberStatus b2cMemberStatus; // PENDING, ACTIVE, INACTIVE

  // 권한 요청 시간
  private LocalDateTime requestedAt;

  // 상태 변경 시간
  private LocalDateTime statusChangedAt;


  public B2CMember(String email, String password, String name, B2CMemberStatus b2CMemberStatus) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.b2cMemberStatus = b2CMemberStatus;
    this.requestedAt = null; // 요청하지 않은 상태라면 기본적으로 null
    this.statusChangedAt = null; // 상태가 변경되지 않았다면 null
  }

  // 권한 요청을 받은 경우 요청 시간을 설정
  public void requestPermission(){
    this.requestedAt = LocalDateTime.now(); // 요청 시점 기록
  }

  // 승인 처리
  public void approveMember() {
    this.b2cMemberStatus = B2CMemberStatus.INACTIVE;
    this.statusChangedAt = LocalDateTime.now(); // 승인 시 상태 변경 시간 기록
  }

  // 거절 처리
  public void rejectMember() {
    this.b2cMemberStatus = B2CMemberStatus.INACTIVE;
    this.statusChangedAt = LocalDateTime.now(); // 거절 시 상태 변경 시간 기록
  }
}
