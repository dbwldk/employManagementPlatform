CREATE EVENT employmanageScheduler
    ON SCHEDULE every 1 day
    STARTS '2020-03-01 00:00:30'
    COMMENT '매일 1회 0시 0분 30초에 실행하는 프로시저'
    DO
      call employmanageUpdate();