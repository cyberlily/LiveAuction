drop procedure if exists SP_GETITEM;

DELIMITER $$

create procedure SP_GETITEM (
  in p_uid integer
  )
begin
	select 
		ITEMS.UID,
		ITEMS.AUCTIONUID,
		ITEMS.ITEMNUMBER,
		ITEMS.NAME,
		ITEMS.DESCRIPTION,
		ITEMS.SELLER,
		ITEMS.VALPRICE,
		ITEMS.MINPRICE,
		ITEMS.INCPRICE,
		ITEMS.URL,
		ITEMS.MULTI,
		CATEGORIES.NAME as CATEGORY,
		USERS.BIDDERNUMBER as WINNER,
		TMPA.BIDPRICE as CURPRICE,
		TMPA.BIDCOUNT as BIDCOUNT,
		TMPB.WATCHCOUNT as WATCHCOUNT
	from ITEMS
	left join 
	  (select ITEMUID, USERUID, BIDPRICE, count(ITEMUID) as BIDCOUNT from (select ITEMUID, USERUID, BIDPRICE from BIDS order by ITEMUID desc, BIDPRICE desc, BIDDATE asc) as TMP group by ITEMUID) as TMPA on TMPA.ITEMUID = ITEMS.UID
	left join CATEGORIES on CATEGORIES.UID = ITEMS.CATEGORYUID 
	left join 
	  (select ITEMUID, count(ITEMUID) as WATCHCOUNT from (select ITEMUID from WATCHES order by ITEMUID desc) as TMP group by ITEMUID) as TMPB on TMPB.ITEMUID = ITEMS.UID
	left join USERS on USERS.UID = TMPA.USERUID 
	where ITEMS.UID = p_uid
	order by ITEMS.UID desc
	limit 1;
end$$

DELIMITER ;


drop procedure if exists SP_GETITEMS;

DELIMITER $$

create procedure SP_GETITEMS (
  in p_auctionuid integer,
  in p_categoryuid integer,
  in p_start integer,
  in p_length integer,
  in p_sort varchar(32),
  in p_dir varchar(32)
  )
begin  
  select 
	ITEMS.UID,
	ITEMS.AUCTIONUID,
	ITEMS.ITEMNUMBER,
	ITEMS.NAME,
	ITEMS.DESCRIPTION,
	ITEMS.SELLER,
	ITEMS.VALPRICE,
	ITEMS.MINPRICE,
	ITEMS.INCPRICE,
	ITEMS.URL,
	ITEMS.MULTI,
	CATEGORIES.NAME as CATEGORY,
	USERS.BIDDERNUMBER as WINNER,
	TMPA.BIDPRICE as CURPRICE,
	TMPA.BIDCOUNT as BIDCOUNT,
	TMPB.WATCHCOUNT as WATCHCOUNT
  from ITEMS
    left join 
      (select ITEMUID, USERUID, BIDPRICE, count(ITEMUID) as BIDCOUNT from (select ITEMUID, USERUID, BIDPRICE from BIDS order by ITEMUID desc, BIDPRICE desc, BIDDATE asc) as TMP group by ITEMUID) as TMPA on TMPA.ITEMUID = ITEMS.UID
    left join CATEGORIES on CATEGORIES.UID = ITEMS.CATEGORYUID 
    left join 
      (select ITEMUID, count(ITEMUID) as WATCHCOUNT from (select ITEMUID from WATCHES order by ITEMUID desc) as TMP group by ITEMUID) as TMPB on TMPB.ITEMUID = ITEMS.UID
    left join USERS on USERS.UID = TMPA.USERUID 
  where ITEMS.AUCTIONUID = p_auctionuid and (p_categoryuid < 0) or (CATEGORIES.UID = p_categoryuid)
  order by 
    case when upper(p_sort) = 'UID' and upper(p_dir) = 'DESC'then ITEMS.UID end desc,
    case when upper(p_sort) = 'UID' and upper(p_dir) = 'ASC' then ITEMS.UID end,
    case when upper(p_sort) = 'ITEMNUMBER' and upper(p_dir) = 'DESC'then ITEMS.ITEMNUMBER+0 end desc,
    case when upper(p_sort) = 'ITEMNUMBER' and upper(p_dir) = 'ASC' then ITEMS.ITEMNUMBER+0 end,
    case when upper(p_sort) = 'NAME' and upper(p_dir) = 'DESC'then ITEMS.NAME end desc,
    case when upper(p_sort) = 'NAME' and upper(p_dir) = 'ASC' then ITEMS.NAME end,
    case when upper(p_sort) = 'CURPRICE' and upper(p_dir) = 'DESC'then CURPRICE end desc,
    case when upper(p_sort) = 'CURPRICE' and upper(p_dir) = 'ASC' then CURPRICE end,
    case when upper(p_sort) = 'WINNER' and upper(p_dir) = 'DESC'then WINNER+0 end desc,
    case when upper(p_sort) = 'WINNER' and upper(p_dir) = 'ASC' then WINNER+0 end,
    case when upper(p_sort) = 'BIDCOUNT' and upper(p_dir) = 'DESC'then BIDCOUNT end desc,
    case when upper(p_sort) = 'BIDCOUNT' and upper(p_dir) = 'ASC' then BIDCOUNT end,
    case when upper(p_sort) = 'WATCHCOUNT' and upper(p_dir) = 'DESC'then WATCHCOUNT end desc,
    case when upper(p_sort) = 'WATCHCOUNT' and upper(p_dir) = 'ASC' then WATCHCOUNT end,
	case when 1 = 1 then ITEMS.UID end
  limit p_start, p_length;
end$$

DELIMITER ;


drop procedure if exists SP_GETITEMUPDATES;

DELIMITER $$

create procedure SP_GETITEMUPDATES (
  in p_auctionuid integer
  )
begin  
    select 
      ITEMS.UID,
      ITEMS.AUCTIONUID,
      ITEMS.ITEMNUMBER,
      USERS.BIDDERNUMBER as WINNER,
      TMPA.BIDPRICE as CURPRICE,
      TMPA.BIDCOUNT as BIDCOUNT,
      TMPB.WATCHCOUNT as WATCHCOUNT
    from ITEMS
    left join 
      (select ITEMUID, USERUID, BIDPRICE, count(ITEMUID) as BIDCOUNT from (select ITEMUID, USERUID, BIDPRICE from BIDS order by ITEMUID desc, BIDPRICE desc, BIDDATE asc) as TMP group by ITEMUID) as TMPA on TMPA.ITEMUID = ITEMS.UID
    left join 
      (select ITEMUID, count(ITEMUID) as WATCHCOUNT from (select ITEMUID from WATCHES order by ITEMUID desc) as TMP group by ITEMUID) as TMPB on TMPB.ITEMUID = ITEMS.UID
    left join USERS on USERS.UID = TMPA.USERUID 
    where ITEMS.AUCTIONUID = p_auctionuid
    order by ITEMS.UID asc;
end$$

DELIMITER ;


drop procedure if exists SP_GETITEMCOUNT;

DELIMITER $$

create procedure SP_GETITEMCOUNT (
  in p_auctionuid integer,
  in p_categoryuid integer -- filter
  )
begin
  if (p_categoryuid < 0) then 
    select count(UID) from ITEMS 
      where AUCTIONUID = p_auctionuid;
  else
    select count(UID) from ITEMS 
    left join CATEGORIES on CATEGORIES.UID = ITEMS.CATEGORYUID
      where AUCTIONUID = p_auctionuid
      and CATEGORIES.UID = p_categoryuid;
  end if;
end$$

DELIMITER ;


drop procedure if exists SP_EDITITEM;

DELIMITER $$
    
create procedure SP_EDITITEM (
  out p_uid integer,
  in p_auctionuid integer, -- unique key
  in p_itemnumber varchar(32), -- unique key
  in p_name varchar(128),
  in p_description varchar(4096),
  in p_category varchar(128),
  in p_seller varchar(128),
  in p_valprice decimal(12,2),
  in p_minprice decimal(12,2),
  in p_incprice decimal(12,2)
  )
begin
  declare v_categoryuid integer;

  set p_uid = -1; 
  set v_categoryuid = -1;
  
  call SP_EDITCATEGORY(v_categoryuid, p_auctionuid, p_category);
  
  insert into ITEMS (
    AUCTIONUID, 
    ITEMNUMBER, 
    NAME, 
    DESCRIPTION, 
    CATEGORYUID, 
    SELLER, 
    VALPRICE, 
    MINPRICE, 
    INCPRICE) 
  values (
    p_auctionuid, 
    p_itemnumber, 
    p_name, 
    p_description, 
    v_categoryuid, 
    p_seller, 
    p_valprice, 
    p_minprice, 
    p_incprice)
  on duplicate key update 
    UID = last_insert_id(UID),
    AUCTIONUID = p_auctionuid, 
    ITEMNUMBER = p_itemnumber, 
    NAME = p_name, 
    DESCRIPTION = p_description, 
    CATEGORYUID = v_categoryuid, 
    SELLER = p_seller, 
    VALPRICE = p_valprice, 
    MINPRICE = p_minprice, 
    INCPRICE = p_incprice;
      
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
  from ITEMS 
  where UID = p_uid;  
  
  select row_count(); 
end$$

DELIMITER ;