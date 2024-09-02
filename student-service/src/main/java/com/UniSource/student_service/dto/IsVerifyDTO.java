package com.UniSource.student_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsVerifyDTO {
    private int adminIdentityId;
    private int studentId;
    private boolean verifiedStudent;
}
