CREATE TABLE `employ_now` (
  `e_num` varchar(50) NOT NULL,
  `e_now` int DEFAULT '0',
  PRIMARY KEY (`e_num`),
  CONSTRAINT `now_e_num` FOREIGN KEY (`e_num`) REFERENCES `employ` (`e_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
