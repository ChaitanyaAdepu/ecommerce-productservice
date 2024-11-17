create schema productdb;

create table productdb.products(
    id bigint primary key,
	product_name character varying(255),
	price float,
	quantity integer
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
