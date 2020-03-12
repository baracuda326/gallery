package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequestMapping
public class ImageGalleryController {

    @GetMapping(value = "/downLoad")
    public String downLoadImage(){
        return null;
    }
}
