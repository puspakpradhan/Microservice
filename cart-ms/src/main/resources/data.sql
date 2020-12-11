
--create table CART_TBL(CART_ID integer not null AUTO_INCREMENT, 
--                      PROD_ID integer not null,
--                      USER_ID integer not null,
--                      USER_NAME varchar(20),
--                      QUANTITY integer not null,
--                      PRICE decimal(5,2) not null, 
--                      TOTAL_AMOUNT decimal(10,2),
--                        PROD_NAME VARCHAR(50),
--                        PROD_DESC VARCHAR(50) );


--create table PRODUCT_TBL(id integer not null, PRODUCT_NAME varchar(20) not null,
--PRODUCT_DESC  varchar(50) not null, PRICE integer not null,CAT_ID integer not null, primary key(id),
--FOREIGN KEY (CAT_ID) REFERENCES PRODUCT_CATEGORY_TBL(CAT_ID) );

--ALTER TABLE PRODUCT_TBL ADD FOREIGN KEY (CAT_ID) REFERENCES PRODUCT_CATEGORY_TBL(CAT_ID); 

--ALTER TABLE CART_TBL DROP PRIMARY KEY;


--ALTER TABLE CART_TBL ADD PROD_DESC VARCHAR(50);

--ALTER TABLE CART_TBL ADD PROD_NAME VARCHAR(50);

insert into CART_TBL(CART_ID, PROD_ID, USER_ID, USER_NAME, QUANTITY, PRICE, TOTAL_AMOUNT, PROD_NAME, PROD_DESC)
values(1, 0, 0, 'My cart Test user name',1,12.12,33.33,'Test Prod name','Test prod Desc');

create table MTCART
(
PROD_CODE VARCHAR2(10) not null, 
USERID VARCHAR2(10) not null,
PROD_NAME VARCHAR2(50) not null, 
QUANTITY integer not null, 
PRICE  DOUBLE not null, 
primary key(PROD_CODE, USERID) 
);

--ALTER TABLE CARTTBL DROP COLUMN TOTAMOUNT

ALTER TABLE MTCART ADD COLUMN SUB_TOT_AMOUNT DOUBLE not null;

insert into MTCART (PROD_CODE,USERID,PROD_NAME,QUANTITY,PRICE,SUB_TOT_AMOUNT) 
values('P004','TestID','Test name', 1, 1000,1000);

--ALTER TABLE CARTMC ALTER COLUMN TOTAL_AMT RENAME TO TOTALAMT ;

--DROP table CART_NEW;

--DROP table CART;

--, TOTAL_AMT decimal(10,2),

