CREATE SCHEMA IF NOT EXISTS `sweethome`;
USE `sweethome` ;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


CREATE TABLE IF NOT EXISTS `sweethome`.`houseinfo` (
  `houseCode` bigint NOT NULL,
  `buildYear` int DEFAULT NULL,
  `roadName` varchar(40) DEFAULT NULL,
  `roadNameBonbun` varchar(5) DEFAULT NULL,
  `roadNameBubun` varchar(5) DEFAULT NULL,
  `roadNameSeq` varchar(2) DEFAULT NULL,
  `roadNameBasementCode` varchar(1) DEFAULT NULL,
  `roadNameCode` varchar(7) DEFAULT NULL,
  `dong` varchar(40) DEFAULT NULL,
  `bonbun` varchar(4) DEFAULT NULL,
  `bubun` varchar(4) DEFAULT NULL,
  `sigunguCode` varchar(5) DEFAULT NULL,
  `eubmyundongCode` varchar(5) DEFAULT NULL,
  `dongCode` varchar(10) DEFAULT NULL,
  `landCode` varchar(1) DEFAULT NULL,
  `houseName` varchar(40) DEFAULT NULL,
  `jibun` varchar(10) DEFAULT NULL,
  `lng` varchar(30) DEFAULT NULL,
  `lat` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`houseCode`),
  UNIQUE KEY `UNIQUE` (`buildYear`,`houseName`,`jibun`,`sigunguCode`,`eubmyundongCode`) /*!80000 INVISIBLE */,
  KEY `houseinfo_dongCode_dongcode_dongCode_fk_idx` (`dongCode`) /*!80000 INVISIBLE */,
  CONSTRAINT `houseinfo_dongCode_dongcode_dongCode_fk` FOREIGN KEY (`dongCode`) REFERENCES `dongcode` (`dongCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `sweethome`.`dongcode` (
    `dongCode` VARCHAR(10) NOT NULL,
    `sidoName` VARCHAR(30) DEFAULT NULL,
    `gugunName` VARCHAR(30) DEFAULT NULL,
    `dongName` VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (`dongCode`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;


CREATE TABLE IF NOT EXISTS `sweethome`.`housedeal` (
    `no` BIGINT NOT NULL,
    `dealAmount` VARCHAR(20) DEFAULT NULL,
    `dealYear` INT DEFAULT NULL,
    `dealMonth` INT DEFAULT NULL,
    `dealDay` INT DEFAULT NULL,
    `area` VARCHAR(20) DEFAULT NULL,
    `floor` VARCHAR(4) DEFAULT NULL,
    `cancelDealType` VARCHAR(1) DEFAULT NULL,
    `houseCode` BIGINT DEFAULT NULL,
    PRIMARY KEY (`no`),
    KEY `housedeal_houseCode_houseinfo_houseCode_fk_idx` (`houseCode`),
    CONSTRAINT `housedeal_houseCode_houseinfo_houseCode_fk` FOREIGN KEY (`houseCode`)
        REFERENCES `houseinfo` (`houseCode`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sweethome`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sweethome`.`user` (
    `user_id` VARCHAR(20) NOT NULL,
    `user_pwd` VARCHAR(60) NOT NULL,
    `user_name` VARCHAR(20) NOT NULL,
    `user_address` VARCHAR(50) NOT NULL,
    `user_email` VARCHAR(50) NOT NULL,
    `type` VARCHAR(15) NOT NULL DEFAULT 'general',
    `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `token` VARCHAR(1000) NULL DEFAULT NULL,
    `origin_profile_img_name` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`user_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sweethome`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sweethome`.`board` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(2000) NOT NULL DEFAULT '',
  `hit` INT NULL DEFAULT '0',
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  INDEX `fk_board_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sweethome`.`user` (`user_id`))
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `sweethome`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sweethome`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `article_no` INT NOT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `content` VARCHAR(500) NOT NULL DEFAULT '',
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_comment_board1_idx` (`article_no` ASC) VISIBLE,
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_board1`
    FOREIGN KEY (`article_no`)
    REFERENCES `sweethome`.`board` (`article_no`),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sweethome`.`user` (`user_id`))
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `sweethome`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sweethome`.`notice` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(2000) NOT NULL DEFAULT '',
  `hit` INT NULL DEFAULT '0',
  `reg_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  INDEX `fk_notice_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_notice_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sweethome`.`user` (`user_id`))
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

- -----------------------------------------------------
-- Table `sweethome`.`favoriteRegion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sweethome`.`favoriteRegion` (
  `user_id` VARCHAR(20) NOT NULL,
  `dongCode` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`user_id`, `dongCode`),
  INDEX `fk_favoriteRegion_dongcode1_idx` (`dongCode` ASC) VISIBLE,
  CONSTRAINT `fk_favoriteRegion_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sweethome`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favoriteRegion_dongcode1`
    FOREIGN KEY (`dongCode`)
    REFERENCES `sweethome`.`dongcode` (`dongCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `sweethome`.`favoriteHousedeal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sweethome`.`favoriteHousedeal` (
  `housedeal_no` BIGINT NOT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  INDEX `fk_favoriteHousedeal_housedeal_idx` (`housedeal_no` ASC) VISIBLE,
  INDEX `fk_favoriteHousedeal_user1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`housedeal_no`, `user_id`),
  CONSTRAINT `fk_favoriteHousedeal_housedeal`
    FOREIGN KEY (`housedeal_no`)
    REFERENCES `sweethome`.`housedeal` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favoriteHousedeal_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sweethome`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--INSERT INTO user(`user_id`, `user_pwd`, `user_name`, `user_address`, `user_email`, `type`) VALUES('admin', '1234', '삼싸피', '서울특별시 강남구 역삼역 1번 출구', 'ssafy@ssafy.com', 'admin');
--insert into user(user_id, user_pwd, user_name, user_address, user_email) values("ssafyKim", "1234", "김싸피", "서울특별시 강남구 언주로 508 서울상록빌딩", "ssafyKim@ssafy.com");
--insert into board(user_id, `subject`, content) VALUES('ssafyKim', "가입 인사드립니다. 김싸피입니다.", "만반잘부ㅎㅎ");
--INSERT INTO notice(`user_id`, `subject`, `content`) VALUES('admin', '공지사항 테스트입니다.', '공지사항 테스트입니다.');
