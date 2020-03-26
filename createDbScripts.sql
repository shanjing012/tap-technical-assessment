DROP DATABASE IF EXISTS technical_assessment;

CREATE DATABASE technical_assessment;

drop user if exists'technical_assessment_user'@'localhost';

create user 'technical_assessment_user'@'localhost' identified by 'password';

grant all on technical_assessment.* to 'technical_assessment_user'@'localhost';

flush privileges;

SET GLOBAL time_zone = "+08:00";

CREATE TABLE technical_assessment.`household_entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `household_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE technical_assessment.`member_entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `annual_income` int(11) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(255) NOT NULL,
  `marital_status` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `household_entity_id` int(11) NOT NULL,
  `spouse_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `household_entity_id` (`household_entity_id`),
  KEY `spouse_id` (`spouse_id`),
  CONSTRAINT `household_entity_id` FOREIGN KEY (`household_entity_id`) REFERENCES `household_entity` (`id`),
  CONSTRAINT `spouse_id` FOREIGN KEY (`spouse_id`) REFERENCES `member_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;