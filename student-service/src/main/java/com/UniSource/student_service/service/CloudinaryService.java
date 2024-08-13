package com.UniSource.student_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map uploadImage(byte[] file) throws IOException {
        return cloudinary.uploader().upload(file, ObjectUtils.asMap(
                "folder", "uni-source/students",
                "width", 150
        ));
    }

    public Map deleteImage(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}