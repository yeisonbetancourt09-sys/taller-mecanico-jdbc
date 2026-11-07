-- Esquema y datos reales del taller (exportado de MySQL Workbench)
CREATE DATABASE IF NOT EXISTS taller_mecanico CHARACTER SET utf8mb4;
USE taller_mecanico;

-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: taller_mecanico
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `documento` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `documento` (`documento`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'1002345678','Carlos Mendoza','3109876543','carlos.mendoza@email.com','Calle 10 #15-20','2026-08-29 15:48:01'),(2,'1008765432','Ana María Gómez','3151234567','ana.gomez@email.com','Carrera 5 #8-12','2026-08-29 15:48:01'),(3,'9901122334','Jorge Ramírez','3005554433','jorge.ramirez@email.com','Avenida 40 #22-05','2026-08-29 15:48:01'),(4,'1003456789','Laura Sofía Restrepo','3201234567','laura.restrepo@gmail.com','Calle 15 #12-40','2026-09-06 21:28:38'),(5,'1009876543','David Fernando Castro','3189876543','david.castro@hotmail.com','Carrera 20 #8-15','2026-09-06 21:28:38'),(6,'1005554433','Camila Andrea Torres','3115556677','camila.torres@yahoo.com','Calle 8 #4-30','2026-09-06 21:28:38'),(7,'1007778899','Felipe Santos Martínez','3147778899','felipe.santos@gmail.com','Avenida 30 #45-12','2026-09-06 21:28:38');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mecanicos`
--

DROP TABLE IF EXISTS `mecanicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mecanicos` (
  `id_mecanico` int NOT NULL AUTO_INCREMENT,
  `documento` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `especialidad` varchar(50) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_mecanico`),
  UNIQUE KEY `documento` (`documento`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mecanicos`
--

LOCK TABLES `mecanicos` WRITE;
/*!40000 ALTER TABLE `mecanicos` DISABLE KEYS */;
INSERT INTO `mecanicos` VALUES (1,'80123456','Roberto Pérez','Sistemas de Frenos y Suspensión','3114445566'),(2,'80654321','Luis Fernando Torres','Electricidad e Inyección','3127778899'),(3,'80234567','Gustavo Adolfo Blanco','Motor y Transmisión','3123334455'),(4,'80345678','Andrés Felipe Morales','Electricidad y Diagnóstico Scanner','3134445566'),(5,'80456789','Javier Enrique Ortiz','Alineación y Balanceo','3145556677');
/*!40000 ALTER TABLE `mecanicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orden_repuestos`
--

DROP TABLE IF EXISTS `orden_repuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orden_repuestos` (
  `id_detalle_repuesto` int NOT NULL AUTO_INCREMENT,
  `id_orden` int NOT NULL,
  `id_repuesto` int NOT NULL,
  `cantidad` int NOT NULL DEFAULT '1',
  `precio_unitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) GENERATED ALWAYS AS ((`cantidad` * `precio_unitario`)) STORED,
  PRIMARY KEY (`id_detalle_repuesto`),
  KEY `id_orden` (`id_orden`),
  KEY `id_repuesto` (`id_repuesto`),
  CONSTRAINT `orden_repuestos_ibfk_1` FOREIGN KEY (`id_orden`) REFERENCES `ordenes_trabajo` (`id_orden`) ON DELETE CASCADE,
  CONSTRAINT `orden_repuestos_ibfk_2` FOREIGN KEY (`id_repuesto`) REFERENCES `repuestos` (`id_repuesto`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden_repuestos`
--

LOCK TABLES `orden_repuestos` WRITE;
/*!40000 ALTER TABLE `orden_repuestos` DISABLE KEYS */;
INSERT INTO `orden_repuestos` (`id_detalle_repuesto`, `id_orden`, `id_repuesto`, `cantidad`, `precio_unitario`) VALUES (1,1,2,1,85000.00),(27,1,2,1,85000.00),(28,1,6,4,35000.00),(29,2,5,1,120000.00),(30,2,7,1,28000.00);
/*!40000 ALTER TABLE `orden_repuestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orden_servicios`
--

DROP TABLE IF EXISTS `orden_servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orden_servicios` (
  `id_detalle_servicio` int NOT NULL AUTO_INCREMENT,
  `id_orden` int NOT NULL,
  `id_servicio` int NOT NULL,
  `precio_aplicado` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_detalle_servicio`),
  KEY `id_orden` (`id_orden`),
  KEY `id_servicio` (`id_servicio`),
  CONSTRAINT `orden_servicios_ibfk_1` FOREIGN KEY (`id_orden`) REFERENCES `ordenes_trabajo` (`id_orden`) ON DELETE CASCADE,
  CONSTRAINT `orden_servicios_ibfk_2` FOREIGN KEY (`id_servicio`) REFERENCES `servicios` (`id_servicio`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden_servicios`
--

LOCK TABLES `orden_servicios` WRITE;
/*!40000 ALTER TABLE `orden_servicios` DISABLE KEYS */;
INSERT INTO `orden_servicios` VALUES (1,1,2,65000.00),(2,1,2,40000.00),(3,1,4,120000.00),(4,2,5,90000.00),(5,4,3,180000.00),(6,4,4,120000.00);
/*!40000 ALTER TABLE `orden_servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenes_trabajo`
--

DROP TABLE IF EXISTS `ordenes_trabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes_trabajo` (
  `id_orden` int NOT NULL AUTO_INCREMENT,
  `fecha_ingreso` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_salida` datetime DEFAULT NULL,
  `estado` enum('Pendiente','En Proceso','Completado','Entregado','Cancelado') DEFAULT 'Pendiente',
  `falla_reportada` text NOT NULL,
  `diagnostico` text,
  `id_vehiculo` int NOT NULL,
  `id_mecanico` int NOT NULL,
  `total` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id_orden`),
  KEY `id_vehiculo` (`id_vehiculo`),
  KEY `id_mecanico` (`id_mecanico`),
  CONSTRAINT `ordenes_trabajo_ibfk_1` FOREIGN KEY (`id_vehiculo`) REFERENCES `vehiculos` (`id_vehiculo`),
  CONSTRAINT `ordenes_trabajo_ibfk_2` FOREIGN KEY (`id_mecanico`) REFERENCES `mecanicos` (`id_mecanico`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenes_trabajo`
--

LOCK TABLES `ordenes_trabajo` WRITE;
/*!40000 ALTER TABLE `ordenes_trabajo` DISABLE KEYS */;
INSERT INTO `ordenes_trabajo` VALUES (1,'2026-08-29 16:07:16',NULL,'En Proceso','El auto tiembla al frenar a alta velocidad','Discos delanteros al límite de desgaste y pastillas cristalizadas',1,1,0.00),(2,'2026-08-29 16:07:23',NULL,'En Proceso','El auto tiembla al frenar a alta velocidad','Discos delanteros al límite de desgaste y pastillas cristalizadas',1,1,0.00),(3,'2026-09-06 21:33:34',NULL,'Completado','Luz de Check Engine encendida y pérdida de potencia','Fallo de combustión en cilindro 2 por bujía desgastada',1,2,0.00),(4,'2026-09-06 21:33:34',NULL,'En Proceso','El aire acondicionado no enfría','Falta de gas por baja presión en el sistema',2,3,0.00),(5,'2026-09-06 21:33:34',NULL,'Pendiente','Ruido fuerte en la parte delantera al pasar baches','Amortiguador delantero derecho estallado',3,1,0.00),(6,'2026-09-06 21:33:34',NULL,'Completado','Mantenimiento preventivo de los 50.000 km','Se requiere cambio de correa de distribución y sincronización',4,1,0.00);
/*!40000 ALTER TABLE `ordenes_trabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repuestos`
--

DROP TABLE IF EXISTS `repuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repuestos` (
  `id_repuesto` int NOT NULL AUTO_INCREMENT,
  `codigo` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_repuesto`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repuestos`
--

LOCK TABLES `repuestos` WRITE;
/*!40000 ALTER TABLE `repuestos` DISABLE KEYS */;
INSERT INTO `repuestos` VALUES (2,'F-001','Pastillas de freno delanteras Bosch',85000.00,8),(5,'ACE-5W30','Aceite Sintético 5W30 (Galón)',120000.00,15),(6,'BUJ-NGK','Bujía Iridium NGK',35000.00,24),(7,'FIL-ACE','Filtro de Aceite Universal',28000.00,30),(8,'FIL-AIR','Filtro de Aire Motor',32000.00,20),(9,'AMR-DEL','Amortiguador Delantero Chevrolet',210000.00,6),(10,'COR-DIS','Correa de Distribución Gates',95000.00,10),(11,'LIQ-FRE','Líquido de Frenos DOT 4 (500ml)',22000.00,15),(12,'REFR-GAS','Gas Refrigerante R134a',45000.00,12);
/*!40000 ALTER TABLE `repuestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicios`
--

DROP TABLE IF EXISTS `servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicios` (
  `id_servicio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  `precio_base` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_servicio`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicios`
--

LOCK TABLES `servicios` WRITE;
/*!40000 ALTER TABLE `servicios` DISABLE KEYS */;
INSERT INTO `servicios` VALUES (1,'Cambio de Aceite y Filtro','Reemplazo de aceite sintético y filtro de aceite',45000.00),(2,'Mantenimiento de Frenos','Limpieza, rectificado y calibración',65000.00),(3,'Alineación y Balanceo','Alineación 3D de dirección y balanceo de 4 ruedas',50000.00),(4,'Diagnóstico con Scanner OBD-II','Lectura de códigos de error y borrado de Check Engine',40000.00),(5,'Cambio de Kit de Arrastre/Distribución','Instalación de correa de repartición y tensores',180000.00),(6,'Sincronización de Motor','Limpieza de inyectores, cuerpo de aceleración y bujías',120000.00),(7,'Carga de Aire Acondicionado','Recarga de gas refrigerante R134a y prueba de fugas',90000.00);
/*!40000 ALTER TABLE `servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehiculos` (
  `id_vehiculo` int NOT NULL AUTO_INCREMENT,
  `placa` varchar(15) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `anio` int NOT NULL,
  `color` varchar(30) DEFAULT NULL,
  `id_cliente` int NOT NULL,
  PRIMARY KEY (`id_vehiculo`),
  UNIQUE KEY `placa` (`placa`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `vehiculos_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
INSERT INTO `vehiculos` VALUES (1,'ABC123','Toyota','Corolla',2020,'Gris Plata',1),(2,'XYZ789','Chevrolet','Onix',2022,'Rojo Metalizado',2),(3,'KGM456','Chevrolet','Onix',2021,'Rojo',1),(4,'MXT789','Renault','Duster',2019,'Negro',2),(5,'BWR123','Mazda','CX-30',2023,'Blanco',3),(6,'JHL654','Ford','Fiesta',2018,'Azul',4),(7,'HGT321','Nissan','Frontier',2022,'Gris Plata',2);
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-06 19:42:56
