create table Address
(
    address_id bigserial not null primary key,
    street     varchar(50)
);

create table client
(
    client_id bigserial   not null primary key,
    name      varchar(50) not null,
    address_id bigserial references Address (address_id)
);
create index idx_client_address_id on client (address_id);
create table Phone
(
    phone_id  bigserial not null primary key,
    number    varchar(50),
    client_id bigserial not null references client (client_id)
);
create index idx_phone_client_id on client (client_id);

