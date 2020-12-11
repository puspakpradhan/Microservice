--create table AUDIT_TBL(orderID  varchar2(20) not null, userID varchar2(20) not null,
--product_name varchar2(30),quantity integer,order_date date,status varchar2(15), primary key(orderID));
insert into AUDIT_TBL(orderID,userID,product_name,quantity,order_date,status)
values('ord1','100','prd name',0,sysdate,'process'); 

create table ADT_TABLE
(
auditID varchar2(20) not null,
userID varchar2(20) not null,
product_code varchar2(10) not null,
productName varchar2(30),
totAmount    DOUBLE not null,
price        DOUBLE not null,
quantity  NUMBER(10) not null,
name varchar2(50),
email varchar2(30),
phone varchar2(12),
address varchar2(50),
order_date date,
status varchar2(20),primary key(userID,product_code)
 );

--ALTER TABLE AUDIT_TABLE DROP COLUMN productCode

insert into ADT_TABLE (auditID,userID, product_code, productName,totAmount,price,quantity,name,email,phone,address, order_date,status)
values('ADT1', '111', 'P005','ProdNameTest', 123.45, 321.21, 2, 'puspak', 'test mail', '789', 'testAddres', sysdate,'PROCESSING');
