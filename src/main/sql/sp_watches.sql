drop procedure if exists SP_GETWATCH;

DELIMITER $$

create procedure SP_GETWATCH (
  in p_uid integer
  )
begin
  select 
    UID, 
    ITEMUID, 
    USERUID
  from WATCHES 
  where UID = p_uid 
  order by UID asc
  limit 1;  
end$$

DELIMITER ;


drop procedure if exists SP_GETWATCHESBYITEM;

DELIMITER $$

create procedure SP_GETWATCHESBYITEM(
  in p_itemuid integer
  )
begin
  select 
    UID, 
    ITEMUID, 
    USERUID
  from WATCHES 
  where ITEMUID = p_itemuid 
  order by ITEMUID asc;  
end$$

DELIMITER ;


drop procedure if exists SP_GETWATCHESBYUSER;

DELIMITER $$

create procedure SP_GETWATCHESBYUSER(
  in p_useruid integer
  )
begin
  select 
    UID, 
    ITEMUID, 
    USERUID 
  from WATCHES 
  where USERUID = p_useruid 
  order by ITEMUID asc;  
end$$

DELIMITER ;


drop procedure if exists SP_EDITWATCH;

DELIMITER $$

create procedure SP_EDITWATCH(
  out p_uid integer,
  in p_itemuid integer,
  in p_useruid integer
  )
begin
  set p_uid = -1;  

  insert into WATCHES (
    ITEMUID, 
    USERUID) 
  values (
    p_itemuid, 
    p_useruid)
  on duplicate key update 
    UID = last_insert_id(UID), 
    ITEMUID = p_itemuid, 
    USERUID = p_useruid;
      
  select last_insert_id() into p_uid;
end$$

DELIMITER ;


drop procedure if exists SP_DELETEWATCH;

DELIMITER $$

create procedure SP_DELETEWATCH (
  in p_uid integer
  )
begin
  delete 
  from WATCHES 
  where UID = p_uid;  
  
  select row_count(); 
end$$

DELIMITER ;
