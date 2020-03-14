package com.example.demo.service;

import com.example.demo.model.dto.Image;
import com.example.demo.model.dto.ImageResponseDto;
import com.example.demo.model.entity.ImageEntity;
import com.example.demo.repository.DataRepository;
import com.example.demo.repository.ImageDownLoadRepository;
import com.example.demo.service.interfaces.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    private final String ROOT_DIR = "images";
    private DataRepository dataRepository;
    private ImageDownLoadRepository imageDownLoadRepository;

    @Autowired
    public DataServiceImpl(DataRepository dataRepository,ImageDownLoadRepository imageDownLoadRepository) {
        this.dataRepository = dataRepository;
        this.imageDownLoadRepository = imageDownLoadRepository;
    }

    @Override
    public void saveData(List<Image> data) throws IOException {
        loadImages(data);
    }

    @Override
    public Iterable<String> getListImages() {
        Iterable<ImageEntity> list = dataRepository.findAll();
        List<String> stringList = new ArrayList<>();
        for (ImageEntity imageEntity : list) {
            stringList.add(imageEntity.getUrl());
        }
        return stringList;
    }

    @Override
    public Iterable<ImageResponseDto> getImagesByAlbum(int id) {
        return mapper(dataRepository.findAllByAlbumId(id));
    }

    @Override
    public ByteArrayResource getPhoto(String path) throws FileNotFoundException {
        return new ByteArrayResource(imageDownLoadRepository.pathToByteArray(Paths.get(path)));
    }

    private Iterable<ImageResponseDto> mapper(Iterable<ImageEntity> allByAlbumId) {
        List<ImageResponseDto> imageList = new ArrayList<>();
        for (ImageEntity imageEntity : allByAlbumId){
            imageList.add(ImageResponseDto.builder()
                    .albumId(imageEntity.getAlbumId())
                    .id(imageEntity.getId())
                    .title(imageEntity.getTitle())
                    .url(imageEntity.getUrl())
                    .localPath(imageEntity.getLocalPath())
                    .thumbnailUrl(imageEntity.getThumbnailUrl())
                    .downloadDateTime(imageEntity.getDownloadDateTime())
                    .fileSize(imageEntity.getFileSize())
            .build());
        }
        return imageList;
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
            if (!isValidFormat(array[1])) array[1] = "jpg";
            File file = new File(fileDir, array[0] + "."
                    + array[1]);
            if (!fileRootDir.isDirectory()) fileRootDir.mkdir();
            if (!fileDir.isDirectory()) fileDir.mkdir();
            if (!file.exists()) file.createNewFile();
            ImageIO.write(imageBuf, array[1], file);
            if (dataRepository.findFirstByUrl(file.getAbsolutePath()) == null) {
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
        }
        dataRepository.saveAll(list);
    }

    private boolean isValidFormat(String s) {
        String[] imageFormat = {"jpg", "bmp", "jpeg", "tiff", "bmp", "gif", "png"};
        for (int i = 0; i < imageFormat.length; i++) {
            if (s.equalsIgnoreCase(imageFormat[i])) return true;
        }
        return false;
    }
}
