package com.lucasnvs.trilho.shared.services;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final String BASE_DIR = "uploads/";

    public FileStorageService() throws IOException {
        Path basePath = Paths.get(BASE_DIR);
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }
    }

    public String saveFile(String subdirectory, MultipartFile file) {
        Path uploadPath = Paths.get(BASE_DIR, subdirectory);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return subdirectory + "/" + fileName;
    }

    public byte[] getFile(String subdirectory, String fileName) throws IOException {
        Path filePath = Paths.get(BASE_DIR, subdirectory, fileName);
        return Files.readAllBytes(filePath);
    }

    public UrlResource loadFileAsResource(String subdirectory, String fileName) throws MalformedURLException {
        Path filePath = Paths.get(BASE_DIR, subdirectory, fileName).normalize();
        return new UrlResource(filePath.toUri());
    }
}
