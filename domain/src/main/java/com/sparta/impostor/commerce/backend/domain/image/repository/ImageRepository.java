package com.sparta.impostor.commerce.backend.domain.image.repository;


import com.sparta.impostor.commerce.backend.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
