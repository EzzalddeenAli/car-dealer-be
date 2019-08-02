--liquibase formatted sql

--changeset cspence:01

CREATE SCHEMA IF NOT EXISTS `cardealer` ;
USE `cardealer` ;

-- -----------------------------------------------------
-- Table `cardealer`.`vehicle_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cardealer`.`vehicle_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `typeid_UNIQUE` ON `cardealer`.`vehicle_type` (`id` ASC);

CREATE UNIQUE INDEX `name_UNIQUE` ON `cardealer`.`vehicle_type` (`name` ASC);


-- -----------------------------------------------------
-- Table `cardealer`.`vehicle_model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cardealer`.`vehicle_model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `modelid_UNIQUE` ON `cardealer`.`vehicle_model` (`id` ASC);


-- -----------------------------------------------------
-- Table `cardealer`.`vehicle_make`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cardealer`.`vehicle_make` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `makeid_UNIQUE` ON `cardealer`.`vehicle_make` (`id` ASC);


-- -----------------------------------------------------
-- Table `cardealer`.`images_vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cardealer`.`images_vehicle` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `url` MEDIUMTEXT NOT NULL,
  `unsplash_id` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `make` INT NULL,
  `model` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_images_vehicle_make`
    FOREIGN KEY (`make`)
    REFERENCES `cardealer`.`vehicle_make` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_images_vehicle_model`
    FOREIGN KEY (`model`)
    REFERENCES `cardealer`.`vehicle_model` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `imageid_UNIQUE` ON `cardealer`.`images_vehicle` (`id` ASC);

CREATE INDEX `fk_images_vehicle_make_idx` ON `cardealer`.`images_vehicle` (`make` ASC);

CREATE INDEX `fk_images_vehicle_model_idx` ON `cardealer`.`images_vehicle` (`model` ASC);


-- -----------------------------------------------------
-- Table `cardealer`.`vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cardealer`.`vehicle` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `registration` VARCHAR(7) NOT NULL,
  `vehicle_type_id` INT NOT NULL,
  `vehicle_model_id` INT NOT NULL,
  `vehicle_make_id` INT NOT NULL,
  `image_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_vehicle_type`
    FOREIGN KEY (`vehicle_type_id`)
    REFERENCES `cardealer`.`vehicle_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_model`
    FOREIGN KEY (`vehicle_model_id`)
    REFERENCES `cardealer`.`vehicle_model` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_make`
    FOREIGN KEY (`vehicle_make_id`)
    REFERENCES `cardealer`.`vehicle_make` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_image`
    FOREIGN KEY (`image_id`)
    REFERENCES `cardealer`.`images_vehicle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'vehicle information';

CREATE UNIQUE INDEX `vehicleid_UNIQUE` ON `cardealer`.`vehicle` (`id` ASC);

CREATE UNIQUE INDEX `registration_UNIQUE` ON `cardealer`.`vehicle` (`registration` ASC);

CREATE INDEX `fk_vehicle_vehicle_type_idx` ON `cardealer`.`vehicle` (`vehicle_type_id` ASC);

CREATE INDEX `fk_vehicle_vehicle_model1_idx` ON `cardealer`.`vehicle` (`vehicle_model_id` ASC);

CREATE INDEX `fk_vehicle_vehicle_make1_idx` ON `cardealer`.`vehicle` (`vehicle_make_id` ASC);

CREATE INDEX `fk_vehicle_image_idx` ON `cardealer`.`vehicle` (`image_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `cardealer`.`vehicle_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `cardealer`;
INSERT INTO `cardealer`.`vehicle_type` (`id`, `name`) VALUES (1, 'car');
INSERT INTO `cardealer`.`vehicle_type` (`id`, `name`) VALUES (2, 'van');
INSERT INTO `cardealer`.`vehicle_type` (`id`, `name`) VALUES (3, 'truck');

COMMIT;


-- -----------------------------------------------------
-- Data for table `cardealer`.`vehicle_model`
-- -----------------------------------------------------
START TRANSACTION;
USE `cardealer`;
INSERT INTO `cardealer`.`vehicle_model` (`id`, `name`) VALUES (1, 'civic');
INSERT INTO `cardealer`.`vehicle_model` (`id`, `name`) VALUES (2, 'M5');
INSERT INTO `cardealer`.`vehicle_model` (`id`, `name`) VALUES (3, 'CL220');

COMMIT;


-- -----------------------------------------------------
-- Data for table `cardealer`.`vehicle_make`
-- -----------------------------------------------------
START TRANSACTION;
USE `cardealer`;
INSERT INTO `cardealer`.`vehicle_make` (`id`, `name`) VALUES (1, 'Honda');
INSERT INTO `cardealer`.`vehicle_make` (`id`, `name`) VALUES (2, 'BMW');
INSERT INTO `cardealer`.`vehicle_make` (`id`, `name`) VALUES (3, 'Mercedes');
INSERT INTO `cardealer`.`vehicle_make` (`id`, `name`) VALUES (4, 'Jaguar');

COMMIT;

