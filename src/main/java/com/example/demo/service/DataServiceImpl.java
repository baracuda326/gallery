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
        BufferedImage image = ImageIO.read(new URL(data.get(1).getThumbnailUrl()));
        String line = data.get(1).getThumbnailUrl();
        int index = line.lastIndexOf("/");
        String str = line.substring(index + 1);
        String[] array = str.split("\\.");
        System.out.println(array[0] + array[1]);
        File file = new File(array[0]);
        if (!file.exists()) {
            file.createNewFile();
        }
        ImageIO.write(image, array[1], file);
        System.out.println(file.getAbsolutePath());
    }
}
