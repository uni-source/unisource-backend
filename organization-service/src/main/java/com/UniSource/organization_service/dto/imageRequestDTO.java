package com.UniSource.organization_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class imageRequestDTO {
    private MultipartFile file;
    private String public_id;
    private int identityId;
}
