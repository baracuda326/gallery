package com.example.demo.repository;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface ImageDownLoadRepository {
    byte[] pathToByteArray(Path path) throws FileNotFoundException;
}
