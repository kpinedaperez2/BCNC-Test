-- Tabla de marcas (brands)
CREATE TABLE BRANDS (
                        ID INT PRIMARY KEY,
                        NAME VARCHAR(50) NOT NULL
);

-- Tabla de precios
CREATE TABLE PRICES (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        BRAND_ID INT NOT NULL,
                        START_DATE TIMESTAMP NOT NULL,
                        END_DATE TIMESTAMP NOT NULL,
                        PRICE_LIST INT NOT NULL,
                        PRODUCT_ID INT NOT NULL,
                        PRIORITY INT NOT NULL,
                        PRICE DECIMAL(10, 2) NOT NULL,
                        CURR VARCHAR(3) NOT NULL,
                        CONSTRAINT FK_BRAND FOREIGN KEY (BRAND_ID) REFERENCES BRANDS(ID)
);
