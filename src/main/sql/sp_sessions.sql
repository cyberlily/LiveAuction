drop procedure if exists SP_SIGNIN;

DELIMITER $$

create procedure SP_SIGNIN (
  in p_useruid integer, 
  out p_sessionid varchar(256)
  )
begin
  set p_sessionid = uuid();
  
  insert into SESSIONS (
    USERUID, 
    SESSIONID, 
    SESSIONDATE) 
  values (
    p_useruid, 
    p_sessionid, 
    now())
  on duplicate key update 
    USERUID = p_useruid, 
    SESSIONID = p_sessionid, 
    SESSIONDATE = now();  
end$$

DELIMITER ;


drop procedure if exists SP_SIGNOUT;

DELIMITER $$

create procedure SP_SIGNOUT (
  in p_useruid integer
  )
begin
  update SESSIONS set 
    SESSIONID = null, 
    SESSIONDATE = null 
  where USERUID = p_useruid;   
end$$

DELIMITER ;


drop procedure if exists SP_GETUSERBYSESSIONID;

DELIMITER $$

create procedure SP_GETUSERBYSESSIONID (
  in p_sessionid varchar(128)
  )
begin
  select 
	USERS.UID, 
	USERS.AUCTIONUID, 
	USERS.BIDDERNUMBER, 
	USERS.FIRSTNAME, 
	USERS.LASTNAME, 
	USERS.PASSWORDHASH from USERS 
  left join SESSIONS on SESSIONS.USERUID = USERS.UID 
    where SESSIONS.SESSIONID = p_sessionid
  order by USERS.UID asc
  limit 1;
end$$

DELIMITER ;
