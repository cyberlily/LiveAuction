drop procedure if exists SP_GETUSER;

DELIMITER $$

create procedure SP_GETUSER (
  in p_uid integer
  )
begin
  select 
    UID, 
    AUCTIONUID, 
    BIDDERNUMBER, 
    FIRSTNAME, 
    LASTNAME, 
    PASSWORDHASH
  from USERS 
  where UID = p_uid 
  order by UID asc
  limit 1;  
end$$
    
DELIMITER ;


drop procedure if exists SP_GETUSERBYBIDDERNUMBER;

DELIMITER $$

create procedure SP_GETUSERBYBIDDERNUMBER (
  in p_biddernumber varchar(32)
  )
begin
  select 
    UID, 
    AUCTIONUID, 
    BIDDERNUMBER, 
    FIRSTNAME, 
    LASTNAME, 
    PASSWORDHASH
  from USERS 
  where BIDDERNUMBER = p_biddernumber
  order by UID asc
  limit 1; 
end$$

DELIMITER ;


drop procedure if exists SP_GETUSERS;

DELIMITER $$

create procedure SP_GETUSERS (
  in p_auctionuid integer,
  in p_start integer,
  in p_length integer,
  in p_sort varchar(32),
  in p_dir varchar(32)
  )
begin
  select 
	UID, 
	AUCTIONUID, 
	BIDDERNUMBER, 
	FIRSTNAME, 
	LASTNAME, 
	PASSWORDHASH
  from USERS 
  where AUCTIONUID = p_auctionuid
  order by 
    case when upper(p_sort) = 'UID' and upper(p_dir) = 'DESC'then UID end desc,
    case when upper(p_sort) = 'UID' and upper(p_dir) = 'ASC' then UID end,
    case when upper(p_sort) = 'BIDDERNUMBER' and upper(p_dir) = 'DESC'then BIDDERNUMBER+0 end desc,
    case when upper(p_sort) = 'BIDDERNUMBER' and upper(p_dir) = 'ASC' then BIDDERNUMBER+0 end,
    case when upper(p_sort) = 'FIRSTNAME' and upper(p_dir) = 'DESC'then FIRSTNAME end desc,
    case when upper(p_sort) = 'FIRSTNAME' and upper(p_dir) = 'ASC' then FIRSTNAME end,
    case when upper(p_sort) = 'LASTNAME' and upper(p_dir) = 'DESC'then LASTNAME end desc,
    case when upper(p_sort) = 'LASTNAME' and upper(p_dir) = 'ASC' then LASTNAME end,
    case when 1 = 1 then UID end
  limit p_start, p_length;
end$$

DELIMITER ;


drop procedure if exists SP_GETUSERCOUNT;

DELIMITER $$

create procedure SP_GETUSERCOUNT (
  in p_auctionuid integer
  )
begin
    select count(UID) from USERS 
      where AUCTIONUID = p_auctionuid;
end$$

DELIMITER ;


drop procedure if exists SP_EDITUSER;

DELIMITER $$

create procedure SP_EDITUSER (
  out p_uid integer,
  in p_auctionuid integer, -- unique key
  in p_biddernumber varchar(32), -- unique key
  in p_firstname varchar(32),
  in p_lastname varchar(32),
  in p_passwordhash varchar(128)
  )
begin
  set p_uid = -1;

  insert into USERS (
    AUCTIONUID, 
    BIDDERNUMBER, 
    FIRSTNAME, 
    LASTNAME, 
    PASSWORDHASH) 
  values (
    p_auctionuid, 
    p_biddernumber, 
    p_firstname, 
    p_lastname, 
    p_passwordhash)
  on duplicate key update 
    UID = last_insert_id(UID),
    AUCTIONUID = p_auctionuid, 
    BIDDERNUMBER = p_biddernumber, 
    FIRSTNAME = p_firstname, 
    LASTNAME = p_lastname, 
    PASSWORDHASH = p_passwordhash;

  select last_insert_id() into p_uid;
end$$

DELIMITER ;


drop procedure if exists SP_DELETEUSER;

DELIMITER $$

create procedure SP_DELETEUSER (
  in p_uid integer
  )
begin
  delete 
  from USERS 
  where UID = p_uid; 
  
  select row_count(); 
end$$

DELIMITER ;




