package com.sparta.impostor.commerce.backend.domain.product.entity;

import com.sparta.impostor.commerce.backend.common.baseentity.Timestamped;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Product extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category.SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private B2BMember member;

    private Product(String name, String description, int stockQuantity, int price,
                    ProductStatus status, Category category, Category.SubCategory subCategory, B2BMember member) {
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.status = status;
        this.category = category;
        this.subCategory = subCategory;
        this.member = member;
    }

    public static Product createProduct(String name, String description, int stockQuantity, int price,
                                        ProductStatus status, Category category, Category.SubCategory subCategory, B2BMember member) {

        return new Product(name, description, stockQuantity, price, status, category, subCategory, member);
    }

    public void updateQuantity(int updatedQuantity) {
        this.stockQuantity = updatedQuantity;
    }


    public Product changeStatus(ProductStatus productStatus) {
        this.status = productStatus;
        return this;
    }


	public static Product updateProduct(Product existingProduct, int newStockQuantity) {
		existingProduct.update(newStockQuantity);
		return existingProduct;
	}


	public void update(int newStockQuantity) {
		this.stockQuantity = newStockQuantity;
	}
}


