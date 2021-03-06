package com.example.demo.repository;

import com.example.demo.model.entity.ImageEntity;
import org.springframework.data.repository.CrudRepository;

public interface DataRepository extends CrudRepository<ImageEntity, Integer> {
    ImageEntity findFirstByUrl(String url);
    Iterable<ImageEntity> findAllByAlbumId(int id);
}
