DROP DATABASE IF EXISTS `orderbookTest`;

CREATE DATABASE `orderbookTest`;

USE `orderbookTest`;

CREATE TABLE `market_participant` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`description` varchar(255),
	`mpid` varchar(4) NOT NULL UNIQUE,
	PRIMARY KEY (`id`)
);

CREATE TABLE `orders` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`participant_id` bigint NOT NULL,
	`stock_id` bigint NOT NULL,
	`price` DECIMAL(10,2) NOT NULL,
	`quantity` int NOT NULL,
	`side` enum('SELL', 'BUY') NOT NULL,
	`order_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
	PRIMARY KEY (`id`)
);

CREATE TABLE `stock` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`symbol` varchar(4) NOT NULL UNIQUE,
	PRIMARY KEY (`id`)
);

CREATE TABLE `trade` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`quantity` int NOT NULL,
	`trade_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
	`price` DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `trade_orders` (
	`trade_id` bigint NOT NULL,
	`order_id` bigint NOT NULL,
	PRIMARY KEY (`trade_id`,`order_id`)
);

CREATE TABLE `users` (
	`id` int NOT NULL AUTO_INCREMENT,
	`username` varchar(255) NOT NULL UNIQUE,
	`password` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);


ALTER TABLE `orders` ADD CONSTRAINT `orders_fk0` FOREIGN KEY (`participant_id`) REFERENCES `market_participant`(`id`) ON DELETE CASCADE;

ALTER TABLE `orders` ADD CONSTRAINT `orders_fk1` FOREIGN KEY (`stock_id`) REFERENCES `stock`(`id`) ON DELETE CASCADE;

ALTER TABLE `trade_orders` ADD CONSTRAINT `trade_orders_fk0` FOREIGN KEY (`trade_id`) REFERENCES `trade`(`id`) ON DELETE CASCADE;

ALTER TABLE `trade_orders` ADD CONSTRAINT `trade_orders_fk1` FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE;
