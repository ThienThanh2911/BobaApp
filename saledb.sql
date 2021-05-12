-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: saledb
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `id` int NOT NULL AUTO_INCREMENT,
  `discount_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `percent` int DEFAULT '0',
  `created_date` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (19,'ThienThanh',75,'2021-04-28',_binary ''),(20,'ThienTuu',50,'2021-04-28',_binary ''),(21,'Boba',100,'2021-04-28',_binary '');
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_price` decimal(10,0) NOT NULL,
  `created_by` int NOT NULL,
  `created_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PAYMENT_CREATE_BY_idx` (`created_by`),
  CONSTRAINT `FK_PAYMENT_CREATE_BY` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (17,90000,19,'2021-05-05'),(18,125000,19,'2021-05-06'),(19,625000,19,'2021-05-07'),(20,625000,19,'2021-05-08'),(21,165000,19,'2021-05-09'),(22,825000,19,'2021-05-10'),(23,1375000,19,'2021-05-11'),(24,395000,9,'2021-05-12');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_details`
--

DROP TABLE IF EXISTS `payment_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_details` (
  `payment_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`payment_id`,`product_id`),
  KEY `FK_PRODUCE_PAYMENT_idx` (`payment_id`),
  KEY `FK_PRODUCE_PRODUCT_idx` (`product_id`),
  CONSTRAINT `FK_PRODUCE_PAYMENT` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
  CONSTRAINT `FK_PRODUCE_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
INSERT INTO `payment_details` VALUES (17,1),(17,3),(17,4),(18,1),(18,2),(18,3),(18,4),(19,1),(19,2),(19,3),(19,4),(20,1),(20,2),(20,3),(20,4),(21,1),(21,2),(21,3),(21,4),(21,33),(22,1),(22,2),(22,3),(22,4),(22,33),(23,1),(23,2),(23,3),(23,4),(23,33),(23,34),(23,35),(23,36),(23,37),(23,38),(23,39),(23,40),(23,41),(23,42),(23,43),(24,4),(24,33),(24,34),(24,38),(24,39),(24,40),(24,41),(24,42),(24,43);
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_by` int DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(10,0) DEFAULT '0',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `FK_PRODUCE_CREATE_BY_idx` (`created_by`),
  CONSTRAINT `FK_PRODUCE_CREATE_BY` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Hồng Trà',19,'M, L',20000,'/images/hongtra.png','2020-01-20',_binary ''),(2,'Hồng Long Bạch Ngọc',19,'M, L',35000,'/images/honglongbachngoc.png','2020-01-20',_binary ''),(3,'Matcha Đậu Đỏ',19,'M, L',30000,'/images/matchadaudo.png','2020-01-20',_binary ''),(4,'Probi Bưởi Trân Châu',19,'M, L',40000,'/images/probibuoi.png','2020-01-20',_binary ''),(33,'Sữa Tươi Trân Châu',19,'M, L',50000,'/images/probibuoi.png','2020-01-20',_binary ''),(34,'Matcha Đậu Xanh',19,'M, L',35000,'/images/matchadaudo.png','2020-01-20',_binary ''),(35,'Matcha Đậu Đen',19,'M, L',35000,'/images/matchadaudo.png','2020-01-20',_binary ''),(36,'Trà Sữa Kim Cương Đen',19,'M, L',60000,'/images/probibuoi.png','2020-01-20',_binary ''),(37,'Trà Sữa Trân Châu',19,'M, L',30000,'/images/probibuoi.png','2020-01-20',_binary ''),(38,'Lục Trà',19,'M, L',20000,'/images/hongtra.png','2020-01-20',_binary ''),(39,'Trà Xanh',19,'M, L',20000,'/images/hongtra.png','2020-01-20',_binary ''),(40,'Trà Đào',19,'M, L',30000,'/images/hongtra.png','2020-01-20',_binary ''),(41,'Trà Xoài',19,'M, L',25000,'/images/hongtra.png','2020-01-20',_binary ''),(42,'Trà Vải',19,'M, L',25000,'/images/hongtra.png','2020-01-20',_binary ''),(43,'Trà Chanh',19,'M, L',25000,'/images/hongtra.png','2020-01-20',_binary '');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_role` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (9,'Thiên Thành','ctthanh2911@gmail.com','thienthanh2911','856eae3e7373f1f786c0febfa3ec5000','vhbhjbj','0969700703','USER'),(19,'Thiên Tựu','thientuu2911@gmail.com','thientuu2911','1c22e0f5c09af510a15da64a6499e3df','hjhjknkllk','0901310332','ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-12 21:53:27
