package com.UniSource.admin_service.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name="_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int identityId;
}