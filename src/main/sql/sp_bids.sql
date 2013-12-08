drop procedure if exists SP_GETBID;

DELIMITER $$

create procedure SP_GETBID (
  in p_uid integer
  )
begin
  select 
    UID, 
    ITEMUID, 
    USERUID, 
    BIDPRICE, 
    BIDDATE 
  from BIDS 
  where UID = p_uid 
  order by UID asc
  limit 1;  
end$$

DELIMITER ;


drop procedure if exists SP_GETBIDSBYITEM;

DELIMITER $$

create procedure SP_GETBIDSBYITEM (
  in p_itemuid integer
  )
begin
  select 
    UID, 
    ITEMUID, 
    USERUID, 
    BIDPRICE, 
    BIDDATE 
  from BIDS 
  where ITEMUID = p_itemuid 
  order by BIDPRICE desc;
end$$

DELIMITER ;


drop procedure if exists SP_GETBIDSBYUSER;

DELIMITER $$

create procedure SP_GETBIDSBYUSER (
  in p_useruid integer
  )
begin
  select 
    UID, 
    ITEMUID, 
    USERUID, 
    BIDPRICE, 
    BIDDATE 
  from BIDS 
  where USERUID = p_useruid 
  order by BIDPRICE desc;
end$$

DELIMITER ;


drop procedure if exists SP_GETBIDSBYUSER2;

DELIMITER $$

create procedure SP_GETBIDSBYUSER2 (
  in p_useruid integer
  )
begin
  select 
    UID, 
    ITEMUID, 
    USERUID, 
    BIDPRICE, 
    BIDDATE 
  from BIDS 
  where USERUID = p_useruid 
  group by ITEMUID
  order by BIDPRICE desc;
end$$

DELIMITER ;


drop procedure if exists SP_EDITBID;

DELIMITER $$

create procedure SP_EDITBID(
  out p_uid integer,
  in p_itemuid integer,
  in p_useruid integer,
  in p_bidprice decimal(12,2)
  )
begin
  set p_uid = -1;  

  insert into BIDS (
    ITEMUID, 
    USERUID, 
    BIDPRICE,  
    BIDDATE) 
  values (
    p_itemuid, 
    p_useruid, 
    p_bidprice, 
    now())
  on duplicate key update 
    UID = last_insert_id(UID);
    
  select last_insert_id() into p_uid;
end$$

DELIMITER ;


drop procedure if exists SP_DELETEBID;

DELIMITER $$

create procedure SP_DELETEBID (
  in p_uid integer
  )
begin
  delete 
  from BIDS 
  where UID = p_uid; 
  
  select row_count(); 
end$$

DELIMITER ;


drop procedure if exists SP_GETMAXBIDBYITEM;

DELIMITER $$

create procedure SP_GETMAXBIDBYITEM (
  in p_itemuid integer
  )
begin
    select 
    UID, 
    ITEMUID, 
    USERUID, 
    BIDPRICE, 
    BIDDATE 
  from BIDS 
  where ITEMUID = p_itemuid 
  order by BIDPRICE desc, UID desc
  limit 1; 
end$$

DELIMITER ;