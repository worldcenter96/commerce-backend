package com.sparta.admin.member.dto.request;

import com.sparta.impostor.commerce.backend.domain.b2bMember.enums.B2BMemberStatus;
import lombok.Getter;

@Getter
public class B2BMemberStatusRequest {
  private B2BMemberStatus status;

}
