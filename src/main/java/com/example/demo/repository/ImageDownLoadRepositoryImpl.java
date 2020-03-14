package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class ImageDownLoadRepositoryImpl implements ImageDownLoadRepository {
    @Override
    public byte[] pathToByteArray(Path path) throws FileNotFoundException {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FileNotFoundException("Picture was not found!");
        }
    }
}
