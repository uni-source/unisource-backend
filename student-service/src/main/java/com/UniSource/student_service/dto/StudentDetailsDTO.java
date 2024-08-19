package com.UniSource.student_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class StudentDetailsDTO {
    private String name;
    private String email;
    private boolean verifiedStudent;
    private int score;
    private String description;
    private String public_id;
    private String public_url;
    private int identityId;
    private String contact;
}
