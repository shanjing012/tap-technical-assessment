DROP DATABASE IF EXISTS technical_assessment;

CREATE DATABASE technical_assessment;

create user 'technical_assessment_user'@'localhost' identified by 'password';

grant all on technical_assessment.* to 'technical_assessment_user'@'localhost';

flush privileges;

CREATE TABLE `household` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `household_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `annual_income` int(11) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(255) NOT NULL,
  `marital_status` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `household_id` int(11) NOT NULL,
  `spouse_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `household_id` (`household_id`),
  KEY `spouse_id` (`spouse_id`),
  CONSTRAINT `household_id` FOREIGN KEY (`household_id`) REFERENCES `household` (`id`),
  CONSTRAINT `spouse_id` FOREIGN KEY (`spouse_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;