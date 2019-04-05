-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema spm
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `spm` ;

-- -----------------------------------------------------
-- Schema spm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `spm` DEFAULT CHARACTER SET utf8 ;
USE `spm` ;

-- -----------------------------------------------------
-- Table `spm`.`user_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`user_info` ;

CREATE TABLE IF NOT EXISTS `spm`.`user_info` (
                                                 `user_id`      INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                 `username`     CHAR(10)     NOT NULL,
                                                 `password`     VARCHAR(256) NOT NULL,
                                                 `time_created` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                 `role`         VARCHAR(32)  NOT NULL,
                                                 `real_name`    VARCHAR(64)  NOT NULL,
                                                 `email`        VARCHAR(45)  NOT NULL,
                                                 `phone`        VARCHAR(45)  NOT NULL,
                                                 PRIMARY KEY (`user_id`),
                                                 UNIQUE INDEX `id_UNIQUE` (`user_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `spm`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`student` ;

CREATE TABLE IF NOT EXISTS `spm`.`student` (
  `user_id` INT UNSIGNED NOT NULL,
  `class` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(128) NULL,
  `college` VARCHAR(128) NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_student_userinfo`
    FOREIGN KEY (`user_id`)
    REFERENCES `spm`.`user_info` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`teacher` ;

CREATE TABLE IF NOT EXISTS `spm`.`teacher` (
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_teacher_userinfo`
    FOREIGN KEY (`user_id`)
    REFERENCES `spm`.`user_info` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`course` ;

CREATE TABLE IF NOT EXISTS `spm`.`course` (
  `course_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `teacher_user_id` INT UNSIGNED NOT NULL,
  `course_name` VARCHAR(128) NOT NULL,
  `description` TEXT NOT NULL,
  `period` TINYINT NOT NULL,
  `start_date` DATE NOT NULL,
  `finish_date` DATE NOT NULL,
  PRIMARY KEY (`course_id`),
  INDEX `fk_course_teacher_idx` (`teacher_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_teacher`
    FOREIGN KEY (`teacher_user_id`)
    REFERENCES `spm`.`teacher` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`experiment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`experiment` ;

CREATE TABLE IF NOT EXISTS `spm`.`experiment` (
  `experiment_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `experiment_name` VARCHAR(256) NOT NULL,
  `description` TEXT NULL,
  `start_date` DATE NOT NULL,
  `finish_date` DATE NOT NULL,
  `course_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`experiment_id`),
  INDEX `fk_experiment_course_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_experiment_course`
    FOREIGN KEY (`course_id`)
    REFERENCES `spm`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`selected_course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`selected_course` ;

CREATE TABLE IF NOT EXISTS `spm`.`selected_course` (
  `student_user_id` INT UNSIGNED NOT NULL,
  `course_course_id` INT UNSIGNED NOT NULL,
  `time_approved` DATETIME NOT NULL,
  `avg_online_score` DECIMAL(5,1) NULL,
  `mid_score` DECIMAL(5,1) NULL,
  `final_score` DECIMAL(5,1) NULL,
  `total_score` DECIMAL(5,1) NULL,
  PRIMARY KEY (`student_user_id`, `course_course_id`),
  INDEX `fk_student_has_course_course2_idx` (`course_course_id` ASC) VISIBLE,
  INDEX `fk_student_has_course_student2_idx` (`student_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_has_course_student2`
    FOREIGN KEY (`student_user_id`)
    REFERENCES `spm`.`student` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_has_course_course2`
    FOREIGN KEY (`course_course_id`)
    REFERENCES `spm`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`chapter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`chapter` ;

CREATE TABLE IF NOT EXISTS `spm`.`chapter` (
  `chapter_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `course_id` INT UNSIGNED NOT NULL,
  `sequence` TINYINT UNSIGNED NOT NULL,
  `chapter_name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`chapter_id`),
  INDEX `fk_chapter_couser_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_chapter_couser`
    FOREIGN KEY (`course_id`)
    REFERENCES `spm`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`file_source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`file_source` ;

CREATE TABLE IF NOT EXISTS `spm`.`file_source` (
  `file_source_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `filename` VARCHAR(256) NOT NULL,
  `identifier` VARCHAR(256) NOT NULL,
  `file_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`file_source_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`section`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`section` ;

CREATE TABLE IF NOT EXISTS `spm`.`section` (
  `section_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `chapter_id` INT UNSIGNED NOT NULL,
  `sequence` TINYINT NOT NULL,
  `section_name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`section_id`),
  INDEX `fk_section_chapter_idx` (`chapter_id` ASC) VISIBLE,
  CONSTRAINT `fk_section_chapter`
    FOREIGN KEY (`chapter_id`)
    REFERENCES `spm`.`chapter` (`chapter_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`experiment_file`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`experiment_file` ;

CREATE TABLE IF NOT EXISTS `spm`.`experiment_file` (
  `experiment_id` INT UNSIGNED NOT NULL,
  `file_source_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`experiment_id`, `file_source_id`),
  INDEX `fk_experiment_has_filesource_filesource1_idx` (`file_source_id` ASC) VISIBLE,
  INDEX `fk_experiment_has_filesource_experiment1_idx` (`experiment_id` ASC) VISIBLE,
  CONSTRAINT `fk_experiment_has_filesource_experiment1`
    FOREIGN KEY (`experiment_id`)
    REFERENCES `spm`.`experiment` (`experiment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_experiment_has_filesource_filesource1`
    FOREIGN KEY (`file_source_id`)
    REFERENCES `spm`.`file_source` (`file_source_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`section_file`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`section_file` ;

CREATE TABLE IF NOT EXISTS `spm`.`section_file` (
  `section_id` INT UNSIGNED NOT NULL,
  `file_source_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`section_id`, `file_source_id`),
  INDEX `fk_section_has_filesource_filesource1_idx` (`file_source_id` ASC) VISIBLE,
  INDEX `fk_section_has_filesource_section1_idx` (`section_id` ASC) VISIBLE,
  CONSTRAINT `fk_section_has_filesource_section1`
    FOREIGN KEY (`section_id`)
    REFERENCES `spm`.`section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_section_has_filesource_filesource1`
    FOREIGN KEY (`file_source_id`)
    REFERENCES `spm`.`file_source` (`file_source_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`student_course_experiment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`student_course_experiment` ;

CREATE TABLE IF NOT EXISTS `spm`.`student_course_experiment` (
  `student_id` INT UNSIGNED NOT NULL,
  `course_id` INT UNSIGNED NOT NULL,
  `experiment_id` INT UNSIGNED NOT NULL,
  `doc_file` INT UNSIGNED NOT NULL,
  `video_file` INT UNSIGNED NOT NULL,
  `score` INT UNSIGNED NULL,
  PRIMARY KEY (`student_id`, `course_id`, `experiment_id`),
  INDEX `fk_student_course_approve_has_experiment1_experiment1_idx` (`experiment_id` ASC) VISIBLE,
  INDEX `fk_student_course_approve_has_experiment1_student_course_ap_idx` (`student_id` ASC, `course_id` ASC) VISIBLE,
  INDEX `fk_stu_course_exp_file_idx` (`doc_file` ASC) VISIBLE,
  INDEX `fk_stu_course_exp_file2_idx` (`video_file` ASC) VISIBLE,
  CONSTRAINT `fk_student_course_approve_has_experiment1_student_course_appr1`
    FOREIGN KEY (`student_id` , `course_id`)
    REFERENCES `spm`.`selected_course` (`student_user_id` , `course_course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_course_approve_has_experiment1_experiment1`
    FOREIGN KEY (`experiment_id`)
    REFERENCES `spm`.`experiment` (`experiment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stu_course_exp_file`
    FOREIGN KEY (`doc_file`)
    REFERENCES `spm`.`file_source` (`file_source_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stu_course_exp_file2`
    FOREIGN KEY (`video_file`)
    REFERENCES `spm`.`file_source` (`file_source_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`exam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`exam` ;

CREATE TABLE IF NOT EXISTS `spm`.`exam` (
  `exam_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `chapter_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`exam_id`),
  INDEX `fk_exam_chapter_idx` (`chapter_id` ASC) VISIBLE,
  CONSTRAINT `fk_exam_chapter`
    FOREIGN KEY (`chapter_id`)
    REFERENCES `spm`.`chapter` (`chapter_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`question_option`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`question_option` ;

CREATE TABLE IF NOT EXISTS `spm`.`question_option` (
  `question_option_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_option_detail` VARCHAR(256) NOT NULL,
  `question_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`question_option_id`),
  INDEX `fk_question_option_idx` (`question_id` ASC) VISIBLE,
  CONSTRAINT `fk_question_option`
    FOREIGN KEY (`question_id`)
    REFERENCES `spm`.`question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`question` ;

CREATE TABLE IF NOT EXISTS `spm`.`question` (
  `question_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `exam` INT UNSIGNED NOT NULL,
  `answer` INT UNSIGNED NOT NULL,
  `detail` TEXT NOT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `fk_exam_question_idx` (`exam` ASC) VISIBLE,
  INDEX `fk_question_answer_idx` (`answer` ASC) VISIBLE,
  UNIQUE INDEX `answer_UNIQUE` (`answer` ASC) VISIBLE,
  CONSTRAINT `fk_exam_question`
    FOREIGN KEY (`exam`)
    REFERENCES `spm`.`exam` (`exam_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_answer`
    FOREIGN KEY (`answer`)
    REFERENCES `spm`.`question_option` (`question_option_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`notice` ;

CREATE TABLE IF NOT EXISTS `spm`.`notice` (
  `notice_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `author` INT UNSIGNED NOT NULL,
  `title` VARCHAR(128) NOT NULL,
  `detail` TEXT NOT NULL,
  `time_created` TIMESTAMP NOT NULL,
  PRIMARY KEY (`notice_id`),
  INDEX `fk_notiece_author_idx` (`author` ASC) VISIBLE,
  CONSTRAINT `fk_notiece_author`
    FOREIGN KEY (`author`)
    REFERENCES `spm`.`teacher` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`message` ;

CREATE TABLE IF NOT EXISTS `spm`.`message` (
  `message_id` INT UNSIGNED NOT NULL,
  `author` INT UNSIGNED NOT NULL,
  `content` TEXT NOT NULL,
  `time_created` DATETIME NOT NULL,
  `reply_to` INT UNSIGNED NULL,
  PRIMARY KEY (`message_id`),
  INDEX `fk_message_reply_idx` (`reply_to` ASC) VISIBLE,
  INDEX `fk_message_author_idx` (`author` ASC) VISIBLE,
  CONSTRAINT `fk_message_reply`
    FOREIGN KEY (`reply_to`)
    REFERENCES `spm`.`message` (`message_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_author`
    FOREIGN KEY (`author`)
    REFERENCES `spm`.`user_info` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`application`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`application` ;

CREATE TABLE IF NOT EXISTS `spm`.`application` (
  `student_user_id` INT UNSIGNED NOT NULL,
  `course_id` INT UNSIGNED NOT NULL,
  `time_created` DATETIME NOT NULL,
  `state` TINYINT NOT NULL DEFAULT 0,
  `comment` VARCHAR(256) NULL,
  PRIMARY KEY (`student_user_id`, `course_id`),
  INDEX `fk_student_has_course_course1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_student_has_course_student1_idx` (`student_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_has_course_student1`
    FOREIGN KEY (`student_user_id`)
    REFERENCES `spm`.`student` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_has_course_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `spm`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `spm`.`token_secret`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spm`.`token_secret` ;

CREATE TABLE IF NOT EXISTS `spm`.`token_secret` (
  `token_secret_id` INT UNSIGNED NOT NULL,
  `secret` VARCHAR(256) NULL,
  PRIMARY KEY (`token_secret_id`),
  UNIQUE INDEX `token_secret_id_UNIQUE` (`token_secret_id` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
