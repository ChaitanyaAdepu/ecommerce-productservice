create schema products;
-----
create table products.category(
    id bigint primary key,
	name character varying(255),
	description character varying(10000),
	created_on timestamp with time zone,
	created_by character varying(100),
	modified_on timestamp with time zone,
	modified_by character varying(100),
        visible boolean default false,
code character varying(20)
);

CREATE SEQUENCE products.category_id_seq
START WITH 101
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;
ALTER SEQUENCE products.category_id_seq
OWNED BY products.category.id;
ALTER TABLE ONLY products.category ALTER COLUMN id SET DEFAULT NEXTVAL('products.category_id_seq'::REGCLASS);

------------
create table products.product(
    id bigint primary key,
	product_name character varying(255),
	description character varying(10000),
	price float,
	quantity integer,
	category_id bigint,
        created_on timestamp with time zone,
	created_by character varying(100),
	modified_on timestamp with time zone,
	modified_by character varying(100),
        visible boolean default false,
code character varying(20),

	CONSTRAINT fk_product_category FOREIGN KEY (category_id)
    REFERENCES products.category (id) MATCH SIMPLE 
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE products.product_id_seq
START WITH 101
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;
ALTER SEQUENCE products.product_id_seq
OWNED BY products.product.id;
ALTER TABLE ONLY products.product ALTER COLUMN id SET DEFAULT NEXTVAL('products.product_id_seq'::REGCLASS);

---

-----
--ALTER TABLE products.product ADD CONSTRAINT fk_product_category FOREIGN KEY (category_id)
--REFERENCES products.category (id) MATCH SIMPLE 
--ON UPDATE NO ACTION ON DELETE NO ACTION;
-----

INSERT INTO products.category(name, description, visible, created_on, created_by) values ('Mobiles','mobiles',true,current_date,'SYSTEM');
INSERT INTO products.category(name, description, visible, created_on, created_by) values ('Books','books',true,current_date,'SYSTEM');


------------------
----PRODUCT REVIEWS TABLE-----
---------------------
create table products.reviews(
    id bigint primary key,
	title character varying,
	description character varying,
	rating float,
	product_id bigint,
	author_name character varying(100),
	author_user_id bigint,
    flag_suspicious boolean default false,
	created_on timestamp with time zone,
	created_by character varying(100),
	modified_on timestamp with time zone,
	modified_by character varying(100),
	CONSTRAINT fk_productid_review FOREIGN KEY (product_id)
    REFERENCES products.product (id) MATCH SIMPLE 
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE products.reviews_id_seq
START WITH 101
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;
ALTER SEQUENCE products.reviews_id_seq
OWNED BY products.reviews.id;
ALTER TABLE ONLY products.reviews ALTER COLUMN id SET DEFAULT NEXTVAL('products.reviews_id_seq'::REGCLASS);
---------------------------------------
---------PRODUCT IMAGES-------------
-----------------
create table products.product_images(
    id bigint primary key,
	image_url character varying,
	description character varying(1000),
	product_id bigint,
        seq_num smallint default 1,

    visible boolean default true,
	created_on timestamp with time zone,
	created_by character varying(100),
	modified_on timestamp with time zone,
	modified_by character varying(100),
	CONSTRAINT fk_productid_review FOREIGN KEY (product_id)
    REFERENCES products.product (id) MATCH SIMPLE 
    ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE products.product_images_id_seq
START WITH 101
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;
ALTER SEQUENCE products.product_images_id_seq
OWNED BY products.product_images.id;
ALTER TABLE ONLY products.product_images ALTER COLUMN id SET DEFAULT NEXTVAL('products.product_images_id_seq'::REGCLASS);
------------------------------------------------