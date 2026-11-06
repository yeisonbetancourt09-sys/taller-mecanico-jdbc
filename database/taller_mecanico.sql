-- Script de base de datos: Módulo Clientes y Vehículos
CREATE DATABASE IF NOT EXISTS taller_mecanico
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE taller_mecanico;

CREATE TABLE IF NOT EXISTS clientes (
  id_cliente INT AUTO_INCREMENT PRIMARY KEY,
  documento  VARCHAR(20)  NOT NULL UNIQUE,
  nombre     VARCHAR(100) NOT NULL,
  telefono   VARCHAR(20)  NOT NULL,
  email      VARCHAR(100),
  direccion  VARCHAR(150)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS vehiculos (
  id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
  placa       VARCHAR(10) NOT NULL UNIQUE,
  marca       VARCHAR(50) NOT NULL,
  modelo      VARCHAR(50) NOT NULL,
  anio        INT NOT NULL,
  color       VARCHAR(30),
  id_cliente  INT NOT NULL,
  CONSTRAINT fk_vehiculo_cliente FOREIGN KEY (id_cliente)
    REFERENCES clientes (id_cliente)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

INSERT INTO clientes (documento, nombre, telefono, email, direccion) VALUES
 ('1006123456', 'Carlos Ramírez', '3101234567', 'carlos@correo.com', 'Calle 10 # 5-20'),
 ('1006654321', 'Laura Gómez',    '3207654321', 'laura@correo.com',  'Carrera 8 # 12-30');
