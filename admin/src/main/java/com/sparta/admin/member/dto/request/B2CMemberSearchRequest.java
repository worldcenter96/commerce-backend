package com.sparta.admin.member.dto.request;

import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class B2CMemberSearchRequest {

  private int page;
  private int size;
  private String sortBy;
  private String orderBy;
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
