CREATE DATABASE shopdb;
USE shopdb;

CREATE TABLE Product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100),
    Price DOUBLE,
    Quantity INT
);
