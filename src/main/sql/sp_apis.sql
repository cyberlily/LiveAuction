drop procedure if exists SP_GETAPIFILTERS;

DELIMITER $$

create procedure SP_GETAPIFILTERS (
  )
begin
  select 
    UID, 
    APPKEY, 
    APPSECRET, 
    DESCRIPTION 
  from APIFILTERS
  order by UID asc;
end$$

DELIMITER ;


drop procedure if exists SP_EDITAPI;

DELIMITER $$
                  
create procedure SP_EDITAPI (
  out p_uid integer,
  in p_appkey varchar(32), -- unique
  in p_appsecret varchar(32),
  in p_description varchar(32)
  )
begin
  declare v_itemuid integer;

  set p_uid = -1; 
  
  insert into APIFILTERS (
    APPKEY, 
    APPSECRET, 
    DESCRIPTION) 
  values (
    p_appkey, 
    p_appsecret, 
    p_description)
  on duplicate key update 
    UID = last_insert_id(UID),
    APPKEY = p_appkey, 
    APPSECRET = p_appsecret, 
    DESCRIPTION = p_description;
  
  select last_insert_id() into p_uid;
end$$

DELIMITER ;


drop procedure if exists SP_DELETEITEM;

DELIMITER $$

create procedure SP_DELETEITEM (
  in p_uid integer
  )
begin
  delete 
  from APIFILTERS 
  where UID = p_uid; 
  
  select row_count();
end$$

DELIMITER ;

call SP_EDITAPI(@p_uid, '647dn32od2zowgj', '84f7jqu1pyp1t1e', 'Web');
select @p_uid;

call SP_EDITAPI(@p_uid, 'rvhkpii2sdgbzj8', '0p8kpv7k6mkxf3d', 'iOS (phone)');
select @p_uid;

call SP_EDITAPI(@p_uid, '1a2nh93pq0q1660', 'hp63v0bi51ly614', 'iOS (tablet)');
select @p_uid;

call SP_EDITAPI(@p_uid, 'z5e4x9d828puclw', 'vlesd5ab9fi4vz1', 'Android (phone)');
select @p_uid;

call SP_EDITAPI(@p_uid, '7522o7nxv6i8hda', 'x2rpmjeh815v8gb', 'Android (tablet)');
select @p_uid;
