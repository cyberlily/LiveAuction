drop procedure if exists SP_GETFUND;

DELIMITER $$

create procedure SP_GETFUND (
  in p_uid integer
  )
begin
  select 
    UID,  
    USERUID, 
    FUNDPRICE, 
    FUNDDATE 
  from FUNDS 
  where UID = p_uid 
  order by UID asc
  limit 1;  
end$$

DELIMITER ;


drop procedure if exists SP_GETFUNDSBYUSER;

DELIMITER $$

create procedure SP_GETFUNDSBYUSER (
  in p_useruid integer
  )
begin
  select 
    UID, 
    USERUID, 
    FUNDPRICE, 
    FUNDDATE 
  from FUNDS 
  where USERUID = p_useruid 
  order by FUNDPRICE asc;
end$$

DELIMITER ;


drop procedure if exists SP_EDITFUND;

DELIMITER $$

create procedure SP_EDITFUND(
  out p_sum decimal(12,2),
  in p_auctionuid integer,
  in p_useruid integer,
  in p_fundprice decimal(12,2)
  )
begin
  set p_sum = 0;  

  insert into FUNDS (
    AUCTIONUID, 
    USERUID, 
    FUNDPRICE, 
    FUNDDATE) 
  values (
    p_auctionuid, 
    p_useruid, 
    p_fundprice,
    now())
  on duplicate key update 
    UID = last_insert_id(UID);

  select sum(FUNDPRICE) into p_sum from FUNDS where AUCTIONUID = p_auctionuid;
end$$

DELIMITER ;


drop procedure if exists SP_DELETEFUND;

DELIMITER $$

create procedure SP_DELETEFUND (
  in p_uid integer
  )
begin
  delete 
  from FUNDS 
  where UID = p_uid; 
  
  select row_count(); 
end$$

DELIMITER ;
