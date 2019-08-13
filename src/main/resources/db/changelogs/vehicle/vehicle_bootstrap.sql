--liquibase formatted sql

--changeset cspence:01

CREATE SCHEMA IF NOT EXISTS `cardealer` ;
USE `cardealer` ;

-- -----------------------------------------------------
-- Table `cardealer`.`vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cardealer`.`vehicle` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `registration` VARCHAR(7) NOT NULL,
  `type` VARCHAR(5) NOT NULL,
  `model` VARCHAR(20) NOT NULL,
  `make` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Data for table `cardealer`.`vehicle`
-- -----------------------------------------------------

USE `cardealer`;
  INSERT INTO `cardealer`.`vehicle` (`registration`, `type`, `make`, `model`)
  VALUES
    ('AAAAAA', 'car', 'Ford', 'Mustang'),
    ('BBBBBB', 'van', 'Ford', 'Transit'),
    ('CCCCCC', 'car', 'Nissan', 'Skyline'),
    ('DDDDDD', 'car', 'Volvo', 'xc90');
