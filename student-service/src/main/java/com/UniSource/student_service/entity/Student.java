package com.UniSource.student_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name="_student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean verifiedStudent;
    private int score;
    private String description;
    private int identityId;
    private String public_id;
    private String public_url;
}
