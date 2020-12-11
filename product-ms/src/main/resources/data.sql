

--create table PRODUCT_CATEGORY_TBL(CAT_ID integer not null, CATEGORY_NAME varchar(20) , primary key(CAT_ID));

insert into PRODUCT_CATEGORY_TBL(CAT_ID,CATEGORY_NAME)
values(1, 'ELECTRONICS');
insert into PRODUCT_CATEGORY_TBL(CAT_ID,CATEGORY_NAME)
values(2, 'FASHION');
insert into PRODUCT_CATEGORY_TBL(CAT_ID,CATEGORY_NAME)
values(3, 'FURNISHING');
insert into PRODUCT_CATEGORY_TBL(CAT_ID,CATEGORY_NAME)
values(4, 'MOBILE');
insert into PRODUCT_CATEGORY_TBL(CAT_ID,CATEGORY_NAME)
values(5, 'TOYS');
insert into PRODUCT_CATEGORY_TBL(CAT_ID,CATEGORY_NAME)
values(6, 'MEDICALEQUIPMENT');

--create table PRODUCT_TBL(id integer not null, PRODUCT_NAME varchar(20) not null,
--PRODUCT_DESC  varchar(50) not null, PRICE integer not null,CAT_ID integer not null, primary key(id),
--FOREIGN KEY (CAT_ID) REFERENCES PRODUCT_CATEGORY_TBL(CAT_ID) );

--ALTER TABLE PRODUCT_TBL ADD FOREIGN KEY (CAT_ID) REFERENCES PRODUCT_CATEGORY_TBL(CAT_ID); 


insert into PRODUCT_TBL(id,PRODUCT_NAME,PRODUCT_DESC,PRICE,CAT_ID)
values(1001, 'TV', 'Sony Bravia 49 Inch', 1000,1);

insert into PRODUCT_TBL(id,PRODUCT_NAME,PRODUCT_DESC,PRICE,CAT_ID)
values(1002, 'REfrigarator', 'Samsung 280 Litr', 2000,1);

insert into PRODUCT_TBL(id,PRODUCT_NAME,PRODUCT_DESC,PRICE,CAT_ID)
values(1003, 'Kurta', 'This is Kurta', 3000,2);

insert into PRODUCT_TBL(id,PRODUCT_NAME,PRODUCT_DESC,PRICE,CAT_ID)
values(1004, 'Chair', 'Godrej Computer Chair', 4000,3);

insert into PRODUCT_TBL(id,PRODUCT_NAME,PRODUCT_DESC,PRICE,CAT_ID)
values(1005, 'iPhone', 'This is iPhone', 5000,4);

insert into PRODUCT_TBL(id,PRODUCT_NAME,PRODUCT_DESC,PRICE,CAT_ID)
values(1006, 'Train Toy', 'This is a Train toy', 6000,5);

insert into PRODUCT_TBL(id,PRODUCT_NAME,PRODUCT_DESC,PRICE,CAT_ID)
values(1007, 'Oxymeter', 'This is oxymeter', 7000,6);

-------------------------
--------New design-------
-------------------------
--create table PRODUCTS
--(
--  CODE        VARCHAR2(20) not null,
--  IMAGE       BLOB,
--  NAME        VARCHAR2(255) not null,
--  PRICE       DOUBLE not null,
--  CREATE_DATE DATE default sysdate not null,
--  primary key(CODE)
--);

insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P001', 'Sony Bravia 49 Inch TV', 1000, sysdate);
  
insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P002', 'Refrigerator 280ltr Samsung', 2000, sysdate);
  
insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P003', 'Mens Jeans', 3000, sysdate);
  
insert into PRODUCTS (CODE, NAME, PRICE, CREATE_DATE)
values ('P004', 'Computer Office Chair', 4000, sysdate);
  

  
  
  
  
  
  
  
