package com.UniSource.student_service.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlnvwbwye",
                "api_key", "665175246251817",
                "api_secret", "xyZh2wN2cFH7-aEgLB666M8zhO8"
        ));
    }
}