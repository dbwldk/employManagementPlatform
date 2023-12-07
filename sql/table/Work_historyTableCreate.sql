CREATE TABLE `work_history` (
  `h_key` int NOT NULL,
  `h_num` varchar(50) DEFAULT NULL,
  `h_date` datetime DEFAULT NULL,
  `h_state` int DEFAULT '0',
  `h_comment` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`h_key`),
  KEY `his_e_num_idx` (`h_num`),
  CONSTRAINT `his_e_num` FOREIGN KEY (`h_num`) REFERENCES `employ` (`e_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
