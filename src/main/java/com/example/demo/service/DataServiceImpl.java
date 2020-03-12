package com.example.demo.service;

import com.example.demo.model.entity.Image;
import com.example.demo.service.interfaces.DataService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
private final String ROOT_DIR = "images";
private final String IMAGE_MINI_DIR = "mini";
    @Override
    public void saveData(List<Image> data) throws IOException {
        loadImages(data);
    }

    private void loadImages(List<Image> data) throws IOException {
        for (Image image : data) {
            BufferedImage imageBuf = ImageIO.read(new URL(image.getUrl()));
            String line = image.getUrl();
            int index = line.lastIndexOf("/");
            String str = line.substring(index + 1);
            String[] array = str.split("\\.");
            File fileRootDir = new File(ROOT_DIR);
            File fileDir = new File(fileRootDir,image.getTitle());
            File file = new File(fileDir,array[0]);
            if (!fileRootDir.isDirectory())fileRootDir.mkdir();
            if (!fileDir.isDirectory())fileDir.mkdir();
            if (!file.exists()) file.createNewFile();
            ImageIO.write(imageBuf, array[1], file);
            System.out.println(file.getAbsolutePath());
        }
    }
}
