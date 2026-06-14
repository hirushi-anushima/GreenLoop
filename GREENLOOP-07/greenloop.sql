CREATE DATABASE greenloop;
USE greenloop;
CREATE TABLE inventory (

    product_id INT PRIMARY KEY AUTO_INCREMENT,

    product_name VARCHAR(100),

    quantity INT,

    reorder_level INT

);
INSERT INTO inventory(product_name, quantity, reorder_level)
VALUES
('Eco Box',100,20),
('Paper Bag',15,20),
('Compostable Bag',50,10);
SELECT*FROM inventory;
SELECT COUNT(*)FROM inventory;
SELECT*FROM greenloop;
SELECT*FROM  greenloop;
SELECT*FROM inventory;
UPDATE inventory
SET reorder_level=150
WHERE product_id=1;
