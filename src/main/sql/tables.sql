set FOREIGN_KEY_CHECKS = 0; -- for drop tables during database rebuild

drop table if exists APIFILTERS;

create table if not exists APIFILTERS (
    UID integer not null auto_increment,
    APPKEY varchar(32) not null,
    APPSECRET varchar(32) not null,
    DESCRIPTION varchar(32) not null,
    primary key (UID),
    unique key (APPKEY)
)  engine=innodb;


drop table if exists AUCTIONS;

create table if not exists AUCTIONS (
    UID integer not null auto_increment,
    NAME varchar(32) not null,
    STARTDATE datetime,
    ENDDATE datetime,
    primary key (UID),
    unique key (NAME)
)  engine=innodb;


drop table if exists USERS;

create table if not exists USERS (
    UID integer not null auto_increment,
    AUCTIONUID integer not null,
    BIDDERNUMBER varchar(32) not null,
    FIRSTNAME varchar(32),
    LASTNAME varchar(32),
    PASSWORDHASH varchar(128) not null,
    primary key (UID),
    unique key (AUCTIONUID , BIDDERNUMBER),
    foreign key (AUCTIONUID)
        references AUCTIONS (UID)
        on delete cascade on update cascade
)  engine=innodb;


drop table if exists SESSIONS;

create table if not exists SESSIONS (
    UID integer not null auto_increment,
    USERUID integer not null,
    SESSIONID varchar(128),
    SESSIONDATE datetime,
    primary key (UID),
    unique key (USERUID),
    foreign key (USERUID)
        references USERS (UID)
        on delete cascade on update cascade
)  engine=innodb;


drop table if exists ITEMS;

create table if not exists ITEMS (
    UID integer not null auto_increment,
    AUCTIONUID integer not null,
    ITEMNUMBER varchar(32) not null,
    NAME varchar(128) not null,
    DESCRIPTION varchar(4096),
    CATEGORYUID integer,
    SELLER varchar(128),
    VALPRICE decimal(12 , 2 ),
    MINPRICE decimal(12 , 2 ),
    INCPRICE decimal(12 , 2 ),
    URL varchar(128) not null default '',
    MULTI boolean not null default false,
    primary key (UID),
    unique key (AUCTIONUID , ITEMNUMBER),
    foreign key (AUCTIONUID)
        references AUCTIONS (UID)
        on delete cascade on update cascade
)  engine=innodb;


drop table if exists CATEGORIES;

create table if not exists CATEGORIES (
    UID integer not null auto_increment,
    AUCTIONUID integer not null,
    NAME varchar(128) not null,
    primary key (UID),
    unique key (AUCTIONUID , NAME),
    foreign key (AUCTIONUID)
        references AUCTIONS (UID)
        on delete cascade on update cascade
)  engine=innodb;


drop table if exists BIDS;

create table if not exists BIDS (
    UID integer not null auto_increment,
    ITEMUID integer not null,
    USERUID integer not null,
    BIDPRICE decimal(12 , 2 ),
    BIDDATE datetime,
    primary key (UID),
    foreign key (ITEMUID)
        references ITEMS (UID)
        on delete cascade on update cascade,
    foreign key (USERUID)
        references USERS (UID)
        on delete cascade on update cascade
)  engine=innodb;


drop table if exists WATCHES;

create table if not exists WATCHES (
    UID integer not null auto_increment,
    ITEMUID integer not null,
    USERUID integer not null,
    primary key (UID),
    unique key (ITEMUID , USERUID),
    foreign key (ITEMUID)
        references ITEMS (UID)
        on delete cascade on update cascade,
    foreign key (USERUID)
        references USERS (UID)
        on delete cascade on update cascade
)  engine=innodb;


drop table if exists DEVICES;

create table if not exists DEVICES (
    UID integer not null auto_increment,
    USERUID integer not null,
    DEVICEID varchar(128),
    DEVICETYPE varchar(128),
    primary key (UID),
    unique key (USERUID , DEVICEID),
    foreign key (USERUID)
        references USERS (UID)
        on delete cascade on update cascade
)  engine=innodb;


drop table if exists FUNDS;

create table if not exists FUNDS (
    UID integer not null auto_increment,
    AUCTIONUID integer not null,
    USERUID integer not null,
    FUNDPRICE decimal(12 , 2 ),
    FUNDDATE datetime,
    primary key (UID),
    foreign key (AUCTIONUID)
        references AUCTIONS (UID)
        on delete cascade on update cascade
)  engine=innodb;


set FOREIGN_KEY_CHECKS = 1;