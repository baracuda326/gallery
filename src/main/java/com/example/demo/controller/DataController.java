package com.example.demo.controller;

import com.example.demo.model.entity.Image;
import com.example.demo.service.interfaces.DataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping
public class DataController {
    private DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    //First way
    @GetMapping(value = "/image")
    public List<Image> getImage() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        URL myURL = new URL("https://s3.amazonaws.com/shielddevtest/photo.txt");
        List<Image> data = objectMapper.readValue(myURL, new TypeReference<List<Image>>() {
        });
        System.out.println(data);
        dataService.saveData(data);
        return data;
    }


    //Second way
//    @GetMapping(value = "/images")
//    public String getFile() {
//        StringBuffer data = new StringBuffer();
//        try {
//            URL myURL = new URL("https://s3.amazonaws.com/shielddevtest/photo.txt");
//            InputStream dataStream = myURL.openConnection().getInputStream();
//            InputStreamReader isr = new InputStreamReader(dataStream, "UTF-8");
//            int c;
//            while ((c = isr.read()) != -1) {
//                data.append((char) c);
//            }
//
//            return data.toString();
//        } catch (IOException ie) {
//            ie.printStackTrace();
//        }
//        return data.toString();
//    }
}
