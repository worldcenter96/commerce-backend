package com.sparta.impostor.commerce.backend.domain.image.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public static Image of(String imageUrl) {
        return Image.builder()
            .imgUrl(imageUrl)
            .build();
    }


    public void updateProduct(Product product){
        this.product = product;
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", imgUrl=" + imgUrl + ", product=" + (product != null ? product.getId(): "null") + "]";
    }
}
