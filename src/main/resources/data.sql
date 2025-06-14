CREATE TABLE IF NOT exists `currency`(
    `id` int NOT NULL AUTO_INCREMENT,
    `currency_number` char (3) NOT NULL,
    `currency_code` char (3) NOT NULL,
    `currency_cht` varchar (50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`currency_number`),
    UNIQUE (`currency_code`)
);

INSERT INTO currency (currency_number, currency_code, currency_cht) VALUES
    ('826', 'GBP', '英鎊'),
    ('840', 'USD', '美元'),
    ('978', 'EUR', '歐元');