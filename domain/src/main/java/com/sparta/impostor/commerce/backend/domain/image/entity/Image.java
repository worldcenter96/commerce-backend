package com.sparta.impostor.commerce.backend.domain.image.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Image extends Timestamped {

    @Id
    private String img_url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
