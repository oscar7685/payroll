/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 50542
Source Host           : localhost:3306
Source Database       : payroll

Target Server Type    : MYSQL
Target Server Version : 50542
File Encoding         : 65001

Date: 2016-02-14 14:50:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for employes
-- ----------------------------
DROP TABLE IF EXISTS `employes`;
CREATE TABLE `employes` (
  `cedula` varchar(45) NOT NULL,
  `fecha_expedicion` date NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `ultimo_cargo` varchar(255) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_final` date NOT NULL,
  `codigo_kg` varchar(45) NOT NULL,
  `numero_cuenta` varchar(45) NOT NULL,
  `ruta_certificado` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employes
-- ----------------------------
