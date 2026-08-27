package com.autocare.vehicle.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {
    private final Path root;

    public LocalStorageService(@Value("${autocare.storage-path:${user.home}/.autocare/vehicles}") String path) {
        root = Path.of(path);
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String upload(MultipartFile file) throws IOException {
        String name = UUID.randomUUID() + "-" + Path.of(file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename()).getFileName();
        Path target = root.resolve(name);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString();
    }

    public byte[] download(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }
}
