package com.example.demo.controller;

import com.example.demo.model.dto.Image;
import com.example.demo.model.dto.ImageResponseDto;
import com.example.demo.service.interfaces.DataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ImageController {
    private DataService dataService;
    private ObjectMapper objectMapper;
    @Value("${sourceUrl}")
    private String sourceUrl;

    @Autowired
    public ImageController(DataService dataService) {
        this.dataService = dataService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping(value = "/init")
    public List<Image> init() {
        URL myURL = null;
        List<Image> data = new ArrayList<>();
        try {
            myURL = new URL(sourceUrl);
            data = objectMapper.readValue(myURL, new TypeReference<List<Image>>() {
            });
            dataService.saveData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> getPhoto(@RequestParam("path") String path) throws FileNotFoundException {
        ByteArrayResource imageByteArr = dataService.getPhoto(path);
        return ResponseEntity
                .ok()
                .header(CacheControl.noCache().getHeaderValue())
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(imageByteArr.contentLength()).body(imageByteArr);
    }

    @GetMapping("/download2")
    public void getFile(@RequestParam("path") String path, HttpServletResponse response) {
        Path file = Paths.get(path);
        if (Files.exists(file)) {
            response.setHeader("Content-disposition", "attachment; filename=" + path);
            response.setContentType("image/jpg");
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping(value = "/listImages")
    public Iterable<String> getListImages() {
        return dataService.getListImages();
    }

    @GetMapping(value = "/album/{albumId}")
    public Iterable<ImageResponseDto> getImagesByAlbum(@PathVariable("albumId") int id) {
        return dataService.getImagesByAlbum(id);
    }
}
