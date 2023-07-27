CREATE DATABASE `dnt`;

-- dnt.`domain` definition
CREATE TABLE `domain` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `abbreviation` varchar(255) DEFAULT NULL,
                          `code` varchar(255) NOT NULL,
                          `data_type` varchar(255) DEFAULT NULL,
                          `description` varchar(255) DEFAULT NULL,
                          `domain` varchar(255) NOT NULL,
                          `hash` varchar(255) NOT NULL,
                          `lang` varchar(255) NOT NULL,
                          `project` varchar(255) DEFAULT NULL,
                          `used_count` bigint(20) DEFAULT 0,
                          `insert_date` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
                          `update_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `UK_nu3blcxheu25gshd0qel25w90` (`hash`)
) ENGINE=InnoDB AUTO_INCREMENT=2042 DEFAULT CHARSET=utf8mb4;

-- dnt.`user` definition
CREATE TABLE `user` (
                        `seq` bigint(20) NOT NULL AUTO_INCREMENT,
                        `insert_date` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
                        `update_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일',
                        `client_id` varchar(255) DEFAULT NULL COMMENT '랜덤키값',
                        `email` varchar(100) DEFAULT NULL COMMENT '이메일',
                        `name` varchar(100) DEFAULT NULL COMMENT '이름',
                        `password` varchar(255) DEFAULT NULL COMMENT '비밀번호',
                        `serurity_key` varchar(255) DEFAULT NULL COMMENT '랜덤키값',
                        PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4;