-- tabel Product --
create table product (
    id varchar(36) primary key,
    code varchar(10) not null unique,
    name varchar(255) not null,
    price decimal(19,2) not null
) Engine=InnoDB;

insert into product (id, code, name, price)
values ('p001', 'P-001', 'Product 001', 101001.01);
