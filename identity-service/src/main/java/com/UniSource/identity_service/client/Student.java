package com.UniSource.identity_service.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private boolean isVerified;
    private int score;
    private String description;
    private int identityId;
}
