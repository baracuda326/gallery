package com.example.demo.controller;

import com.example.demo.model.dto.Image;
import com.example.demo.service.interfaces.DataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ImageController {
    private DataService dataService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ImageController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(value = "/init")
    public List<Image> init() {
        URL myURL = null;
        List<Image> data = new ArrayList<>();
        try {
            myURL = new URL("https://s3.amazonaws.com/shielddevtest/photo.txt");
            data = objectMapper.readValue(myURL, new TypeReference<List<Image>>() {
            });
            dataService.saveData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @GetMapping(value = "/downLoad")
    public void getFile() {

    }

    @GetMapping(value = "/listImages")
    public Iterable<String> getListImages() {
        return dataService.getListImages();
    }

    @GetMapping(value = "/album")
    public Iterable<Image> getImagesByAlbum() {
        return dataService.getImagesByAlbum();
    }
}
