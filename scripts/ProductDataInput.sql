use impostor;

LOAD DATA INFILE '/var/lib/mysql-files/products.csv'
    INTO TABLE product
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (id, name, description, price, status, category, sub_category, member_id, created_at, modified_at);