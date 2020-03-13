package com.example.demo.service.interfaces;

import com.example.demo.model.dto.Image;

import java.io.IOException;
import java.util.List;

public interface DataService {
    void saveData(List<Image> data) throws IOException;
    Iterable<String> getListImages();
    Iterable<Image> getImagesByAlbum();
}
