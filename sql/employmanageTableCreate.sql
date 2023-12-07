CREATE TABLE `dept` (
  `d_num` bigint NOT NULL,
  `d_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`d_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `pos` (
  `p_num` bigint NOT NULL,
  `p_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`p_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `employ` (
  `e_num` varchar(50) NOT NULL,
  `e_name` varchar(45) DEFAULT NULL,
  `e_dept` bigint DEFAULT NULL,
  `e_pos` bigint DEFAULT NULL,
  `e_gender` int DEFAULT NULL,
  `e_phone` varchar(150) DEFAULT NULL,
  `e_addr` varchar(200) DEFAULT NULL,
  `e_email` varchar(200) DEFAULT NULL,
  `e_birth` datetime(6) DEFAULT NULL,
  `e_pic` varchar(200) DEFAULT NULL,
  `e_pswd` varchar(75) DEFAULT '0000',
  PRIMARY KEY (`e_num`),
  UNIQUE KEY `e_num_UNIQUE` (`e_num`),
  KEY `e_dept_num_idx` (`e_dept`),
  KEY `e_pos_num_idx` (`e_pos`),
  CONSTRAINT `e_dept_num` FOREIGN KEY (`e_dept`) REFERENCES `dept` (`d_num`),
  CONSTRAINT `e_pos_num` FOREIGN KEY (`e_pos`) REFERENCES `pos` (`p_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `employ_now` (
  `e_num` varchar(50) NOT NULL,
  `e_now` int DEFAULT '0',
  PRIMARY KEY (`e_num`),
  CONSTRAINT `now_e_num` FOREIGN KEY (`e_num`) REFERENCES `employ` (`e_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `employ_state` (
  `e_s_num` varchar(50) NOT NULL,
  `e_s_sdate` datetime(6) DEFAULT NULL,
  `e_s_edate` datetime(6) DEFAULT NULL,
  `e_s_state` int DEFAULT '0',
  PRIMARY KEY (`e_s_num`),
  CONSTRAINT `state_e_num` FOREIGN KEY (`e_s_num`) REFERENCES `employ` (`e_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `work_history` (
  `h_key` bigint NOT NULL,
  `h_num` varchar(50) DEFAULT NULL,
  `h_date` datetime DEFAULT NULL,
  `h_state` int DEFAULT '0',
  `h_comment` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`h_key`),
  KEY `his_e_num_idx` (`h_num`),
  CONSTRAINT `his_e_num` FOREIGN KEY (`h_num`) REFERENCES `employ` (`e_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
