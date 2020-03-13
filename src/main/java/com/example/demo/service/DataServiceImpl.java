package com.example.demo.service;

import com.example.demo.model.dto.Image;
import com.example.demo.model.entity.ImageEntity;
import com.example.demo.repository.DataRepository;
import com.example.demo.service.interfaces.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    private final String ROOT_DIR = "images";
    private DataRepository dataRepository;

    @Autowired
    public DataServiceImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void saveData(List<Image> data) throws IOException {
        loadImages(data);
    }

    @Override
    public Iterable<String> getListImages() {
        Iterable<ImageEntity> list = dataRepository.findAll();
        List<String> stringList = new ArrayList<>();
       for(ImageEntity imageEntity : list){
           stringList.add(imageEntity.getUrl());
       }
        return stringList;
    }

    @Override
    public Iterable<Image> getImagesByAlbum() {
        return null;
    }

    private void loadImages(List<Image> data) throws IOException {
        List<ImageEntity> list = new ArrayList<>();
        for (Image image : data) {
            BufferedImage imageBuf = ImageIO.read(new URL(image.getUrl()));
            String line = image.getUrl();
            int index = line.lastIndexOf("/");
            String str = line.substring(index + 1);
            String[] array = str.split("\\.");
            File fileRootDir = new File(ROOT_DIR);
            File fileDir = new File(fileRootDir, image.getTitle());
            File file = new File(fileDir, array[0] + "."
                    + array[1]);
            if (!fileRootDir.isDirectory()) fileRootDir.mkdir();
            if (!fileDir.isDirectory()) fileDir.mkdir();
            if (!file.exists()) file.createNewFile();
            ImageIO.write(imageBuf, array[1], file);
            list.add(ImageEntity.builder()
                    .albumId(image.getAlbumId())
                    .title(image.getTitle())
                    .url(file.getAbsolutePath())
                    .localPath(file.getParent())
                    .thumbnailUrl(file.getAbsolutePath())
                    .downloadDateTime(LocalDateTime.now().toString())
                    .fileSize(file.length())
                    .build());

        }
        dataRepository.saveAll(list);
    }

}
