
--create table ORDER_TABLE( userID varchar2(20) not null, 
--                          orderID varchar2(50) not null,
--                          productID varchar2(10) not null, 
--                          productName varchar2(30) not null, 
--                          productDesc varchar2(50), 
--                          price integer not null, 
--                          quantity integer not null,
--                          status varchar2(20) not null, 
--                          primary key(userID, orderID,productID)
--                      );

insert into ORDER_TABLE (userID, orderID, productID, product_name, product_Desc, price, quantity, status)
values('TestUser', 'Test Order', 'TestProd', 'TestProdName', 'TestProd desc', 1, 1,'PROCESSING');

--insert into ORDER_TABLE(userID,orderID,productID, product_name, product_Desc, price,quantity,status)
--values('101', 'ord1', 'prod2',  'ProdName', 'Prod desc', 11, 1,'PROCESSING');

--insert into ORDER_TABLE(userID,orderID,productID, product_name, product_Desc, price,quantity,status)
--values('101', 'ord2', 'prod1',  'ProdName', 'Prod desc',  2, 1,'PROCESSING');


----------------------
-------New Design-----
----------------------
-- Create table

--create table ORDERS
--(
--  ID               VARCHAR2(50) not null,
--  AMOUNT           double not null,
--  CUSTOMER_ADDRESS VARCHAR2(255) not null,
--  CUSTOMER_EMAIL   VARCHAR2(128) not null,
--  CUSTOMER_NAME    VARCHAR2(255) not null,
--  CUSTOMER_PHONE   VARCHAR2(128) not null,
--  ORDER_DATE       DATE default sysdate not null,
--  ORDER_NUM        NUMBER(10) not null, primary key (ID)
--) ;

--alter table ORDERS  add primary key (ID) ;

--alter table ORDERS  add constraint ORDER_UK unique (ORDER_NUM) ;
---------------------------------------

insert into ORDERS(ID,AMOUNT,CUSTOMER_ADDRESS,CUSTOMER_EMAIL,CUSTOMER_NAME,CUSTOMER_PHONE,ORDER_DATE,ORDER_NUM)
values('ord1',1000,'address','test.gmail.com','Test Name','8987678',sysdate,10);


-----New table to avoid foreig key in jpa
--create table ORDER_DETAILS_NEW
--(
--  ID         VARCHAR2(50) not null,
--  AMOUNT     DOUBLE not null,
--  PRICE      DOUBLE not null,
--  QUANITY    NUMBER(10) not null,
--  ORDER_ID   VARCHAR2(50) not null,
--  PRODUCT_ID VARCHAR2(20) not null,primary key (ID)
--);

insert into ORDER_DETAILS_NEW(ID,AMOUNT,PRICE,QUANITY,ORDER_ID,PRODUCT_ID)
values('OrdDet1',2000,1000,2,'ord1','P001');  

--alter table ORDER_DETAILS  add primary key (ID) ;

--Create USER ORDER Deatails table

create table USER_ORDER
(
USERID VARCHAR2(10) not null,
ORDER_ID VARCHAR2(50) not null, 
primary key(ORDER_ID)
);

insert into USER_ORDER(USERID,ORDER_ID) values('100','OR123');

  
-- Create table

--create table ORDER_DETAILS
--(
--  ID         VARCHAR2(50) not null,
--  AMOUNT     DOUBLE not null,
--  PRICE      DOUBLE not null,
--  QUANITY    NUMBER(10) not null,
--  ORDER_ID   VARCHAR2(50) not null,
--  PRODUCT_ID VARCHAR2(20) not null,primary key (ID)
--);
  
--alter table ORDER_DETAILS  add primary key (ID) ;
--alter table ORDER_DETAILS  add constraint ORDER_DETAIL_ORD_FK foreign key (ORDER_ID)  references ORDERS (ID);
--alter table ORDER_DETAILS  add constraint ORDER_DETAIL_PROD_FK foreign key (PRODUCT_ID)  references PRODUCTS (CODE);


  
  ----------------------------
  -----Product here as well----
  -----------------------------
--create table PRODUCTS
--(
--  CODE        VARCHAR2(20) not null,
--  IMAGE       BLOB,
--  NAME        VARCHAR2(255) not null,
--  PRICE       DOUBLE not null,
--  CREATE_DATE DATE default sysdate not null,
-- primary key(CODE)
--);

insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P001', 'Sony Bravia 49 Inch TV', 1000, sysdate);
  
insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P002', 'Refrigerator 280ltr Samsung', 2000, sysdate);
  
insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P003', 'Mens Jeans', 3000, sysdate);
  
insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P004', 'Computer Office Chair', 4000, sysdate);




insert into ORDER_DETAILS(ID,AMOUNT,PRICE,QUANITY,ORDER_ID,PRODUCT_ID)
values('OrdDet1',2000,1000,2,'ord1','P001');





