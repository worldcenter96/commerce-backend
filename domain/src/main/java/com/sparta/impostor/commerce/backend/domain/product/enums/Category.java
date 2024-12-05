package com.sparta.impostor.commerce.backend.domain.product.enums;

import lombok.Getter;

/**
 * 현재 카테고리, 서브 카테고리는 남성의류를 겨냥하여 작성되었음
 */
@Getter
public enum Category {
    TOP(SubCategory.TOP_SUBCATEGORIES),
    BOTTOM(SubCategory.BOTTOM_SUBCATEGORIES),
    OUTERWEAR(SubCategory.OUTERWEAR_SUBCATEGORIES),
    ACCESSORIES(SubCategory.ACCESSORIES_SUBCATEGORIES),
    FOOTWEAR(SubCategory.FOOTWEAR_SUBCATEGORIES);


    private final SubCategory[] subCategories;

    Category(SubCategory[] subCategories) {
        this.subCategories = subCategories;
    }


    @Getter
    public enum SubCategory {
        T_SHIRT, SHIRT, KNIT,
        JEANS, SLACKS, SHORTS,
        COATS, JACKETS, PARKAS, CARDIGANS,
        HAT, BELT, GLOVES,
        SNEAKERS, BOOTS, DRESS_SHOES;

        public static final SubCategory[] TOP_SUBCATEGORIES = {
                T_SHIRT,
                SHIRT,
                KNIT
        };

        public static final SubCategory[] BOTTOM_SUBCATEGORIES = {
                JEANS,
                SLACKS,
                SHORTS
        };

        public static final SubCategory[] OUTERWEAR_SUBCATEGORIES = {
                JACKETS,
                COATS,
                PARKAS,
                CARDIGANS
        };

        public static final SubCategory[] ACCESSORIES_SUBCATEGORIES = {
                HAT,
                BELT,
                GLOVES
        };

        public static final SubCategory[] FOOTWEAR_SUBCATEGORIES = {
                SNEAKERS,
                BOOTS,
                DRESS_SHOES
        };
    }
}
