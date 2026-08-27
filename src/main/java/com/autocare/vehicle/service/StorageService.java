package com.autocare.vehicle.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String upload(MultipartFile file) throws IOException;

    byte[] download(String path) throws IOException;
}
