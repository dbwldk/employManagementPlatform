CREATE TABLE `employ` (
  `e_num` varchar(50) NOT NULL,
  `e_name` varchar(45) DEFAULT NULL,
  `e_dept` int unsigned DEFAULT NULL,
  `e_pos` int unsigned DEFAULT NULL,
  `e_gender` int DEFAULT NULL,
  `e_phone` varchar(150) DEFAULT NULL,
  `e_addr` varchar(200) DEFAULT NULL,
  `e_email` varchar(200) DEFAULT NULL,
  `e_birth` date DEFAULT NULL,
  `e_pic` varchar(200) DEFAULT NULL,
  `e_pswd` varchar(75) DEFAULT '0000',
  PRIMARY KEY (`e_num`),
  UNIQUE KEY `e_num_UNIQUE` (`e_num`),
  KEY `d_num_idx` (`e_dept`),
  KEY `p_num_idx` (`e_pos`),
  CONSTRAINT `d_num` FOREIGN KEY (`e_dept`) REFERENCES `dept` (`d_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `p_num` FOREIGN KEY (`e_pos`) REFERENCES `pos` (`p_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
