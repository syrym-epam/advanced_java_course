
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);


INSERT INTO product (name, price) VALUES ('Wireless Mechanical Keyboard', 89.99);
INSERT INTO product (name, price) VALUES ('Ergonomic Mouse', 49.50);
INSERT INTO product (name, price) VALUES ('UltraWide 34-inch Monitor', 399.00);
INSERT INTO product (name, price) VALUES ('USB-C Multi-Port Hub', 29.99);
INSERT INTO product (name, price) VALUES ('Noise-Canceling Headphones', 199.99);