package com.UniSource.project_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name="_project")
public class Project {
    private int id;
    private String name;
    private String description;
    private String title;
    private String category;
    private String technologies;
    private String resource;
    private Date dueDate;
    private String status;
    private int organizationID;
    private int mentorID;

}
