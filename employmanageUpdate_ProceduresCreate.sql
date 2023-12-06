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
    
    -- employ에서 e_num을 받아오는 부분
	DECLARE employ_cur CURSOR FOR SELECT e_num FROM employ;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- 현재 시간과 날짜를 받음
	SET current_datetime = NOW();
    -- 현재 요일을 받음
    SET current_day = DAYOFWEEK(NOW());
    -- 현재 요일이 금요일이나 일요일이라면 프로시저 종료
	IF current_day = 1 OR current_day = 7 THEN
		CLOSE employ_cur;
		LEAVE employmanageUpdate;
    END IF;
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
