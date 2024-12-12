package com.sparta.admin.member.dto.request;

import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class B2CMemberSearchRequest {

  private int page = 1;
  private int size = 10;
  private String sortBy = "id";
  private String orderBy = "asc";
  private B2CMemberStatus status;


  public void setDefaults() {
    if (this.page <= 0) {
      this.page = 1;
    }
    if (this.size <= 0) {
      this.size = 10;
    }
    if (this.sortBy == null || this.sortBy.isEmpty()) {
      this.sortBy = "id";
    }
    if (this.orderBy == null || this.orderBy.isEmpty()) {
      this.orderBy = "asc";
    }
  }
}
