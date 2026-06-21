-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: medic_inventory
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `medic_inventory`
--

/*!40000 DROP DATABASE IF EXISTS `medic_inventory`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `medic_inventory` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `medic_inventory`;

--
-- Table structure for table `alertas`
--

DROP TABLE IF EXISTS `alertas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alertas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activa` int DEFAULT NULL,
  `disparada_en` datetime(6) DEFAULT NULL,
  `nivel` varchar(20) DEFAULT NULL,
  `nota` varchar(255) DEFAULT NULL,
  `resuelta_en` datetime(6) DEFAULT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  `sede_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKobekdf8a0wgjehkrkjmunucpg` (`producto_id`),
  KEY `FKalanc2395ru4dpbd8q1u4l7l5` (`sede_id`),
  CONSTRAINT `FKalanc2395ru4dpbd8q1u4l7l5` FOREIGN KEY (`sede_id`) REFERENCES `sedes` (`id`),
  CONSTRAINT `FKobekdf8a0wgjehkrkjmunucpg` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alertas`
--

LOCK TABLES `alertas` WRITE;
/*!40000 ALTER TABLE `alertas` DISABLE KEYS */;
INSERT INTO `alertas` VALUES (50,1,'2026-06-20 18:50:45.000000','ALTA',NULL,NULL,'STOCK_CRITICO',4,1),(51,1,'2026-06-20 18:50:45.000000','MEDIA',NULL,NULL,'STOCK_BAJO',1,1),(52,0,'2025-12-01 12:00:01.478291','MEDIA','Cobertura: 7 días (umbral: 15 días, consumo diario: 2,7 unidades)','2026-06-02 21:57:57.733653','COBERTURA_BAJA',1,1),(53,0,'2026-06-01 23:00:00.035160','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-01 23:00:00.152943','PEDIDO_SIN_ACUSE',5,1),(54,0,'2026-06-01 23:00:00.046428','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-01 23:00:00.138869','PEDIDO_SIN_ACUSE',4,1),(55,0,'2026-06-01 23:00:00.053356','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(56,0,'2026-06-01 23:00:00.059563','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(57,0,'2026-06-01 23:00:00.066166','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(58,0,'2026-06-01 23:00:00.071491','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(59,0,'2026-06-01 23:00:00.077612','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(60,0,'2026-06-01 23:00:00.084194','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(61,0,'2026-06-01 23:00:00.089779','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(62,0,'2026-06-01 23:00:00.096330','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-01 23:00:00.139373','PEDIDO_SIN_ACUSE',4,1),(63,0,'2026-06-02 00:00:00.312192','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-02 00:00:00.464944','PEDIDO_SIN_ACUSE',5,1),(64,0,'2026-06-02 00:00:00.332627','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(65,0,'2026-06-02 00:00:00.340792','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(66,0,'2026-06-02 00:00:00.348312','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(67,0,'2026-06-02 00:00:00.355246','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(68,0,'2026-06-02 00:00:00.362750','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(69,0,'2026-06-02 00:00:00.370357','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(70,0,'2026-06-02 00:00:00.377751','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(71,0,'2026-06-02 00:00:00.385805','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(72,0,'2026-06-02 00:00:00.392814','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-02 00:00:00.448132','PEDIDO_SIN_ACUSE',4,1),(73,0,'2026-06-02 22:00:00.078709','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-02 22:00:00.639784','PEDIDO_SIN_ACUSE',5,1),(74,0,'2026-06-02 22:00:00.126860','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(75,0,'2026-06-02 22:00:00.134893','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(76,0,'2026-06-02 22:00:00.147958','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(77,0,'2026-06-02 22:00:00.158824','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(78,0,'2026-06-02 22:00:00.169633','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(79,0,'2026-06-02 22:00:00.179901','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(80,0,'2026-06-02 22:00:00.190426','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(81,0,'2026-06-02 22:00:00.198606','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-02 22:00:00.612686','PEDIDO_SIN_ACUSE',4,1),(82,0,'2026-06-02 22:00:00.207984','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-02 22:00:00.613692','PEDIDO_SIN_ACUSE',4,1),(83,0,'2026-06-02 23:00:00.469416','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-02 23:00:01.929740','PEDIDO_SIN_ACUSE',5,1),(84,0,'2026-06-02 23:00:00.514919','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-02 23:00:01.924741','PEDIDO_SIN_ACUSE',4,1),(85,0,'2026-06-02 23:00:00.522914','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-02 23:00:01.918554','PEDIDO_SIN_ACUSE',4,1),(86,0,'2026-06-02 23:00:00.529529','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-02 23:00:01.913553','PEDIDO_SIN_ACUSE',4,1),(87,0,'2026-06-02 23:00:00.536550','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-02 23:00:01.907042','PEDIDO_SIN_ACUSE',4,1),(88,0,'2026-06-02 23:00:00.543571','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-02 23:00:01.900687','PEDIDO_SIN_ACUSE',4,1),(89,0,'2026-06-02 23:00:00.550599','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-02 23:00:01.894180','PEDIDO_SIN_ACUSE',4,1),(90,0,'2026-06-02 23:00:00.559136','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-02 23:00:01.888158','PEDIDO_SIN_ACUSE',4,1),(91,0,'2026-06-02 23:00:00.566642','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-02 23:00:01.882153','PEDIDO_SIN_ACUSE',4,1),(92,0,'2026-06-02 23:00:00.576669','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-02 23:00:01.873559','PEDIDO_SIN_ACUSE',4,1),(93,0,'2026-06-03 00:00:00.480593','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-03 00:00:18.776351','PEDIDO_SIN_ACUSE',5,1),(94,0,'2026-06-03 00:00:00.507612','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-03 00:00:18.770339','PEDIDO_SIN_ACUSE',4,1),(95,0,'2026-06-03 00:00:00.515341','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-03 00:00:18.764834','PEDIDO_SIN_ACUSE',4,1),(96,0,'2026-06-03 00:00:00.522853','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-03 00:00:18.758327','PEDIDO_SIN_ACUSE',4,1),(97,0,'2026-06-03 00:00:00.528854','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-03 00:00:18.754327','PEDIDO_SIN_ACUSE',4,1),(98,0,'2026-06-03 00:00:00.536771','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-03 00:00:18.747312','PEDIDO_SIN_ACUSE',4,1),(99,0,'2026-06-03 00:00:00.543812','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-03 00:00:18.743314','PEDIDO_SIN_ACUSE',4,1),(100,0,'2026-06-03 00:00:00.550589','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-03 00:00:18.738307','PEDIDO_SIN_ACUSE',4,1),(101,0,'2026-06-03 00:00:00.557267','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-03 00:00:18.731804','PEDIDO_SIN_ACUSE',4,1),(102,0,'2026-06-03 00:00:00.563294','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-03 00:00:18.723292','PEDIDO_SIN_ACUSE',4,1),(103,0,'2026-06-03 18:34:39.000000','ALTA','Cobertura: 2 días (umbral: 15 días, consumo diario: 40.0 unidades)','2026-06-03 21:38:59.159836','COBERTURA_BAJA',12,1),(104,0,'2026-06-03 18:34:39.000000','MEDIA','Cobertura: 8 días (umbral: 15 días, consumo diario: 10.0 unidades)','2026-06-03 21:38:59.110797','COBERTURA_BAJA',10,1),(105,0,'2026-06-03 18:34:39.000000','BAJA','Cobertura: 11 días (umbral: 15 días, consumo diario: 6.8 unidades)','2026-06-03 21:38:58.866095','COBERTURA_BAJA',3,1),(106,0,'2026-06-03 21:38:58.191752','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-03 21:38:58.987321','PEDIDO_SIN_ACUSE',5,1),(107,0,'2026-06-03 21:38:58.381944','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-03 21:38:58.933995','PEDIDO_SIN_ACUSE',4,1),(108,0,'2026-06-03 21:38:58.410293','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(109,0,'2026-06-03 21:38:58.444695','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(110,0,'2026-06-03 21:38:58.476561','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(111,0,'2026-06-03 21:38:58.506573','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(112,0,'2026-06-03 21:38:58.534361','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(113,0,'2026-06-03 21:38:58.561759','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(114,0,'2026-06-03 21:38:58.587465','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(115,0,'2026-06-03 21:38:58.619941','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-03 21:38:58.935339','PEDIDO_SIN_ACUSE',4,1),(116,0,'2026-06-03 21:38:59.721624','BAJA','Cobertura: 11 días (umbral: 15 días, consumo diario: 6,8 unidades)','2026-06-03 22:00:01.173518','COBERTURA_BAJA',3,1),(117,0,'2026-06-03 21:38:59.889952','MEDIA','Cobertura: 8 días (umbral: 15 días, consumo diario: 10,0 unidades)','2026-06-03 22:00:01.329348','COBERTURA_BAJA',10,1),(118,0,'2026-06-03 21:38:59.954766','ALTA','Cobertura: 2 días (umbral: 15 días, consumo diario: 40,0 unidades)','2026-06-03 22:00:01.370907','COBERTURA_BAJA',12,1),(119,0,'2026-06-03 22:00:00.833549','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-03 22:00:01.236143','PEDIDO_SIN_ACUSE',5,1),(120,0,'2026-06-03 22:00:00.897179','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-03 22:00:01.201030','PEDIDO_SIN_ACUSE',4,1),(121,0,'2026-06-03 22:00:00.919676','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(122,0,'2026-06-03 22:00:00.939490','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(123,0,'2026-06-03 22:00:00.972798','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(124,0,'2026-06-03 22:00:00.992597','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(125,0,'2026-06-03 22:00:01.009800','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(126,0,'2026-06-03 22:00:01.028636','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(127,0,'2026-06-03 22:00:01.043168','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(128,0,'2026-06-03 22:00:01.062443','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-03 22:00:01.202025','PEDIDO_SIN_ACUSE',4,1),(129,0,'2026-06-03 23:00:00.646481','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-03 23:30:00.207141','PEDIDO_SIN_ACUSE',5,1),(130,0,'2026-06-03 23:00:00.707578','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-03 23:30:00.148658','PEDIDO_SIN_ACUSE',4,1),(131,0,'2026-06-03 23:00:00.725807','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-03 23:30:00.148658','PEDIDO_SIN_ACUSE',4,1),(132,0,'2026-06-03 23:00:00.746288','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-03 23:30:00.148658','PEDIDO_SIN_ACUSE',4,1),(133,0,'2026-06-03 23:00:00.765266','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-03 23:30:00.148658','PEDIDO_SIN_ACUSE',4,1),(134,0,'2026-06-03 23:00:00.784433','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-03 23:30:00.149748','PEDIDO_SIN_ACUSE',4,1),(135,0,'2026-06-03 23:00:00.808122','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-03 23:30:00.149748','PEDIDO_SIN_ACUSE',4,1),(136,0,'2026-06-03 23:00:00.830480','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-03 23:30:00.149748','PEDIDO_SIN_ACUSE',4,1),(137,0,'2026-06-03 23:00:00.849951','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-03 23:30:00.149748','PEDIDO_SIN_ACUSE',4,1),(138,0,'2026-06-03 23:00:00.869406','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-03 23:30:00.149748','PEDIDO_SIN_ACUSE',4,1),(139,0,'2026-06-04 22:16:50.128256','BAJA','Cobertura: 11 días (umbral: 15 días, consumo diario: 6,8 unidades)','2026-06-04 22:16:50.376544','COBERTURA_BAJA',3,1),(140,0,'2026-06-04 22:16:50.184412','MEDIA','Cobertura: 8 días (umbral: 15 días, consumo diario: 9,7 unidades)','2026-06-04 22:16:50.412894','COBERTURA_BAJA',10,1),(141,0,'2026-06-04 22:16:50.200431','ALTA','Cobertura: 2 días (umbral: 15 días, consumo diario: 38,7 unidades)','2026-06-04 22:16:50.423406','COBERTURA_BAJA',12,1),(142,0,'2026-06-04 22:16:50.530429','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-05 21:57:34.741502','PEDIDO_SIN_ACUSE',5,1),(143,0,'2026-06-04 22:16:50.537677','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-05 21:57:34.720045','PEDIDO_SIN_ACUSE',4,1),(144,0,'2026-06-04 22:16:50.544160','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-05 21:57:34.710024','PEDIDO_SIN_ACUSE',4,1),(145,0,'2026-06-04 22:16:50.552270','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-05 21:57:34.691515','PEDIDO_SIN_ACUSE',4,1),(146,0,'2026-06-04 22:16:50.558271','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-05 21:57:34.679437','PEDIDO_SIN_ACUSE',4,1),(147,0,'2026-06-04 22:16:50.567379','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-05 21:57:34.667420','PEDIDO_SIN_ACUSE',4,1),(148,0,'2026-06-04 22:16:50.574893','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-05 21:57:34.654403','PEDIDO_SIN_ACUSE',4,1),(149,0,'2026-06-04 22:16:50.586051','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-05 21:57:34.641294','PEDIDO_SIN_ACUSE',4,1),(150,0,'2026-06-04 22:16:50.594385','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-05 21:57:34.624172','PEDIDO_SIN_ACUSE',4,1),(151,0,'2026-06-04 22:16:50.602064','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-05 21:57:34.597223','PEDIDO_SIN_ACUSE',4,1),(152,0,'2026-06-05 22:00:00.059214','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-05 22:00:00.526625','PEDIDO_SIN_ACUSE',5,1),(153,0,'2026-06-05 22:00:00.082097','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-05 22:00:00.513608','PEDIDO_SIN_ACUSE',4,1),(154,0,'2026-06-05 22:00:00.089054','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-05 22:00:00.513608','PEDIDO_SIN_ACUSE',4,1),(155,0,'2026-06-05 22:00:00.096833','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-05 22:00:00.513608','PEDIDO_SIN_ACUSE',4,1),(156,0,'2026-06-05 22:00:00.104344','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-05 22:00:00.513608','PEDIDO_SIN_ACUSE',4,1),(157,0,'2026-06-05 22:00:00.110889','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-05 22:00:00.513608','PEDIDO_SIN_ACUSE',4,1),(158,0,'2026-06-05 22:00:00.119400','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-05 22:00:00.513608','PEDIDO_SIN_ACUSE',4,1),(159,0,'2026-06-05 22:00:00.128414','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-05 22:00:00.513608','PEDIDO_SIN_ACUSE',4,1),(160,0,'2026-06-05 22:00:00.135933','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-05 22:00:00.514608','PEDIDO_SIN_ACUSE',4,1),(161,0,'2026-06-05 22:00:00.144445','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-05 22:00:00.514608','PEDIDO_SIN_ACUSE',4,1),(162,1,'2026-06-05 22:00:00.205390','BAJA','Cobertura: 12 días (umbral: 15 días, consumo diario: 6,5 unidades)',NULL,'COBERTURA_BAJA',3,1),(163,1,'2026-06-05 22:00:00.266029','MEDIA','Cobertura: 8 días (umbral: 15 días, consumo diario: 9,3 unidades)',NULL,'COBERTURA_BAJA',10,1),(164,1,'2026-06-05 22:00:00.283776','ALTA','Cobertura: 2 días (umbral: 15 días, consumo diario: 37,3 unidades)',NULL,'COBERTURA_BAJA',12,1),(165,0,'2026-06-05 23:00:00.247675','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-05 23:14:44.477554','PEDIDO_SIN_ACUSE',5,1),(166,0,'2026-06-05 23:00:00.316721','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-05 23:14:44.470369','PEDIDO_SIN_ACUSE',4,1),(167,0,'2026-06-05 23:00:00.322745','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-05 23:14:44.462371','PEDIDO_SIN_ACUSE',4,1),(168,0,'2026-06-05 23:00:00.328745','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-05 23:14:44.455291','PEDIDO_SIN_ACUSE',4,1),(169,0,'2026-06-05 23:00:00.335660','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-05 23:14:44.446700','PEDIDO_SIN_ACUSE',4,1),(170,0,'2026-06-05 23:00:00.341171','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-05 23:14:44.440165','PEDIDO_SIN_ACUSE',4,1),(171,0,'2026-06-05 23:00:00.348630','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-05 23:14:44.427916','PEDIDO_SIN_ACUSE',4,1),(172,0,'2026-06-05 23:00:00.356213','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-05 23:14:44.411011','PEDIDO_SIN_ACUSE',4,1),(173,0,'2026-06-05 23:00:00.363280','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-05 23:14:44.397052','PEDIDO_SIN_ACUSE',4,1),(174,0,'2026-06-05 23:00:00.369278','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-05 23:14:44.368674','PEDIDO_SIN_ACUSE',4,1),(175,0,'2026-06-06 00:00:00.288427','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-06 00:00:00.411099','PEDIDO_SIN_ACUSE',5,1),(176,0,'2026-06-06 00:00:00.302439','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-06 00:00:00.397088','PEDIDO_SIN_ACUSE',4,1),(177,0,'2026-06-06 00:00:00.309448','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-06 00:00:00.397088','PEDIDO_SIN_ACUSE',4,1),(178,0,'2026-06-06 00:00:00.316477','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-06 00:00:00.397088','PEDIDO_SIN_ACUSE',4,1),(179,0,'2026-06-06 00:00:00.324503','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-06 00:00:00.398087','PEDIDO_SIN_ACUSE',4,1),(180,0,'2026-06-06 00:00:00.329503','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-06 00:00:00.398087','PEDIDO_SIN_ACUSE',4,1),(181,0,'2026-06-06 00:00:00.335521','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-06 00:00:00.398087','PEDIDO_SIN_ACUSE',4,1),(182,0,'2026-06-06 00:00:00.342033','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-06 00:00:00.398087','PEDIDO_SIN_ACUSE',4,1),(183,0,'2026-06-06 00:00:00.349542','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-06 00:00:00.398087','PEDIDO_SIN_ACUSE',4,1),(184,0,'2026-06-06 00:00:00.356053','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-06 00:00:00.398087','PEDIDO_SIN_ACUSE',4,1),(185,0,'2026-06-06 12:00:00.044495','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-06 12:00:00.242592','PEDIDO_SIN_ACUSE',5,1),(186,0,'2026-06-06 12:00:00.105330','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-06 12:00:00.217512','PEDIDO_SIN_ACUSE',4,1),(187,0,'2026-06-06 12:00:00.112349','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(188,0,'2026-06-06 12:00:00.118364','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(189,0,'2026-06-06 12:00:00.125129','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(190,0,'2026-06-06 12:00:00.131638','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(191,0,'2026-06-06 12:00:00.139641','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(192,0,'2026-06-06 12:00:00.145847','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(193,0,'2026-06-06 12:00:00.152359','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(194,0,'2026-06-06 12:00:00.159359','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-06 12:00:00.219510','PEDIDO_SIN_ACUSE',4,1),(195,0,'2026-06-06 13:00:00.175447','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-06 13:00:07.431288','PEDIDO_SIN_ACUSE',5,1),(196,0,'2026-06-06 13:00:00.185613','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-06 13:00:07.425288','PEDIDO_SIN_ACUSE',4,1),(197,0,'2026-06-06 13:00:00.191357','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-06 13:00:07.420288','PEDIDO_SIN_ACUSE',4,1),(198,0,'2026-06-06 13:00:00.196357','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-06 13:00:07.415288','PEDIDO_SIN_ACUSE',4,1),(199,0,'2026-06-06 13:00:00.201357','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-06 13:00:07.410289','PEDIDO_SIN_ACUSE',4,1),(200,0,'2026-06-06 13:00:00.207124','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-06 13:00:07.404779','PEDIDO_SIN_ACUSE',4,1),(201,0,'2026-06-06 13:00:00.212128','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-06 13:00:07.400256','PEDIDO_SIN_ACUSE',4,1),(202,0,'2026-06-06 13:00:00.219128','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-06 13:00:07.394174','PEDIDO_SIN_ACUSE',4,1),(203,0,'2026-06-06 13:00:00.224019','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-06 13:00:07.390174','PEDIDO_SIN_ACUSE',4,1),(204,0,'2026-06-06 13:00:00.229533','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-06 13:00:07.385174','PEDIDO_SIN_ACUSE',4,1),(205,0,'2026-06-06 14:00:00.386954','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.','2026-06-06 14:30:00.058961','PEDIDO_SIN_ACUSE',5,1),(206,0,'2026-06-06 14:00:00.395665','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(207,0,'2026-06-06 14:00:00.401586','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(208,0,'2026-06-06 14:00:00.406591','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(209,0,'2026-06-06 14:00:00.411586','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(210,0,'2026-06-06 14:00:00.416979','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(211,0,'2026-06-06 14:00:00.422798','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(212,0,'2026-06-06 14:00:00.427795','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(213,0,'2026-06-06 14:00:00.433794','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(214,0,'2026-06-06 14:00:00.442130','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.','2026-06-06 14:30:00.045953','PEDIDO_SIN_ACUSE',4,1),(215,1,'2026-06-06 15:00:00.181648','ALTA','CP010: Pedido #1 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Losart├ín 50 mg. Enviado: 2025-11-09T18:44:43.857246. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',5,1),(216,1,'2026-06-06 15:00:00.192553','ALTA','CP010: Pedido #16 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:08:58.436658. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(217,1,'2026-06-06 15:00:00.198561','ALTA','CP010: Pedido #17 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:09:52.909293. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(218,1,'2026-06-06 15:00:00.203564','ALTA','CP010: Pedido #18 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:16:36.842830. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(219,1,'2026-06-06 15:00:00.209240','ALTA','CP010: Pedido #19 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:18:52.838072. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(220,1,'2026-06-06 15:00:00.213248','ALTA','CP010: Pedido #20 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:20:30.723042. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(221,1,'2026-06-06 15:00:00.219244','ALTA','CP010: Pedido #21 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:21:25.660551. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(222,1,'2026-06-06 15:00:00.224246','ALTA','CP010: Pedido #22 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T00:46:09.697124. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(223,1,'2026-06-06 15:00:00.230437','ALTA','CP010: Pedido #23 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: N/A. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(224,1,'2026-06-06 15:00:00.235437','ALTA','CP010: Pedido #24 sin acuse de recibo. Proveedor: Ronald Martin, Producto: Salbutamol Inhalador. Enviado: 2025-11-27T01:06:33.972274. Requiere seguimiento inmediato.',NULL,'PEDIDO_SIN_ACUSE',4,1),(225,1,'2026-06-20 18:50:45.000000','ALTA','Stock crítico detectado',NULL,'STOCK_CRITICO',9,1),(226,1,'2026-06-20 18:50:45.000000','ALTA','Stock crítico detectado',NULL,'STOCK_CRITICO',6,1),(227,1,'2026-06-20 18:50:45.000000','MEDIA','Stock por debajo del punto de pedido',NULL,'STOCK_BAJO',7,1),(228,1,'2026-06-20 18:50:45.000000','MEDIA','Stock por debajo del punto de pedido',NULL,'STOCK_BAJO',13,1);
/*!40000 ALTER TABLE `alertas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_logs`
--

DROP TABLE IF EXISTS `audit_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accion` varchar(100) NOT NULL,
  `descripcion` text,
  `entidad_id` bigint DEFAULT NULL,
  `entidad_tipo` varchar(50) DEFAULT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `usuario_nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_logs`
--

LOCK TABLES `audit_logs` WRITE;
/*!40000 ALTER TABLE `audit_logs` DISABLE KEYS */;
INSERT INTO `audit_logs` VALUES (1,'USUARIO_CREADO','Usuario creado: Franco (francos@gmail.com)',6,'Usuario','2026-06-01 23:02:56.886465','0:0:0:0:0:0:0:1',5,'Dr. Luis Martínez - Jefe de Farmacia');
/*!40000 ALTER TABLE `audit_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `column3` varchar(255) DEFAULT NULL,
  `column4` varchar(255) DEFAULT NULL,
  `column5` varchar(255) DEFAULT NULL,
  `column7` varchar(255) DEFAULT NULL,
  `nombre` varchar(80) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qcog8b7hps1hioi9onqwjdt6y` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,NULL,NULL,NULL,NULL,'Analg├®sicos'),(2,NULL,NULL,NULL,NULL,'Antibi├│ticos'),(3,NULL,NULL,NULL,NULL,'Antiinflamatorios'),(4,NULL,NULL,NULL,NULL,'Vitaminas'),(5,NULL,NULL,NULL,NULL,'Inhaladores'),(6,NULL,NULL,NULL,NULL,'Antihipertensivos'),(7,NULL,NULL,NULL,NULL,'Anti├ícidos'),(8,NULL,NULL,NULL,NULL,'Antihistam├¡nicos'),(9,NULL,NULL,NULL,NULL,'Antipir├®ticos'),(10,NULL,NULL,NULL,NULL,'Antimic├│ticos');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario` (
  `sede_id` bigint NOT NULL AUTO_INCREMENT,
  `lote_id` bigint NOT NULL,
  `cantidad` int DEFAULT '0',
  `Column4` varchar(255) DEFAULT NULL,
  `Column5` varchar(255) DEFAULT NULL,
  `Column6` varchar(255) DEFAULT NULL,
  `Column7` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sede_id`,`lote_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` VALUES (1,1,100,NULL,NULL,NULL,NULL),(1,2,80,NULL,NULL,NULL,NULL),(1,3,120,NULL,NULL,NULL,NULL),(1,4,30,NULL,NULL,NULL,NULL),(1,5,100,NULL,NULL,NULL,NULL),(1,6,80,NULL,NULL,NULL,NULL),(1,7,100,NULL,NULL,NULL,NULL),(1,8,100,NULL,NULL,NULL,NULL),(1,9,80,NULL,NULL,NULL,NULL),(1,10,100,NULL,NULL,NULL,NULL),(1,11,100,NULL,NULL,NULL,NULL),(1,12,80,NULL,NULL,NULL,NULL),(1,13,100,NULL,NULL,NULL,NULL),(1,14,80,NULL,NULL,NULL,NULL),(1,15,20,NULL,NULL,NULL,NULL),(1,16,80,NULL,NULL,NULL,NULL),(1,17,80,NULL,NULL,NULL,NULL),(1,18,5,NULL,NULL,NULL,NULL),(1,19,80,NULL,NULL,NULL,NULL),(1,20,12,NULL,NULL,NULL,NULL),(1,21,30,NULL,NULL,NULL,NULL),(1,22,80,NULL,NULL,NULL,NULL),(1,23,10,NULL,NULL,NULL,NULL),(1,24,80,NULL,NULL,NULL,NULL),(1,25,80,NULL,NULL,NULL,NULL),(1,26,80,NULL,NULL,NULL,NULL),(1,27,35,NULL,NULL,NULL,NULL),(1,28,80,NULL,NULL,NULL,NULL),(1,29,80,NULL,NULL,NULL,NULL),(1,30,80,NULL,NULL,NULL,NULL),(1,31,80,NULL,NULL,NULL,NULL),(1,32,80,NULL,NULL,NULL,NULL),(1,33,80,NULL,NULL,NULL,NULL),(1,34,80,NULL,NULL,NULL,NULL),(1,35,80,NULL,NULL,NULL,NULL),(1,36,80,NULL,NULL,NULL,NULL),(1,37,80,NULL,NULL,NULL,NULL),(1,38,80,NULL,NULL,NULL,NULL),(1,39,80,NULL,NULL,NULL,NULL),(1,40,80,NULL,NULL,NULL,NULL),(1,41,80,NULL,NULL,NULL,NULL),(1,42,80,NULL,NULL,NULL,NULL),(1,43,80,NULL,NULL,NULL,NULL),(1,44,80,NULL,NULL,NULL,NULL),(1,45,100,NULL,NULL,NULL,NULL),(1,46,100,NULL,NULL,NULL,NULL),(1,47,100,NULL,NULL,NULL,NULL),(1,48,100,NULL,NULL,NULL,NULL),(1,49,100,NULL,NULL,NULL,NULL),(1,50,80,NULL,NULL,NULL,NULL),(1,51,120,NULL,NULL,NULL,NULL),(1,52,90,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lotes`
--

DROP TABLE IF EXISTS `lotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lotes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo_producto_prov` varchar(80) DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `producto_id` bigint NOT NULL,
  `precio_unitario` decimal(10,2) DEFAULT '0.00' COMMENT 'Precio de compra por unidad de este lote',
  `bloqueado` bit(1) DEFAULT b'0',
  `motivo_bloqueo` varchar(255) DEFAULT NULL,
  `bloqueado_en` date DEFAULT NULL,
  `bloqueado_por` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnnj5qqxdf1snlgaxjxtut4ow2` (`producto_id`),
  CONSTRAINT `FKnnj5qqxdf1snlgaxjxtut4ow2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lotes`
--

LOCK TABLES `lotes` WRITE;
/*!40000 ALTER TABLE `lotes` DISABLE KEYS */;
INSERT INTO `lotes` VALUES (15,'PARA-001',_binary '','2026-12-31',1,2.50,_binary '\0',NULL,NULL,NULL),(16,'AMOX-001',_binary '','2026-12-31',2,5.00,_binary '\0',NULL,NULL,NULL),(17,'IBUP-001',_binary '','2026-12-31',3,3.00,_binary '\0',NULL,NULL,NULL),(18,'SALB-001',_binary '','2026-12-31',4,15.00,_binary '\0',NULL,NULL,NULL),(19,'LOSA-001',_binary '','2026-12-31',5,4.50,_binary '\0',NULL,NULL,NULL),(20,'VITA-001',_binary '','2026-12-31',6,8.00,_binary '\0',NULL,NULL,NULL),(21,'OMEP-001',_binary '','2026-12-31',7,6.00,_binary '\0',NULL,NULL,NULL),(22,'LORA-001',_binary '','2026-12-31',8,2.00,_binary '\0',NULL,NULL,NULL),(23,'AZIT-001',_binary '','2026-12-31',9,12.00,_binary '\0',NULL,NULL,NULL),(24,'METF-001',_binary '','2026-12-31',10,3.50,_binary '\0',NULL,NULL,NULL),(25,'ATOR-001',_binary '','2026-12-31',11,7.00,_binary '\0',NULL,NULL,NULL),(26,'DICL-001',_binary '','2026-12-31',12,3.00,_binary '\0',NULL,NULL,NULL),(27,'CLON-001',_binary '','2026-12-31',13,10.00,_binary '\0',NULL,NULL,NULL),(28,'FLUC-001',_binary '','2026-06-25',14,9.00,_binary '\0',NULL,NULL,NULL),(29,'ACET-001',_binary '','2026-07-10',15,5.50,_binary '\0',NULL,NULL,NULL),(30,'DEXA-001',_binary '','2026-12-31',16,4.00,_binary '\0',NULL,NULL,NULL),(31,'CETI-001',_binary '','2026-12-31',17,2.50,_binary '\0',NULL,NULL,NULL),(32,'FURO-001',_binary '','2026-12-31',18,3.00,_binary '\0',NULL,NULL,NULL),(33,'KETO-001',_binary '','2026-12-31',19,8.00,_binary '\0',NULL,NULL,NULL),(34,'COMP-001',_binary '','2026-12-31',20,6.00,_binary '\0',NULL,NULL,NULL),(35,'CIPR-001',_binary '','2026-12-31',21,7.50,_binary '\0',NULL,NULL,NULL),(36,'RANI-001',_binary '','2026-12-31',22,4.00,_binary '\0',NULL,NULL,NULL),(37,'ENAL-001',_binary '','2026-12-31',23,3.50,_binary '\0',NULL,NULL,NULL),(38,'TRAM-001',_binary '','2026-12-31',24,15.00,_binary '\0',NULL,NULL,NULL),(39,'PRED-001',_binary '','2026-12-31',25,3.00,_binary '\0',NULL,NULL,NULL),(40,'CLOR-001',_binary '','2026-12-31',26,2.00,_binary '\0',NULL,NULL,NULL),(41,'HIDR-001',_binary '','2026-12-31',27,5.00,_binary '\0',NULL,NULL,NULL),(42,'INSU-001',_binary '','2026-12-31',28,25.00,_binary '\0',NULL,NULL,NULL),(43,'KETO2-001',_binary '','2026-12-31',29,10.00,_binary '\0',NULL,NULL,NULL),(44,'CLOT-001',_binary '','2026-12-31',30,12.00,_binary '\0',NULL,NULL,NULL),(45,'LOTE-NOV-2025-001',_binary '','2025-12-31',4,NULL,_binary '\0',NULL,NULL,NULL),(46,'45432',_binary '','2026-06-14',4,NULL,_binary '\0',NULL,NULL,NULL),(47,'45432',_binary '','2026-06-14',2,NULL,_binary '\0',NULL,NULL,NULL),(48,'45432',_binary '','2027-12-15',8,NULL,_binary '\0',NULL,NULL,NULL),(49,'LOTE-PAR-001',_binary '','2025-12-31',1,5.50,_binary '','321654','2026-06-02',NULL),(50,'LOTE-PAR-002',_binary '','2025-06-30',1,5.50,_binary '\0',NULL,NULL,NULL),(51,'LOTE-AMO-001',_binary '','2025-11-15',2,12.00,_binary '\0',NULL,NULL,NULL),(52,'LOTE-AMO-002',_binary '','2025-08-20',2,12.00,_binary '\0',NULL,NULL,NULL),(53,'LOTE-IBU-001',_binary '','2025-10-10',3,8.00,_binary '\0',NULL,NULL,NULL),(54,'LOTE-SAL-001',_binary '','2025-09-25',4,25.00,_binary '\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `lotes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimientos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `creado_por` int DEFAULT NULL,
  `doc_ref` varchar(80) DEFAULT NULL,
  `lote_id` bigint DEFAULT NULL,
  `motivo` varchar(160) DEFAULT NULL,
  `ocurrio_en` datetime(6) DEFAULT NULL,
  `sede_id` bigint DEFAULT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=533 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos`
--

LOCK TABLES `movimientos` WRITE;
/*!40000 ALTER TABLE `movimientos` DISABLE KEYS */;
INSERT INTO `movimientos` VALUES (355,500,1,'FC-2025-0601',1,'Compra inicial','2025-06-05 09:00:00.000000',1,'ENTRADA'),(356,-80,1,'VT-2025-1001',1,'Venta farmacia','2025-06-08 10:30:00.000000',1,'SALIDA'),(357,-65,1,'VT-2025-1015',1,'Venta farmacia','2025-06-12 14:20:00.000000',1,'SALIDA'),(358,-70,1,'VT-2025-1028',1,'Venta farmacia','2025-06-18 11:45:00.000000',1,'SALIDA'),(359,-55,1,'VT-2025-1042',1,'Venta farmacia','2025-06-25 16:10:00.000000',1,'SALIDA'),(360,-60,1,'VT-2025-1056',1,'Venta farmacia','2025-07-03 09:15:00.000000',1,'SALIDA'),(361,-75,1,'VT-2025-1070',1,'Venta farmacia','2025-07-10 13:30:00.000000',1,'SALIDA'),(362,-50,1,'VT-2025-1089',1,'Venta farmacia','2025-07-20 10:00:00.000000',1,'SALIDA'),(363,600,1,'FC-2025-0802',2,'Reposici├│n stock','2025-08-02 08:30:00.000000',1,'ENTRADA'),(364,-90,1,'VT-2025-1105',2,'Venta farmacia','2025-08-05 11:20:00.000000',1,'SALIDA'),(365,-85,1,'VT-2025-1125',2,'Venta farmacia','2025-08-15 14:45:00.000000',1,'SALIDA'),(366,-70,1,'VT-2025-1142',2,'Venta farmacia','2025-08-25 09:30:00.000000',1,'SALIDA'),(367,-80,1,'VT-2025-1158',2,'Venta farmacia','2025-09-08 10:15:00.000000',1,'SALIDA'),(368,-95,1,'VT-2025-1175',2,'Venta farmacia','2025-09-22 15:20:00.000000',1,'SALIDA'),(369,-75,1,'VT-2025-1192',2,'Venta farmacia','2025-10-10 11:30:00.000000',1,'SALIDA'),(370,-65,1,'VT-2025-1210',2,'Venta farmacia','2025-10-28 13:45:00.000000',1,'SALIDA'),(371,-50,1,'VT-2025-1228',2,'Venta farmacia','2025-11-12 09:00:00.000000',1,'SALIDA'),(372,400,1,'FC-2025-0610',3,'Compra antibi├│tico','2025-06-10 10:00:00.000000',1,'ENTRADA'),(373,-45,1,'VT-2025-1030',3,'Receta m├®dica','2025-06-18 11:30:00.000000',1,'SALIDA'),(374,-50,1,'VT-2025-1058',3,'Receta m├®dica','2025-07-05 14:20:00.000000',1,'SALIDA'),(375,-40,1,'VT-2025-1092',3,'Receta m├®dica','2025-07-22 10:45:00.000000',1,'SALIDA'),(376,-55,1,'VT-2025-1118',3,'Receta m├®dica','2025-08-12 09:15:00.000000',1,'SALIDA'),(377,-48,1,'VT-2025-1160',3,'Receta m├®dica','2025-09-03 13:30:00.000000',1,'SALIDA'),(378,-52,1,'VT-2025-1182',3,'Receta m├®dica','2025-09-28 11:00:00.000000',1,'SALIDA'),(379,-43,1,'VT-2025-1200',3,'Receta m├®dica','2025-10-18 14:15:00.000000',1,'SALIDA'),(380,100,1,'FC-2025-1025',4,'Reposici├│n','2025-10-25 09:00:00.000000',1,'ENTRADA'),(381,-38,1,'VT-2025-1222',4,'Receta m├®dica','2025-11-08 10:30:00.000000',1,'SALIDA'),(382,-40,1,'VT-2025-1235',4,'Receta m├®dica','2025-11-15 13:45:00.000000',1,'SALIDA'),(383,350,1,'FC-2025-0615',5,'Compra analg├®sico','2025-06-15 08:00:00.000000',1,'ENTRADA'),(384,-60,1,'VT-2025-1035',5,'Venta farmacia','2025-06-20 10:30:00.000000',1,'SALIDA'),(385,-55,1,'VT-2025-1065',5,'Venta farmacia','2025-07-08 14:15:00.000000',1,'SALIDA'),(386,-48,1,'VT-2025-1100',5,'Venta farmacia','2025-08-01 11:20:00.000000',1,'SALIDA'),(387,-45,1,'VT-2025-1132',5,'Venta farmacia','2025-08-20 09:45:00.000000',1,'SALIDA'),(388,-42,1,'VT-2025-1165',5,'Venta farmacia','2025-09-10 13:00:00.000000',1,'SALIDA'),(389,500,1,'FC-2025-0915',6,'Reposici├│n stock','2025-09-15 10:00:00.000000',1,'ENTRADA'),(390,-55,1,'VT-2025-1188',6,'Venta farmacia','2025-10-05 11:30:00.000000',1,'SALIDA'),(391,-65,1,'VT-2025-1208',6,'Venta farmacia','2025-10-25 14:20:00.000000',1,'SALIDA'),(392,150,1,'FC-2025-0620',7,'Compra inhalador','2025-06-20 09:30:00.000000',1,'ENTRADA'),(393,-25,1,'VT-2025-1068',7,'Receta especialista','2025-07-10 10:15:00.000000',1,'SALIDA'),(394,-22,1,'VT-2025-1128',7,'Receta especialista','2025-08-15 13:30:00.000000',1,'SALIDA'),(395,-28,1,'VT-2025-1178',7,'Receta especialista','2025-09-20 11:45:00.000000',1,'SALIDA'),(396,-27,1,'VT-2025-1215',7,'Receta especialista','2025-10-30 14:00:00.000000',1,'SALIDA'),(397,600,1,'FC-2025-0608',8,'Compra antihipertensivo','2025-06-08 08:45:00.000000',1,'ENTRADA'),(398,-85,1,'VT-2025-1040',8,'Tratamiento cr├│nico','2025-06-25 10:00:00.000000',1,'SALIDA'),(399,-80,1,'VT-2025-1085',8,'Tratamiento cr├│nico','2025-07-18 13:15:00.000000',1,'SALIDA'),(400,-88,1,'VT-2025-1138',8,'Tratamiento cr├│nico','2025-08-22 11:30:00.000000',1,'SALIDA'),(401,-90,1,'VT-2025-1180',8,'Tratamiento cr├│nico','2025-09-25 14:45:00.000000',1,'SALIDA'),(402,700,1,'FC-2025-1001',9,'Reposici├│n','2025-10-01 09:00:00.000000',1,'ENTRADA'),(403,-92,1,'VT-2025-1202',9,'Tratamiento cr├│nico','2025-10-20 10:30:00.000000',1,'SALIDA'),(404,-86,1,'VT-2025-1225',9,'Tratamiento cr├│nico','2025-11-10 13:00:00.000000',1,'SALIDA'),(405,1500,1,'FC-2025-0601-VIT',10,'Compra masiva vitamina','2025-06-01 10:00:00.000000',1,'ENTRADA'),(406,-50,1,'VT-2025-1080',10,'Venta suplemento','2025-07-15 11:30:00.000000',1,'SALIDA'),(407,-45,1,'VT-2025-1145',10,'Venta suplemento','2025-08-25 14:20:00.000000',1,'SALIDA'),(408,-55,1,'VT-2025-1185',10,'Venta suplemento','2025-09-30 10:15:00.000000',1,'SALIDA'),(409,-48,1,'VT-2025-1212',10,'Venta suplemento','2025-10-28 13:45:00.000000',1,'SALIDA'),(410,-52,1,'VT-2025-1232',10,'Venta suplemento','2025-11-14 11:00:00.000000',1,'SALIDA'),(411,-1175,1,'AJ-2025-001',10,'Ajuste inventario - sobrestock','2025-11-16 09:00:00.000000',1,'SALIDA'),(412,400,1,'FC-2025-0612',11,'Compra gastroprotector','2025-06-12 09:15:00.000000',1,'ENTRADA'),(413,-70,1,'VT-2025-1045',11,'Venta farmacia','2025-06-28 10:45:00.000000',1,'SALIDA'),(414,-65,1,'VT-2025-1095',11,'Venta farmacia','2025-07-25 13:30:00.000000',1,'SALIDA'),(415,-68,1,'VT-2025-1130',11,'Venta farmacia','2025-08-18 11:15:00.000000',1,'SALIDA'),(416,-72,1,'VT-2025-1172',11,'Venta farmacia','2025-09-15 14:00:00.000000',1,'SALIDA'),(417,-75,1,'VT-2025-1195',11,'Venta farmacia','2025-10-12 10:30:00.000000',1,'SALIDA'),(418,300,1,'FC-2025-1015',12,'Reposici├│n','2025-10-15 08:45:00.000000',1,'ENTRADA'),(419,-80,1,'VT-2025-1220',12,'Venta farmacia','2025-11-05 11:20:00.000000',1,'SALIDA'),(420,-73,1,'VT-2025-1238',12,'Venta farmacia','2025-11-16 14:35:00.000000',1,'SALIDA'),(421,250,1,'FC-2025-0618',13,'Compra antihistam├¡nico','2025-06-18 10:00:00.000000',1,'ENTRADA'),(422,-25,1,'VT-2025-1052',13,'Venta alergia','2025-07-02 11:30:00.000000',1,'SALIDA'),(423,-22,1,'VT-2025-1110',13,'Venta alergia','2025-08-08 14:20:00.000000',1,'SALIDA'),(424,-40,1,'VT-2025-1168',13,'Venta alergia','2025-09-12 10:15:00.000000',1,'SALIDA'),(425,-45,1,'VT-2025-1190',13,'Venta alergia','2025-10-08 13:30:00.000000',1,'SALIDA'),(426,-40,1,'VT-2025-1218',13,'Venta alergia','2025-11-03 11:45:00.000000',1,'SALIDA'),(427,50,1,'FC-2025-0901-CTRL',14,'Compra medicamento controlado','2025-09-01 09:00:00.000000',1,'ENTRADA'),(428,-8,1,'VT-2025-1170',14,'Receta psiquiatra - Controlado','2025-09-15 10:30:00.000000',1,'SALIDA'),(429,-10,1,'VT-2025-1187',14,'Receta psiquiatra - Controlado','2025-10-05 11:15:00.000000',1,'SALIDA'),(430,-9,1,'VT-2025-1205',14,'Receta psiquiatra - Controlado','2025-10-22 13:45:00.000000',1,'SALIDA'),(431,-8,1,'VT-2025-1223',14,'Receta psiquiatra - Controlado','2025-11-08 10:00:00.000000',1,'SALIDA'),(432,-10,1,'VT-2025-1240',14,'Receta psiquiatra - Controlado','2025-11-16 14:20:00.000000',1,'SALIDA'),(433,2,3,'123',2,'Dispensación','2025-11-22 12:18:24.199677',1,'SALIDA'),(434,100,5,'FAC-2025-001',45,'','2025-11-27 13:43:05.973848',1,'ENTRADA'),(435,50,3,'FAC-2025-001',15,'Dispensación','2025-11-27 13:53:29.345955',1,'SALIDA'),(436,20,3,'FAC-2025-001',15,'Dispensación','2025-11-27 13:53:47.139097',1,'SALIDA'),(437,9,3,'FAC-2025-001',15,'Dispensación','2025-11-27 13:54:05.019618',1,'SALIDA'),(438,1,3,'FAC-2025-001',15,'Dispensación','2025-11-27 13:54:13.696610',1,'SALIDA'),(439,100,5,'PEDIDO-16',46,'Recepción de pedido #16 - Ronald Martin','2026-05-31 00:30:21.747145',1,'ENTRADA'),(440,100,1,'PEDIDO-2',47,'Recepción de pedido #2 - Ronald Martin','2026-05-31 22:44:09.056979',1,'ENTRADA'),(441,100,5,'FAC-TEST-001',48,'','2026-06-01 21:47:48.956262',1,'ENTRADA'),(442,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-06-03 18:32:42.000000',1,'SALIDA'),(443,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-06-02 18:32:42.000000',1,'SALIDA'),(444,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-06-01 18:32:42.000000',1,'SALIDA'),(445,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-31 18:32:42.000000',1,'SALIDA'),(446,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-30 18:32:42.000000',1,'SALIDA'),(447,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-29 18:32:42.000000',1,'SALIDA'),(448,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-28 18:32:42.000000',1,'SALIDA'),(449,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-27 18:32:42.000000',1,'SALIDA'),(450,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-26 18:32:42.000000',1,'SALIDA'),(451,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-25 18:32:42.000000',1,'SALIDA'),(452,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-24 18:32:42.000000',1,'SALIDA'),(453,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-23 18:32:42.000000',1,'SALIDA'),(454,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-22 18:32:42.000000',1,'SALIDA'),(455,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-21 18:32:42.000000',1,'SALIDA'),(456,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-20 18:32:42.000000',1,'SALIDA'),(457,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-19 18:32:42.000000',1,'SALIDA'),(458,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-18 18:32:42.000000',1,'SALIDA'),(459,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-17 18:32:42.000000',1,'SALIDA'),(460,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-16 18:32:42.000000',1,'SALIDA'),(461,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-15 18:32:42.000000',1,'SALIDA'),(462,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-14 18:32:42.000000',1,'SALIDA'),(463,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-13 18:32:42.000000',1,'SALIDA'),(464,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-12 18:32:42.000000',1,'SALIDA'),(465,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-11 18:32:42.000000',1,'SALIDA'),(466,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-10 18:32:42.000000',1,'SALIDA'),(467,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-09 18:32:42.000000',1,'SALIDA'),(468,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-08 18:32:42.000000',1,'SALIDA'),(469,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-07 18:32:42.000000',1,'SALIDA'),(470,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-06 18:32:42.000000',1,'SALIDA'),(471,40,NULL,NULL,26,'CP018 carga prueba riesgo quiebre','2026-05-05 18:32:42.000000',1,'SALIDA'),(473,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-06-03 18:32:42.000000',1,'SALIDA'),(474,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-06-02 18:32:42.000000',1,'SALIDA'),(475,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-06-01 18:32:42.000000',1,'SALIDA'),(476,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-31 18:32:42.000000',1,'SALIDA'),(477,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-30 18:32:42.000000',1,'SALIDA'),(478,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-29 18:32:42.000000',1,'SALIDA'),(479,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-28 18:32:42.000000',1,'SALIDA'),(480,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-27 18:32:42.000000',1,'SALIDA'),(481,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-26 18:32:42.000000',1,'SALIDA'),(482,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-25 18:32:42.000000',1,'SALIDA'),(483,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-24 18:32:42.000000',1,'SALIDA'),(484,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-23 18:32:42.000000',1,'SALIDA'),(485,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-22 18:32:42.000000',1,'SALIDA'),(486,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-21 18:32:42.000000',1,'SALIDA'),(487,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-20 18:32:42.000000',1,'SALIDA'),(488,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-19 18:32:42.000000',1,'SALIDA'),(489,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-18 18:32:42.000000',1,'SALIDA'),(490,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-17 18:32:42.000000',1,'SALIDA'),(491,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-16 18:32:42.000000',1,'SALIDA'),(492,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-15 18:32:42.000000',1,'SALIDA'),(493,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-14 18:32:42.000000',1,'SALIDA'),(494,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-13 18:32:42.000000',1,'SALIDA'),(495,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-12 18:32:42.000000',1,'SALIDA'),(496,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-11 18:32:42.000000',1,'SALIDA'),(497,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-10 18:32:42.000000',1,'SALIDA'),(498,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-09 18:32:42.000000',1,'SALIDA'),(499,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-08 18:32:42.000000',1,'SALIDA'),(500,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-07 18:32:42.000000',1,'SALIDA'),(501,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-06 18:32:42.000000',1,'SALIDA'),(502,10,NULL,NULL,24,'CP018 carga prueba riesgo quiebre','2026-05-05 18:32:42.000000',1,'SALIDA'),(504,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-06-03 18:32:42.000000',1,'SALIDA'),(505,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-06-02 18:32:42.000000',1,'SALIDA'),(506,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-06-01 18:32:42.000000',1,'SALIDA'),(507,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-31 18:32:42.000000',1,'SALIDA'),(508,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-30 18:32:42.000000',1,'SALIDA'),(509,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-29 18:32:42.000000',1,'SALIDA'),(510,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-28 18:32:42.000000',1,'SALIDA'),(511,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-27 18:32:42.000000',1,'SALIDA'),(512,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-26 18:32:42.000000',1,'SALIDA'),(513,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-25 18:32:42.000000',1,'SALIDA'),(514,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-24 18:32:42.000000',1,'SALIDA'),(515,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-23 18:32:42.000000',1,'SALIDA'),(516,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-22 18:32:42.000000',1,'SALIDA'),(517,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-21 18:32:42.000000',1,'SALIDA'),(518,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-20 18:32:42.000000',1,'SALIDA'),(519,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-19 18:32:42.000000',1,'SALIDA'),(520,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-18 18:32:42.000000',1,'SALIDA'),(521,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-17 18:32:42.000000',1,'SALIDA'),(522,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-16 18:32:42.000000',1,'SALIDA'),(523,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-15 18:32:42.000000',1,'SALIDA'),(524,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-14 18:32:42.000000',1,'SALIDA'),(525,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-13 18:32:42.000000',1,'SALIDA'),(526,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-12 18:32:42.000000',1,'SALIDA'),(527,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-11 18:32:42.000000',1,'SALIDA'),(528,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-10 18:32:42.000000',1,'SALIDA'),(529,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-09 18:32:42.000000',1,'SALIDA'),(530,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-08 18:32:42.000000',1,'SALIDA'),(531,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-07 18:32:42.000000',1,'SALIDA'),(532,7,NULL,NULL,17,'CP018 carga prueba riesgo quiebre','2026-05-06 18:32:42.000000',1,'SALIDA');
/*!40000 ALTER TABLE `movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FK5484f79o4gkfc3eud34p3h2wl` (`product_id`),
  CONSTRAINT `FK5484f79o4gkfc3eud34p3h2wl` FOREIGN KEY (`product_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delivery_date` datetime(6) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `order_number` varchar(255) NOT NULL,
  `status` enum('PENDING','CONFIRMED','IN_TRANSIT','DELIVERED','CANCELLED') NOT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `type` enum('PURCHASE','DISPATCH') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `supplier_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nthkiu7pgmnqnu86i2jyoe2v7` (`order_number`),
  KEY `FKdonshqnho1i2ac9m0yr697ybt` (`supplier_id`),
  CONSTRAINT `FKdonshqnho1i2ac9m0yr697ybt` FOREIGN KEY (`supplier_id`) REFERENCES `proveedores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2026-06-02 00:03:27.000000','2026-06-09 00:03:27.000000','Pedido urgente de medicamentos','2026-06-02 00:03:27.000000','ORD-2025-001','PENDING',1250.00,'PURCHASE','2026-06-02 00:03:27.000000',1),(2,'2026-06-02 00:03:27.000000','2026-06-23 18:50:57.000000','Pedido confirmado por proveedor','2026-06-02 00:03:27.000000','ORD-2025-002','CONFIRMED',3450.50,'PURCHASE','2026-06-20 18:50:57.000000',2),(3,'2026-06-02 00:03:27.000000','2026-06-27 18:50:57.000000','En camino desde Lima','2026-05-30 00:03:27.000000','ORD-2025-003','IN_TRANSIT',890.75,'PURCHASE','2026-06-20 18:50:57.000000',1),(4,'2026-06-02 00:03:27.000000','2026-05-30 00:03:27.000000','Entregado satisfactoriamente','2026-05-23 00:03:27.000000','ORD-2025-004','DELIVERED',2100.00,'PURCHASE','2026-06-02 00:03:27.000000',3),(5,'2026-06-02 00:03:27.000000','2026-05-25 00:03:27.000000','Recibido completo','2026-05-18 00:03:27.000000','ORD-2025-005','DELIVERED',1560.25,'PURCHASE','2026-06-02 00:03:27.000000',2),(6,'2026-06-02 00:03:27.000000',NULL,'Cancelado por falta de stock del proveedor','2026-05-28 00:03:27.000000','ORD-2025-006','CANCELLED',750.00,'PURCHASE','2026-06-02 00:03:27.000000',1),(7,'2026-06-02 00:03:27.000000','2026-06-12 00:03:27.000000','Pedido pendiente de confirmación','2026-06-02 00:03:27.000000','ORD-2025-007','PENDING',980.50,'PURCHASE','2026-06-02 00:03:27.000000',3),(8,'2026-06-02 00:03:27.000000','2026-07-02 18:50:57.000000','En tránsito','2026-05-31 00:03:27.000000','ORD-2025-008','IN_TRANSIT',2340.00,'PURCHASE','2026-06-20 18:50:57.000000',2),(9,'2026-06-02 00:03:27.000000','2026-07-08 18:50:57.000000','Confirmado - llegada próxima semana','2026-06-02 00:03:27.000000','ORD-2025-009','CONFIRMED',1680.75,'PURCHASE','2026-06-20 18:50:57.000000',1),(10,'2026-06-02 00:03:27.000000','2026-05-21 00:03:27.000000','Entrega exitosa','2026-05-13 00:03:27.000000','ORD-2025-010','DELIVERED',3200.00,'PURCHASE','2026-06-02 00:03:27.000000',3);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(40) DEFAULT NULL,
  `controlado` int DEFAULT NULL,
  `creado_en` datetime(6) DEFAULT NULL,
  `nombre` varchar(150) NOT NULL,
  `notas` varchar(255) DEFAULT NULL,
  `requiere_refrigeracion` int DEFAULT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `unidad_id` bigint DEFAULT NULL,
  `precio_venta` decimal(10,2) DEFAULT '0.00' COMMENT 'Precio de venta sugerido por unidad',
  `bloqueado` bit(1) DEFAULT b'0',
  `motivo_bloqueo` varchar(255) DEFAULT NULL,
  `bloqueado_en` datetime DEFAULT NULL,
  `bloqueado_por` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2fwq10nwymfv7fumctxt9vpgb` (`categoria_id`),
  KEY `FK4lfqhqtbf1xjs43fmhcbdlvhq` (`unidad_id`),
  CONSTRAINT `FK2fwq10nwymfv7fumctxt9vpgb` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `FK4lfqhqtbf1xjs43fmhcbdlvhq` FOREIGN KEY (`unidad_id`) REFERENCES `unidades` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'',0,NULL,'Paracetamol 500mg','',0,1,2,5.00,_binary '\0',NULL,NULL,NULL),(2,'',0,NULL,'Amoxicilina 500 mg','',0,2,2,8.50,_binary '\0',NULL,NULL,NULL),(3,'MED-003',0,NULL,'Ibuprofeno 400 mg',NULL,0,3,2,12.75,_binary '\0',NULL,NULL,NULL),(4,'MED-004',0,NULL,'Salbutamol Inhalador',NULL,0,5,1,15.50,_binary '\0',NULL,NULL,NULL),(5,'MED-005',0,NULL,'Losart├ín 50 mg',NULL,0,6,2,18.90,_binary '\0',NULL,NULL,NULL),(6,'MED-006',0,NULL,'Vitamina C 1000mg',NULL,0,4,2,22.00,_binary '\0',NULL,NULL,NULL),(7,'MED-007',0,NULL,'Omeprazol 20mg',NULL,0,7,2,28.50,_binary '\0',NULL,NULL,NULL),(8,'MED-008',0,NULL,'Loratadina 10mg',NULL,0,8,2,32.75,_binary '\0',NULL,NULL,NULL),(9,'MED-009',0,NULL,'Azitromicina 500mg',NULL,0,2,2,38.00,_binary '','123','2026-06-02 22:25:18',1),(10,'MED-010',0,NULL,'Metformina 850mg',NULL,0,1,2,45.50,_binary '\0',NULL,NULL,NULL),(11,'MED-011',0,NULL,'Atorvastatina 20mg',NULL,0,6,2,5.00,_binary '\0',NULL,NULL,NULL),(12,'MED-012',0,NULL,'Diclofenaco 50mg',NULL,0,3,2,22.00,_binary '\0',NULL,NULL,NULL),(13,'MED-013',1,NULL,'Clonazepam 2mg',NULL,0,1,2,5.00,_binary '\0',NULL,NULL,NULL),(14,'MED-014',0,NULL,'Fluconazol 150mg',NULL,0,10,1,28.50,_binary '\0',NULL,NULL,NULL),(15,'MED-015',0,NULL,'Acetilciste├¡na 600mg',NULL,0,1,7,18.90,_binary '\0',NULL,NULL,NULL),(16,'MED-016',0,NULL,'Dexametasona 4mg',NULL,0,3,1,32.75,_binary '\0',NULL,NULL,NULL),(17,'MED-017',0,NULL,'Cetirizina 10mg',NULL,0,8,2,5.00,_binary '\0',NULL,NULL,NULL),(18,'MED-018',0,NULL,'Furosemida 40mg',NULL,0,6,2,38.00,_binary '\0',NULL,NULL,NULL),(19,'MED-019',0,NULL,'Ketoprofeno Gel',NULL,0,3,8,5.00,_binary '\0',NULL,NULL,NULL),(20,'MED-020',0,NULL,'Complejo B Inyectable',NULL,1,4,6,45.50,_binary '\0',NULL,NULL,NULL),(21,'MED-021',0,NULL,'Ciprofloxacino 500mg',NULL,0,2,2,28.50,_binary '\0',NULL,NULL,NULL),(22,'MED-022',0,NULL,'Ranitidina 150mg',NULL,0,7,2,8.50,_binary '\0',NULL,NULL,NULL),(23,'MED-023',0,NULL,'Enalapril 10mg',NULL,0,6,2,5.00,_binary '\0',NULL,NULL,NULL),(24,'MED-024',1,NULL,'Tramadol 50mg',NULL,0,1,2,32.75,_binary '\0',NULL,NULL,NULL),(25,'MED-025',0,NULL,'Prednisona 5mg',NULL,0,3,2,18.90,_binary '\0',NULL,NULL,NULL),(26,'MED-026',0,NULL,'Clorfenamina 4mg',NULL,0,8,2,8.50,_binary '\0',NULL,NULL,NULL),(27,'MED-027',0,NULL,'Hidrocortisona Crema',NULL,0,3,8,38.00,_binary '\0',NULL,NULL,NULL),(28,'MED-028',1,NULL,'Insulina NPH',NULL,1,1,6,28.50,_binary '\0',NULL,NULL,NULL),(29,'MED-029',0,NULL,'Ketorolaco 10mg',NULL,0,1,2,5.00,_binary '\0',NULL,NULL,NULL),(30,'MED-030',0,NULL,'Clotrimazol ├ôvulos',NULL,0,10,1,45.50,_binary '\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos_proveedores`
--

DROP TABLE IF EXISTS `productos_proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos_proveedores` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` bit(1) NOT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `numero_id_lote` bigint NOT NULL,
  `producto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6kxf1o0wmjsy07p010epwvo8x` (`numero_id_lote`),
  KEY `FKe5o3x8dnnov56t5n1hwd7mvaa` (`producto_id`),
  CONSTRAINT `FK6kxf1o0wmjsy07p010epwvo8x` FOREIGN KEY (`numero_id_lote`) REFERENCES `lotes` (`id`),
  CONSTRAINT `FKe5o3x8dnnov56t5n1hwd7mvaa` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos_proveedores`
--

LOCK TABLES `productos_proveedores` WRITE;
/*!40000 ALTER TABLE `productos_proveedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `productos_proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pronostico`
--

DROP TABLE IF EXISTS `pronostico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pronostico` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `demanda_prevista` int DEFAULT NULL,
  `metodo` varchar(40) DEFAULT NULL,
  `periodo_fin` date DEFAULT NULL,
  `periodo_inicio` date DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  `sede_id` bigint DEFAULT NULL,
  `stock_seguridad` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pronostico`
--

LOCK TABLES `pronostico` WRITE;
/*!40000 ALTER TABLE `pronostico` DISABLE KEYS */;
/*!40000 ALTER TABLE `pronostico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedores` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `column5` int DEFAULT NULL,
  `column6` int DEFAULT NULL,
  `column7` varchar(255) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `nombre` varchar(150) NOT NULL,
  `telefono` varchar(40) DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,NULL,NULL,NULL,'samuelchamorro2020@gmail.com','Ronald Martin','987 898 677',_binary ''),(2,NULL,NULL,NULL,'samuelchamorro2020@gmail.com','Mar├¡a Gonz├ílez SAC','956 789 123',_binary ''),(3,NULL,NULL,NULL,'samuelchamorro2020@gmail.com','FarmaPlus SAC','945 123 456',_binary ''),(4,NULL,NULL,NULL,'samuelchamorro2020@gmail.com','MediDistribuidora S.A.','901 234 567',_binary ''),(5,NULL,NULL,NULL,'samuelchamorro2020@gmail.com','Droguer├¡a Lima','987 654 321',_binary '');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrador'),(2,'Farmaceutico'),(3,'Auxiliar de Almacen'),(5,'Jefe de Farmacia');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sedes`
--

DROP TABLE IF EXISTS `sedes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sedes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(20) NOT NULL,
  `column4` varchar(255) DEFAULT NULL,
  `column5` varchar(255) DEFAULT NULL,
  `column6` varchar(255) DEFAULT NULL,
  `column7` varchar(255) DEFAULT NULL,
  `nombre` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sedes`
--

LOCK TABLES `sedes` WRITE;
/*!40000 ALTER TABLE `sedes` DISABLE KEYS */;
INSERT INTO `sedes` VALUES (1,'SEDE-001',NULL,NULL,NULL,NULL,'Cl├¡nica Principal - Lima'),(2,'SEDE-002',NULL,NULL,NULL,NULL,'Sucursal Norte - Trujillo'),(3,'SEDE-003',NULL,NULL,NULL,NULL,'Sucursal Sur - Arequipa');
/*!40000 ALTER TABLE `sedes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `alert_value` int DEFAULT NULL,
  `auto_update` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date_format` varchar(255) DEFAULT NULL,
  `expiration_days` int DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `notify_by_email` bit(1) DEFAULT NULL,
  `notify_expiring` bit(1) DEFAULT NULL,
  `notify_low_stock` bit(1) DEFAULT NULL,
  `notify_new_orders` bit(1) DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  `currency` varchar(10) DEFAULT 'PEN',
  `supplier_email` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_iolw98qy0bp9gqo2u0pnxmx39` (`user_id`),
  CONSTRAINT `FKekbam63h9wid6cxf0u13a78lm` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES (1,10,_binary '','2026-06-01 23:54:38.363142','mm/dd/yyyy',30,'en',_binary '\0',_binary '',_binary '',_binary '','Europe/Madrid','USD',NULL,'2026-06-06 12:57:23.876768',5),(2,10,_binary '','2026-06-06 12:57:51.744671','yyyy-mm-dd',30,'es',_binary '\0',_binary '',_binary '',_binary '','America/New_York','USD',NULL,'2026-06-06 13:27:57.317329',1);
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitudes_compra`
--

DROP TABLE IF EXISTS `solicitudes_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitudes_compra` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aprobada` bit(1) DEFAULT NULL,
  `creada_en` datetime(6) DEFAULT NULL,
  `notas` varchar(255) DEFAULT NULL,
  `origen` int DEFAULT NULL,
  `sede_id` bigint DEFAULT NULL,
  `solicitado_por` bigint DEFAULT NULL,
  `email_sent` bit(1) NOT NULL,
  `request_date` datetime(6) DEFAULT NULL,
  `status` enum('DRAFT','PENDING','SENT','SEND_FAILED','CONFIRMED','IN_TRANSIT','DELIVERED','CANCELLED') DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `supplier_id` bigint DEFAULT NULL,
  `requested_quantity` int DEFAULT '100',
  `current_stock` int DEFAULT NULL,
  `alert_level` int DEFAULT NULL,
  `email_subject` varchar(500) DEFAULT NULL,
  `email_body` text,
  `aprobado_por` bigint DEFAULT NULL,
  `cantidad_ajustada` int DEFAULT NULL,
  `fecha_aprobacion` datetime(6) DEFAULT NULL,
  `generado_automaticamente` bit(1) DEFAULT NULL,
  `motivo_ajuste` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpoma95h2orjlg89m2g8uqb05k` (`sede_id`),
  KEY `FK2ghkjbj6p046qomhve7lmd09p` (`solicitado_por`),
  KEY `FKlyi9ct3354sbij8yswcuf4dtp` (`product_id`),
  KEY `FKglrva4dstphktfckp48qjjqgj` (`supplier_id`),
  CONSTRAINT `FK2ghkjbj6p046qomhve7lmd09p` FOREIGN KEY (`solicitado_por`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKglrva4dstphktfckp48qjjqgj` FOREIGN KEY (`supplier_id`) REFERENCES `proveedores` (`id`),
  CONSTRAINT `FKlyi9ct3354sbij8yswcuf4dtp` FOREIGN KEY (`product_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `FKpoma95h2orjlg89m2g8uqb05k` FOREIGN KEY (`sede_id`) REFERENCES `sedes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitudes_compra`
--

LOCK TABLES `solicitudes_compra` WRITE;
/*!40000 ALTER TABLE `solicitudes_compra` DISABLE KEYS */;
INSERT INTO `solicitudes_compra` VALUES (1,_binary '','2025-11-09 18:44:43.857246','',1,1,5,_binary '\0','2025-11-09 18:44:43.853224','SENT',5,1,100,80,10,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,'2025-11-10 17:54:02.321876',NULL,NULL,NULL,NULL,_binary '','2025-11-10 17:54:02.319864','CONFIRMED',2,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,'2025-11-10 17:55:20.229993',NULL,NULL,NULL,NULL,_binary '','2025-11-10 17:55:20.229993','PENDING',2,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,'2025-11-10 21:50:44.728009',NULL,NULL,NULL,NULL,_binary '','2025-11-10 21:50:44.722502','PENDING',2,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,'2025-11-15 12:09:05.061803',NULL,NULL,NULL,NULL,_binary '','2025-11-15 12:09:05.057606','PENDING',30,1,100,80,25,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,NULL,'2025-11-15 13:22:45.807506',NULL,NULL,NULL,NULL,_binary '','2025-11-15 13:22:45.806507','PENDING',1,1,100,100,21,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,NULL,'2025-11-17 21:44:27.201437',NULL,NULL,NULL,NULL,_binary '','2025-11-17 21:44:27.197887','PENDING',1,1,100,100,21,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,NULL,'2025-11-22 12:22:12.450512',NULL,NULL,NULL,NULL,_binary '','2025-11-22 12:22:12.450512','PENDING',9,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,NULL,'2025-11-24 18:15:02.851903',NULL,NULL,NULL,NULL,_binary '','2025-11-24 18:15:02.840838','PENDING',9,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,NULL,'2025-11-24 18:17:08.093228',NULL,NULL,NULL,NULL,_binary '','2025-11-24 18:17:08.092226','PENDING',9,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,NULL,'2025-11-24 18:17:44.739158',NULL,NULL,NULL,NULL,_binary '','2025-11-24 18:17:44.739158','PENDING',9,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,NULL,'2025-11-24 18:19:27.707780',NULL,NULL,NULL,NULL,_binary '','2025-11-24 18:19:27.706778','PENDING',9,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,NULL,'2025-11-24 18:21:09.099290',NULL,NULL,NULL,NULL,_binary '','2025-11-24 18:21:09.099290','PENDING',9,1,100,80,17,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,NULL,'2025-11-26 21:48:26.271651',NULL,NULL,NULL,NULL,_binary '','2025-11-26 21:48:26.270652','PENDING',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,NULL,'2025-11-26 21:53:14.466480',NULL,NULL,NULL,NULL,_binary '','2025-11-26 21:53:14.466480','PENDING',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,NULL,'2025-11-27 00:08:58.436658',NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:08:58.435656','SENT',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(17,NULL,'2025-11-27 00:09:52.909293',NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:09:52.908294','SENT',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(18,NULL,'2025-11-27 00:16:36.842830',NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:16:36.842830','SENT',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,NULL,'2025-11-27 00:18:52.838072',NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:18:52.837071','SENT',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,NULL,'2025-11-27 00:20:30.723042',NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:20:30.723042','SENT',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,NULL,'2025-11-27 00:21:25.660551',NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:21:25.660551','SENT',4,1,100,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,NULL,'2025-11-27 00:46:09.697124',NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:46:09.695617','SENT',4,1,100,5,14,'Solicitud de reabastecimiento - Salbutamol Inhalador','Estimado proveedor Samuel,\n\nSolicitamos el envío de 100 unidades de Salbutamol Inhalador para mantener el nivel de stock óptimo.\n\nStock actual: null unidades\nNivel de alerta: null unidades\n\nAtentamente,\nClínica Vestida de Sol - Área de Farmacia.',NULL,NULL,NULL,NULL,NULL),(23,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','2025-11-27 00:46:41.000000','SENT',4,1,150,5,14,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,NULL,'2025-11-27 01:06:33.972274',NULL,NULL,NULL,NULL,_binary '','2025-11-27 01:06:33.966261','SENT',4,1,37,5,14,'Solicitud de reabastecimiento - Salbutamol Inhalador','Estimado proveedor Samuel,\n\nSolicitamos el envío de 37 unidades de Salbutamol Inhalador para mantener el nivel de stock óptimo.\n\nStock actual: null unidades\nNivel de alerta: null unidades\n\nAtentamente,\nClínica Vestida de Sol - Área de Farmacia.',NULL,NULL,NULL,NULL,NULL),(25,NULL,'2025-11-27 13:55:08.490629',NULL,NULL,NULL,NULL,_binary '','2025-11-27 13:55:08.487969','CONFIRMED',1,1,43,0,NULL,'Solicitud de reabastecimiento - Paracetamol 500mg','Estimado proveedor,\ngay\n\nSolicitamos el envío de 43 unidades de Paracetamol 500mg para mantener el nivel de stock óptimo.\n\nStock actual: 0 unidades\nNivel de alerta: null unidades\n\nAtentamente,\nClínica Vestida de Sol - Área de Farmacia.',NULL,NULL,NULL,NULL,NULL),(26,_binary '\0',NULL,'Borrador de prueba - CP008: Paracetamol',1,1,1,_binary '\0','2026-06-01 22:35:20.000000','DRAFT',3,1,200,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '\0',NULL),(27,_binary '\0',NULL,'CP008: Revisar cantidades de Amoxicilina',1,1,2,_binary '\0','2026-06-01 22:35:20.000000','DRAFT',2,2,200,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '\0',NULL),(28,_binary '\0',NULL,'CP008: Ibuprofeno pendiente de aprobación',1,1,1,_binary '\0','2026-06-01 22:35:20.000000','DRAFT',3,1,150,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '\0',NULL),(29,_binary '','2026-06-20 18:51:35.000000',NULL,NULL,1,1,_binary '\0','2026-06-20 18:51:35.000000','SEND_FAILED',5,1,50,80,36,NULL,NULL,NULL,NULL,NULL,_binary '\0',NULL),(30,_binary '','2026-06-20 18:51:35.000000',NULL,NULL,1,1,_binary '','2026-06-20 18:51:35.000000','IN_TRANSIT',6,1,60,12,19,NULL,NULL,NULL,NULL,NULL,_binary '\0',NULL),(31,_binary '','2026-06-20 18:51:35.000000',NULL,NULL,1,1,_binary '','2026-06-20 18:51:35.000000','DELIVERED',7,1,70,30,11,NULL,NULL,NULL,NULL,NULL,_binary '\0',NULL);
/*!40000 ALTER TABLE `solicitudes_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitudes_compra_det`
--

DROP TABLE IF EXISTS `solicitudes_compra_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitudes_compra_det` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_sugerida` int DEFAULT NULL,
  `column6` varchar(255) DEFAULT NULL,
  `column7` varchar(255) DEFAULT NULL,
  `motivo` varchar(160) DEFAULT NULL,
  `producto_id` bigint NOT NULL,
  `solicitud_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9onxv9ufafptt8fq102g7vuga` (`producto_id`),
  KEY `FKaotqxqd2nuylnyhv32dwgwd55` (`solicitud_id`),
  CONSTRAINT `FK9onxv9ufafptt8fq102g7vuga` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `FKaotqxqd2nuylnyhv32dwgwd55` FOREIGN KEY (`solicitud_id`) REFERENCES `solicitudes_compra` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitudes_compra_det`
--

LOCK TABLES `solicitudes_compra_det` WRITE;
/*!40000 ALTER TABLE `solicitudes_compra_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitudes_compra_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabajos_rpa`
--

DROP TABLE IF EXISTS `trabajos_rpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trabajos_rpa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email_message_id` varchar(120) DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `finalizado_en` datetime(6) DEFAULT NULL,
  `orchestrator_job_id` varchar(80) DEFAULT NULL,
  `solicitado_en` datetime(6) DEFAULT NULL,
  `solicitud_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajos_rpa`
--

LOCK TABLES `trabajos_rpa` WRITE;
/*!40000 ALTER TABLE `trabajos_rpa` DISABLE KEYS */;
INSERT INTO `trabajos_rpa` VALUES (1,NULL,'PENDING',NULL,NULL,'2026-06-01 22:25:01.949351',1);
/*!40000 ALTER TABLE `trabajos_rpa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `umbral_stock`
--

DROP TABLE IF EXISTS `umbral_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `umbral_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `column5` varchar(255) DEFAULT NULL,
  `column7` varchar(255) DEFAULT NULL,
  `minimo` int DEFAULT NULL,
  `punto_pedido` int DEFAULT NULL,
  `stock_seguridad` int DEFAULT NULL,
  `producto_id` bigint NOT NULL,
  `sede_id` bigint NOT NULL,
  `umbral_cobertura_dias` int DEFAULT '15' COMMENT 'HU-17: D├¡as m├¡nimos de cobertura (stock/consumo_diario). Alerta si cobertura < umbral',
  `stock_maximo` int DEFAULT NULL COMMENT 'Stock m├íximo permitido (HU-04.3). Pedidos autom├íticos se ajustan para no exceder este l├¡mite.',
  PRIMARY KEY (`id`),
  KEY `FK75gde3occskr81va61ao4ihs9` (`producto_id`),
  KEY `FKpna8utfe726i5yb62okhf8p5h` (`sede_id`),
  KEY `idx_umbrales_cobertura` (`umbral_cobertura_dias`),
  KEY `idx_stock_maximo` (`stock_maximo`),
  CONSTRAINT `FK75gde3occskr81va61ao4ihs9` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `FKpna8utfe726i5yb62okhf8p5h` FOREIGN KEY (`sede_id`) REFERENCES `sedes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `umbral_stock`
--

LOCK TABLES `umbral_stock` WRITE;
/*!40000 ALTER TABLE `umbral_stock` DISABLE KEYS */;
INSERT INTO `umbral_stock` VALUES (1,NULL,NULL,21,31,75,1,1,15,100),(2,NULL,NULL,17,40,84,2,1,15,100),(3,NULL,NULL,18,57,69,3,1,15,NULL),(4,NULL,NULL,14,57,97,4,1,15,NULL),(5,NULL,NULL,10,36,51,5,1,15,NULL),(6,NULL,NULL,19,39,60,6,1,15,90),(7,NULL,NULL,11,52,74,7,1,15,NULL),(8,NULL,NULL,15,53,56,8,1,15,NULL),(9,NULL,NULL,17,40,81,9,1,15,NULL),(10,NULL,NULL,13,56,98,10,1,15,NULL),(11,NULL,NULL,12,54,85,11,1,15,NULL),(12,NULL,NULL,10,33,71,12,1,15,NULL),(13,NULL,NULL,25,48,87,13,1,15,NULL),(14,NULL,NULL,28,41,58,14,1,15,NULL),(15,NULL,NULL,23,56,69,15,1,15,NULL),(16,NULL,NULL,15,35,52,16,1,15,NULL),(17,NULL,NULL,25,48,91,17,1,15,NULL),(18,NULL,NULL,16,32,73,18,1,15,NULL),(19,NULL,NULL,11,57,68,19,1,15,NULL),(20,NULL,NULL,12,42,89,20,1,15,NULL),(21,NULL,NULL,23,31,62,21,1,15,NULL),(22,NULL,NULL,11,48,94,22,1,15,NULL),(23,NULL,NULL,22,42,57,23,1,15,NULL),(24,NULL,NULL,20,37,78,24,1,15,NULL),(25,NULL,NULL,13,35,65,25,1,15,NULL),(26,NULL,NULL,11,41,84,26,1,15,NULL),(27,NULL,NULL,15,41,50,27,1,15,NULL),(28,NULL,NULL,28,46,50,28,1,15,NULL),(29,NULL,NULL,18,34,72,29,1,15,NULL),(30,NULL,NULL,25,43,99,30,1,15,NULL);
/*!40000 ALTER TABLE `umbral_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidades`
--

DROP TABLE IF EXISTS `unidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidades` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(20) NOT NULL,
  `column4` varchar(255) DEFAULT NULL,
  `column5` varchar(255) DEFAULT NULL,
  `column7` varchar(255) DEFAULT NULL,
  `nombre` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidades`
--

LOCK TABLES `unidades` WRITE;
/*!40000 ALTER TABLE `unidades` DISABLE KEYS */;
INSERT INTO `unidades` VALUES (1,'UND',NULL,NULL,NULL,'Unidad'),(2,'CAJ',NULL,NULL,NULL,'Caja'),(3,'BLS',NULL,NULL,NULL,'Bolsa'),(4,'FRS',NULL,NULL,NULL,'Frasco'),(5,'TAB',NULL,NULL,NULL,'Tableta'),(6,'AMP',NULL,NULL,NULL,'Ampolla'),(7,'SOB',NULL,NULL,NULL,'Sobre'),(8,'TUB',NULL,NULL,NULL,'Tubo');
/*!40000 ALTER TABLE `unidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` int DEFAULT NULL,
  `creado_en` datetime(6) DEFAULT NULL,
  `email` varchar(120) NOT NULL,
  `nombre_completo` varchar(120) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `rol_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kfsp0s1tflm1cwlj8idhqsad0` (`email`),
  KEY `FKqf5elo4jcq7qrt83oi0qmenjo` (`rol_id`),
  CONSTRAINT `FKqf5elo4jcq7qrt83oi0qmenjo` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,1,NULL,'admin@medic.com','Admin Sistema','$2a$10$j5hfd010eY7BV7teZnr56.LX4Oe15dGeBTqZ2ctZr6v0Zdro7S3E.',1),(2,1,NULL,'juan.perez@medic.com','Juan P├®rez Garc├¡a','$2a$10$xKEK2q42JlDwJOS8VB6tTONozN6XbVmj2Qu2Xf8mbXSzNAwBkVkey',3),(3,1,NULL,'ana.torres@medic.com','Dra. Ana Torres','$2a$10$1cE7uwyhtKbH8Tk2/iWEl.U3vIhWVpbY5yxM36nvrJQwI7wm6boAi',2),(5,1,'2025-11-09 18:09:14.000000','jefe@medic.com','Dr. Luis Martínez - Jefe de Farmacia','$2a$10$GVm.GUjPgI8zyCDeSBewfeh/cTZk94Xd6gwyt5AuiF5K.9LON7BQ6',5),(6,1,'2026-06-01 23:02:56.876845','francos@gmail.com','Franco','$2a$10$YuV9q0Ssw3RvpOL8bRHUX.LdXRwmDIRLCQjPkswhFU4aaYF9J7xPG',3);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'medic_inventory'
--

--
-- Dumping routines for database 'medic_inventory'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-20 18:52:02
