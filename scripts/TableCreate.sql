CREATE TABLE admin_member (
                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                  email VARCHAR(50) NOT NULL UNIQUE,
                                  password VARCHAR(255) NOT NULL,
                                  name VARCHAR(20) NOT NULL,
                                  created_at DATETIME NOT NULL,
                                  modified_at DATETIME NOT NULL,
                                  PRIMARY KEY (id)
);

CREATE TABLE b2b_member (
                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                  email VARCHAR(50) NOT NULL UNIQUE,
                                  password VARCHAR(255) NOT NULL,
                                  name VARCHAR(20) NOT NULL,
                                  approve_status VARCHAR(255) NOT NULL,
                                  created_at DATETIME NOT NULL,
                                  modified_at DATETIME NOT NULL,
                                  PRIMARY KEY (id)
);

CREATE TABLE b2c_member (
                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                  email VARCHAR(50) NOT NULL UNIQUE,
                                  password VARCHAR(255) NOT NULL,
                                  name VARCHAR(20) NOT NULL,
                                  status VARCHAR(255) NOT NULL,
                                  created_at DATETIME NOT NULL,
                                  modified_at DATETIME NOT NULL,
                                  PRIMARY KEY (id)
);

CREATE TABLE product (
                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                  name VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  stock_quantity INT NOT NULL,
                                  price INT NOT NULL,
                                  status VARCHAR(255) NOT NULL,
                                  category VARCHAR(255) NOT NULL,
                                  sub_category VARCHAR(255) NOT NULL,
                                  created_at DATETIME NOT NULL,
                                  modified_at DATETIME NOT NULL,
                                  PRIMARY KEY (id)
);

CREATE TABLE orders (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        tracking_number VARCHAR(50),
                        order_status VARCHAR(255) NOT NULL,
                        delivery_status VARCHAR(255) NOT NULL,
                        total_price BIGINT NOT NULL,
                        quantity BIGINT NOT NULL,
                        created_at DATETIME NOT NULL,
                        modified_at DATETIME NOT NULL,
                        member_id BIGINT,
                        product_id BIGINT,
                        PRIMARY KEY (id),
                        FOREIGN KEY (member_id) REFERENCES b2c_member(id),
                        FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE image (
                                  img_url VARCHAR(255) NOT NULL,
                                  product_id BIGINT,
                                  created_at DATETIME NOT NULL,
                                  modified_at DATETIME NOT NULL,
                                  PRIMARY KEY (img_url),
                                  FOREIGN KEY (product_id) REFERENCES product(id)
);


