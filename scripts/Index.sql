# DB 선택
USE impostor;

# 인덱스 추가
CREATE INDEX idx_product_price ON product(price);
CREATE INDEX idx_product_sub_category ON product(sub_category);
CREATE INDEX idx_product_created_at ON product(created_at);

# product 테이블의 전체 인덱스 보기
SHOW INDEX FROM product;

# 전체 데이터 중 각 서브 카테고리별 개수 및 비율 확인 쿼리
SELECT sub_category, COUNT(*) AS cnt,
       (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM product)) AS percentage_of_total
FROM product
WHERE name like '%상품%'
GROUP BY sub_category;

# 상품 검색 실행계획 확인 쿼리
explain
select
    p1_0.id,
    p1_0.category,
    p1_0.created_at,
    p1_0.description,
    p1_0.member_id,
    p1_0.modified_at,
    p1_0.name,
    p1_0.price,
    p1_0.status,
    p1_0.stock_quantity,
    p1_0.sub_category
from product p1_0
where
    p1_0.category='TOP'
  and p1_0.sub_category='SHIRT'
  and p1_0.name like '%상품%' escape '!'
    and p1_0.status='ON_SALE'
order by
    p1_0.price
limit 0, 10;