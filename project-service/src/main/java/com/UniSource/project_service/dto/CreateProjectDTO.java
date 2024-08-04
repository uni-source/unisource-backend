package com.UniSource.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDTO {
    private String name;
    private String description;
    private String title;
    private String category;
    private String technologies;
    private String resource;
    private Date dueDate;
    private int organizationID;
    private int mentorID;

}
