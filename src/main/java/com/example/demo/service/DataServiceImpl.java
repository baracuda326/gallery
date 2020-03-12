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

    @Override
    public void saveData(List<Image> data) throws IOException {
        loadImages(data);
    }

    private void loadImages(List<Image> data) throws IOException {
        for (Image image : data) {
            BufferedImage imageBuf = ImageIO.read(new URL(image.getThumbnailUrl()));
            String line = image.getThumbnailUrl();
            int index = line.lastIndexOf("/");
            String str = line.substring(index + 1);
            String[] array = str.split("\\.");
            File file = new File(array[0]);
            if (!file.exists()) {
                file.createNewFile();
            }
            ImageIO.write(imageBuf, array[1], file);
            System.out.println(file.getAbsolutePath());
        }
    }
}
