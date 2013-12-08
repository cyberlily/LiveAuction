-- start event
update liveauction.AUCTIONS set ENDDATE = '2013-12-23 22:00:00' where UID= 1;

-- end event
update liveauction.AUCTIONS set ENDDATE = now() where UID= 1;

-- get bids
select 
	ITEMS.UID,
	ITEMS.ITEMNUMBER,
	USERS.BIDDERNUMBER as WINNER,
	TMPA.BIDPRICE as CURPRICE
  from ITEMS
    left join 
      (select ITEMUID, USERUID, BIDPRICE, count(ITEMUID) as BIDCOUNT from (select ITEMUID, USERUID, BIDPRICE from BIDS order by ITEMUID desc, BIDPRICE desc, BIDDATE asc) as TMP group by ITEMUID) as TMPA on TMPA.ITEMUID = ITEMS.UID
    left join CATEGORIES on CATEGORIES.UID = ITEMS.CATEGORYUID 
    left join 
      (select ITEMUID, count(ITEMUID) as WATCHCOUNT from (select ITEMUID from WATCHES order by ITEMUID desc) as TMP group by ITEMUID) as TMPB on TMPB.ITEMUID = ITEMS.UID
    left join USERS on USERS.UID = TMPA.USERUID 
  where ITEMS.AUCTIONUID = 1
  order by 
    ITEMS.ITEMNUMBER;

-- get funds
select 
    FUNDS.UID,  
    USERS.BIDDERNUMBER,
    FUNDS.FUNDPRICE
  from FUNDS 
	left join USERS on USERS.UID = FUNDS.USERUID
  order by UID asc;

-- min inc price
update liveauction.ITEMS set ITEMS.INCPRICE=5.0 where UID > 0;