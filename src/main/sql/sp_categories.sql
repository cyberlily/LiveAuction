drop procedure if exists SP_GETCATEGORY;

DELIMITER $$

create procedure SP_GETCATEGORY (
  in p_uid integer
  )
begin
  select
    UID, 
    AUCTIONUID, 
    NAME 
  from CATEGORIES 
  where UID = p_uid 
  order by UID asc
  limit 1;  
end$$

DELIMITER ;


drop procedure if exists SP_GETCATEGORIES;

DELIMITER $$

create procedure SP_GETCATEGORIES (
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
    NAME 
  from CATEGORIES
  where AUCTIONUID = p_auctionuid
  order by 
    case when upper(p_sort) = 'UID' and upper(p_dir) = 'DESC'then UID end desc,
    case when upper(p_sort) = 'UID' and upper(p_dir) = 'ASC' then UID end,
    case when upper(p_sort) = 'NAME' and upper(p_dir) = 'DESC'then NAME end desc,
    case when upper(p_sort) = 'NAME' and upper(p_dir) = 'ASC' then NAME end,
    case when 1 = 1 then UID end
  limit p_start, p_length;
end$$

DELIMITER ;


drop procedure if exists SP_GETCATEGORYCOUNT;

DELIMITER $$

create procedure SP_GETCATEGORYCOUNT (
  in p_auctionuid integer
  )
begin
  select 
    count(UID) 
  from CATEGORIES 
  where AUCTIONUID = p_auctionuid;
end$$

DELIMITER ;


drop procedure if exists SP_EDITCATEGORY;

DELIMITER $$

create procedure SP_EDITCATEGORY (
  out p_uid integer,
  in p_auctionuid integer, -- unique key
  in p_name varchar(128) -- unique key
  )
begin
  set p_uid = -1;
  
  insert into CATEGORIES (
    AUCTIONUID, 
    NAME) 
  values (
    p_auctionuid, 
    p_name) 
  on duplicate key update 
    UID = last_insert_id(UID),
    AUCTIONUID = p_auctionuid, 
    NAME = p_name;
  
  select last_insert_id() into p_uid;
end$$

DELIMITER ;


drop procedure if exists SP_DELETECATEGORY;

DELIMITER $$

create procedure SP_DELETECATEGORY (
  in p_uid integer
  )
begin
  delete 
  from CATEGORIES 
  where UID = p_uid;
  
  select row_count(); 
end$$

DELIMITER ;