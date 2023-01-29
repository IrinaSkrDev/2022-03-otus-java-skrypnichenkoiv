create table client
(
    client_id bigserial   not null primary key,
    name      varchar(50) not null
);
create table address
(
    address_id bigserial not null primary key,
    street     varchar(50),
    client_id  bigserial not null references client (client_id)
);

--create index idx_address_client_id on address (client_id);
create table phone
(
    phone_id  bigserial not null primary key,
    number    varchar(50),
    client_id bigserial not null references client (client_id)
);
--create index idx_phone_client_id on phone (client_id);

