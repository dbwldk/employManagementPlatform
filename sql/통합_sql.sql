-- db create
CREATE database `employmanage` DEFAULT CHARACTER SET utf8mb4 ;
USE `employmanage`;

-- table create
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


-- view create
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `employ_view` 
AS select `e`.`e_num` 
AS `e_num`,`es`.`e_s_state` 
AS `e_s_state`,`e`.`e_dept` 
AS `e_dept`,`e`.`e_pos` 
AS `e_pos`,`e`.`e_name` 
AS `e_name` 
from (`employ` `e` join `employ_state` `es` on((`e`.`e_num` = `es`.`e_s_num`)));


-- procedure create
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `employmanageUpdate`()
employmanageUpdate:BEGIN
    -- 현재 시간과 날짜를 저장하기 위한 변수
    DECLARE current_datetime DATETIME;
    -- 현재 요일을 받기 위한 변수
    DECLARE current_day INT;
	-- 커서에 다음 데이터가 없을때 반복문을 종료 시키기 위한 변수
    DECLARE done INT DEFAULT FALSE;
	-- employ에서 받아온 사원번호를 받는 변수
	DECLARE em_num VARCHAR(50);
    -- work_history의 키 최대값을 받기 위한 변수
    DECLARE max_key INT;
    -- 현재 재직 중인 사원인지 확인하기 위한 변수
    DECLARE e_tenure INT;
    
    DECLARE e_daily_off INT;
    
    -- employ에서 e_num을 받아오는 부분
	DECLARE employ_cur CURSOR FOR SELECT e_num FROM employ;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- 현재 시간과 날짜를 받음
	SET current_datetime = NOW();
    -- 현재 요일을 받음
    SET current_day = DAYOFWEEK(NOW());
    -- 현재 요일이 토요일이나 일요일이라면 프로시저 종료
	-- IF current_day = 1 OR current_day = 7 THEN
	-- 	LEAVE employmanageUpdate;
    -- END IF;
    -- work_history에서 키 최대값을 받는 부분
    SELECT MAX(h_key) INTO max_key FROM work_history;
    -- 키값이 널이면 키값을 0으로 설정
	IF max_key IS NULL THEN
		set max_key = 0;
	ELSE 
		set max_key = max_key + 1;
	END IF;

    OPEN employ_cur;
    
    read_loop: LOOP
        FETCH employ_cur INTO em_num;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SELECT e_s_state INTO e_tenure FROM employ_state WHERE e_s_num = em_num;
        
        IF e_tenure = 0 THEN
        
			SELECT e_now INTO e_daily_off FROM employ_now WHERE e_num = em_num;
            IF e_daily_off = 1 THEN
				INSERT INTO work_history (h_key, h_num, h_date, h_state) VALUES (max_key, em_num, current_datetime, 2);
                set max_key = max_key + 1;
            END IF;
			-- work_history 업데이트
			-- work_history에 오늘의 출근 기록 결근 상태로 넣음
			INSERT INTO work_history (h_key, h_num, h_date, h_state) VALUES (max_key, em_num, current_datetime, 0);
			-- 키값 증가
			set max_key = max_key + 1;
			
			-- employ_now 업데이트
			-- em_num이 같은 칼럼을 찾아 비출근 상태(0)으로 변경
			UPDATE employ_now
			SET e_now = 0
			WHERE e_num = em_num;
        END IF;
    END LOOP;
	
    CLOSE employ_cur;
END$$
DELIMITER ;


-- scheduler create
CREATE EVENT employmanageScheduler
    ON SCHEDULE every 1 day
    STARTS '2020-03-01 00:00:30'
    COMMENT '매일 1회 0시 0분 30초에 실행하는 프로시저'
    DO
      call employmanageUpdate();


-- sample data insert

-- 'dept' data insert
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (1, '인사부');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (2, '인사1팀');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (3, '인사2팀');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (11, '영업부');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (12, '영업1팀');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (13, '영업2팀');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (14, '영업3팀');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (21, '개발부');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (22, '개발1팀');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (23, '개발2팀');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (31, '총무부');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (41, '기획부');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (51, '회계부');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (61, '마케팅부');
INSERT INTO `dept`(`d_num`, `d_name`) VALUES (71, '해외사업부');

-- 'pos' data insert
INSERT INTO `pos`(`p_num`, `p_name`) VALUES (1, 'Admin');
INSERT INTO `pos`(`p_num`, `p_name`) VALUES (2, '부장');
INSERT INTO `pos`(`p_num`, `p_name`) VALUES (3, '차장');
INSERT INTO `pos`(`p_num`, `p_name`) VALUES (4, '과장');
INSERT INTO `pos`(`p_num`, `p_name`) VALUES (5, '대리');
INSERT INTO `pos`(`p_num`, `p_name`) VALUES (6, '주임');
INSERT INTO `pos`(`p_num`, `p_name`) VALUES (7, '사원');

-- 'employ' data insert
INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('A0000001', '이성계', '1', '2', '0', '11122223333', '서울특별시 종로구', 'abcd@seoul.cas', '1990-05-04 00:00:00.000000', '/imgs/img/normal.png', '0000');

INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('A0000002', '정몽주', '11', '2', '1', '22244448888', '서울특별시 강남구', '2222@seoul.cas', '1993-04-08 00:00:00.000000', '/imgs/img/normal.png', '0000');

INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('A0000003', '이순신', '11', '3', '0', '33311115555', '충청북도 청주시', '1597@seoul.cas', '2000-05-08 00:00:00.000000', '/imgs/img/normal.png', '0000');

INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('A0000004', '신사임당', '31', '2', '0', '66655558888', '서울특별시 강서구', '5555@seoul.cas', '2001-08-04 00:00:00.000000', '/imgs/img/normal.png', '0000');

INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('A0000005', '강감찬', '51', '2', '0', '22233339999', '서울특별시 양천구', '1234@seoul.cas', '1999-05-07 00:00:00.000000', '/imgs/img/cat.png', '0000');

INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('A0000006', '김구', '51', '2', '0', '88811115555', '전라북도 전주시', '6751@seoul.cas', '2000-08-20 00:00:00.000000', '/imgs/img/normal.png', '0000');

INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('A0000007', '홍길동', '1', '3', '1', '88800008888', '서귀포시', '4485@seoul.cas', '1998-12-03 00:00:00.000000', '/imgs/img/normal.png', '0000');

INSERT INTO `employ`(`e_num`, `e_name`, `e_dept`, `e_pos`, `e_gender`, `e_phone`, `e_addr`, `e_email`, `e_birth`, `e_pic`, `e_pswd`) 
VALUES ('Admin', 'Admin', '1', '1', '2', '00000000000', '00000000000', '0000@soeuk.cas', '1111-11-11 00:00:00.000000', '/imgs/img/normal.png', '1234');


-- 'employ_now' data insert
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('A0000001', 2);
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('A0000002', 2);
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('A0000003', 0);
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('A0000004', 0);
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('A0000005', 0);
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('A0000006', 0);
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('A0000007', 0);
INSERT INTO `employ_now`(`e_num`, `e_now`) VALUES ('Admin', 2);


-- 'employ_state' data insert
INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`, `e_s_edate`, `e_s_state`)
VALUES ('A0000001', '2023-12-10 14:35:37.737000', '2023-12-10 17:57:10.789000', 1);

INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`)
VALUES ('A0000002', '2023-12-10 14:37:21.595000');

INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`)
VALUES ('A0000003', '2023-12-10 14:39:41.164000');

INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`)
VALUES ('A0000004', '2023-12-10 14:40:28.766000');

INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`)
VALUES ('A0000005', '2023-12-10 14:43:07.508000');

INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`)
VALUES ('A0000006', '2023-12-10 14:45:25.237000');

INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`)
VALUES ('A0000007', '2023-12-10 14:44:17.253000');

INSERT INTO `employ_state`(`e_s_num`, `e_s_sdate`)
VALUES ('Admin', '1112-11-11 00:00:00.000000');


-- 'work_history' data insert
INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (2, 'Admin', '2023-12-10 17:35:39', 1);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (3, 'A0000001', '2023-12-10 19:05:03', 1);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (4, 'A0000002', '2023-12-10 19:07:12', 1);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (5, 'A0000003', '2023-12-10 14:39:41', 0);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (6, 'A0000004', '2023-12-10 14:40:29', 0);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (7, 'A0000005', '2023-12-10 18:33:07', 1);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (8, 'A0000001', '2023-12-10 19:05:25', 2);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (9, 'A0000002', '2023-12-10 19:07:19', 2);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (10, 'A0000004', '2023-12-08 14:40:29', 1);

INSERT INTO `work_history`(`h_key`, `h_num`, `h_date`, `h_state`)
VALUES (11, 'A0000006', '2023-12-10 15:50:25', 0);