drop procedure if exists SP_REGISTERDEVICE;

DELIMITER $$

create procedure SP_REGISTERDEVICE (
  out p_uid integer,
  in p_useruid integer, 
  in p_deviceid varchar(128),
  in p_devicetype varchar(128)
  )
begin  
  set p_uid = -1; 
  
  insert into DEVICES (
    USERUID,
    DEVICEID,
    DEVICETYPE) 
  values (
    p_useruid, 
    p_deviceid,
    p_devicetype)
  on duplicate key update 
    UID = last_insert_id(UID),
    USERUID = p_useruid, 
    DEVICEID = p_deviceid,
    DEVICETYPE = p_devicetype;  
    
  select last_insert_id() into p_uid;
end$$

DELIMITER ;


drop procedure if exists SP_GETDEVICES;

DELIMITER $$

create procedure SP_GETDEVICES (
  in p_start integer,
  in p_length integer,
  in p_sort varchar(32),
  in p_dir varchar(32)
  )
begin  
    select 
		UID,
		USERUID,
		DEVICEID,
		DEVICETYPE
    from DEVICES
    order by UID asc
    limit p_start, p_length;
end$$

DELIMITER ;


drop procedure if exists SP_GETDEVICECOUNT;

DELIMITER $$

create procedure SP_GETDEVICECOUNT (
  )
begin
  select 
    count(distinct UID) 
  from DEVICES 
  order by UID asc;
end$$

DELIMITER ;


drop procedure if exists SP_GETDEVICESFORUSER;

DELIMITER $$

create procedure SP_GETDEVICESFORUSER (
  in p_useruid integer
  )
begin
  select 
    UID,
    USERUID,
    DEVICEID,
    DEVICETYPE
  from DEVICES 
  where USERUID = p_useruid 
  order by UID asc;
end$$

DELIMITER ;