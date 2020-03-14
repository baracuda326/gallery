package com.example.demo.service.interfaces;

import com.example.demo.model.dto.Image;
import com.example.demo.model.dto.ImageResponseDto;
import org.springframework.core.io.ByteArrayResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface DataService {
    void saveData(List<Image> data) throws IOException;
    Iterable<String> getListImages();
    Iterable<ImageResponseDto> getImagesByAlbum(int id);
    ByteArrayResource getPhoto(String path) throws FileNotFoundException;
}
