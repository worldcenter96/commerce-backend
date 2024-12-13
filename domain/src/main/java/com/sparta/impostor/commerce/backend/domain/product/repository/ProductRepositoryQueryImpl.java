package com.sparta.impostor.commerce.backend.domain.product.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.impostor.commerce.backend.domain.order.entity.QOrders;
import com.sparta.impostor.commerce.backend.domain.product.entity.Product;
import com.sparta.impostor.commerce.backend.domain.product.entity.QProduct;
import com.sparta.impostor.commerce.backend.domain.product.enums.Category;
import com.sparta.impostor.commerce.backend.domain.product.enums.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ProductRepositoryQueryImpl implements ProductRepositoryQuery {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QProduct product = QProduct.product;


    @Override
    public Page<Product> searchProductsWithFilters(
            String keyword,
            ProductStatus productStatus,
            Category category,
            Category.SubCategory subCategory,
            Pageable pageable
    ) {
        BooleanBuilder whereClause = new BooleanBuilder();
        if (keyword != null && !keyword.isEmpty()) {
            whereClause.and(product.name.containsIgnoreCase(keyword));
        }
        if (productStatus != null) {
            whereClause.and(product.status.eq(productStatus));
        }
        if (category != Category.DEFAULT) {
            whereClause.and(product.category.eq(category));
        } else if (subCategory != Category.SubCategory.DEFAULT) {
            whereClause.and(product.subCategory.eq(subCategory));
        }
        JPAQuery<?> baseQuery = jpaQueryFactory.from(product).where(whereClause);


        JPAQuery<?> results = baseQuery.clone();
        JPAQuery<?> countQuery = baseQuery.clone();

        List<Product> productList = results.select(product)
                .orderBy(getOrderSpecifier(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        long count = Optional.ofNullable(countQuery.select(product.count())
                .fetchOne()).orElse(0L);
        return new PageImpl<>(productList, pageable, count);
    }

    @Override
    public Page<Product> retrieveRelatedProducts(ProductStatus productStatus, Category.SubCategory subCategory, Pageable pageable) {
        /*
        예상 메인 쿼리
        SELECT p.id, COUNT(o.product_id) AS order_count
        FROM product p
        LEFT JOIN ORDERS o
        ON p.id = o.product_id
        WHERE p.status = 'ON_SALE'
        AND p.sub_category = 'TOP'
        GROUP BY p.id
        ORDER BY order_count DESC, p.price ASC
         */
        QOrders orders = QOrders.orders;

        BooleanExpression whereClause = product.status.eq(productStatus)
                .and(subCategory != Category.SubCategory.DEFAULT ? product.subCategory.eq(subCategory) : null);

        OrderSpecifier<?>[] orderSpecifiers = {
                orders.product.id.count().desc(),
                product.price.asc()
        };

        List<Product> productList = jpaQueryFactory.select(product)
                .from(product)
                .leftJoin(orders).on(product.eq(orders.product))
                .where(whereClause)
                .groupBy(product.id)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = Optional.ofNullable(jpaQueryFactory.select(product.id.countDistinct())
                .from(product)
                .leftJoin(orders).on(product.eq(orders.product))
                .where(whereClause)
                .fetchOne()).orElse(0L);

        return new PageImpl<>(productList, pageable, count);
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder pathBuilder = new PathBuilder(Product.class, "product").get(property);
            orderSpecifiers.add(new OrderSpecifier(direction, pathBuilder));
        });
        return orderSpecifiers;
    }
}
