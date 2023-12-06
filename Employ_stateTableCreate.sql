CREATE TABLE `employ_state` (
  `e_s_num` varchar(50) NOT NULL,
  `e_s_sdate` date DEFAULT NULL,
  `e_s_edate` date DEFAULT NULL,
  `e_s_state` int DEFAULT '0',
  PRIMARY KEY (`e_s_num`),
  CONSTRAINT `state_e_num` FOREIGN KEY (`e_s_num`) REFERENCES `employ` (`e_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
