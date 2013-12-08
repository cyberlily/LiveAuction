drop procedure if exists SP_GETAUCTION;

DELIMITER $$

create procedure SP_GETAUCTION (
  in p_uid integer
  )
begin
  select 
    UID, 
    NAME, 
    STARTDATE, 
    ENDDATE 
  from AUCTIONS 
  where UID = p_uid 
  order by UID asc
  limit 1; 
end$$

DELIMITER ;


drop procedure if exists SP_GETAUCTIONS;

DELIMITER $$

create procedure SP_GETAUCTIONS (
  in p_start integer,
  in p_length integer,
  in p_sort varchar(32),
  in p_dir varchar(32)
    )
begin
  select 
    UID, 
    NAME, 
    STARTDATE, 
    ENDDATE 
  from AUCTIONS 
    order by UID asc
    limit p_start, p_length;
end$$

DELIMITER ;


drop procedure if exists SP_GETAUCTIONCOUNT;

DELIMITER $$

create procedure SP_GETAUCTIONCOUNT (
  )
begin
  select 
    count(UID) 
  from AUCTIONS;
end$$

DELIMITER ;


drop procedure if exists SP_EDITAUCTION;

DELIMITER $$

create procedure SP_EDITAUCTION (
  out p_uid integer,
  in p_name varchar(32), -- unique
  in p_startdate datetime,
  in p_enddate datetime
  )
begin 
    set p_uid = -1; 

  insert into AUCTIONS (
    NAME, 
    STARTDATE, 
    ENDDATE) 
  values (
    p_name, 
    p_startdate, 
    p_enddate)
  on duplicate key update 
    UID = last_insert_id(UID),
    NAME = p_name, 
    STARTDATE = p_startdate, 
    ENDDATE = p_enddate;
  
  select last_insert_id() into p_uid; 
end$$

DELIMITER ;


drop procedure if exists SP_DELETEAUCTION;

DELIMITER $$

create procedure SP_DELETEAUCTION (
  in p_uid integer
  )
begin 
  delete 
  from AUCTIONS 
  where UID = p_uid;
  
  select row_count(); 
end$$

DELIMITER ;