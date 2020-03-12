package com.example.demo.service.interfaces;

import com.example.demo.model.entity.Image;

import java.io.IOException;
import java.util.List;

public interface DataService {
    void saveData(List<Image> data) throws IOException;
}
