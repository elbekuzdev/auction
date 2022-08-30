create table users(
    id serial primary key ,
    firstname varchar not null ,
    lastname varchar not null ,
    username varchar not null ,
    password varchar not null ,
    is_admin bool default false
);
insert into users (firstname, lastname, username, password, is_admin) values ('Elbek', 'Nurmatov', 'elbek_uzdev', 'elbek2003', true);
insert into users (firstname, lastname, username, password, is_admin) values ('Elbek', 'Nurmatov', 'elbek_nurmatov', 'elbek2003', false);
create table lot(
    id serial primary key ,
    model varchar not null ,
    description varchar not null ,
    start_price double precision not null ,
    start_date timestamp with time zone default now(),
    user_id int references users(id),
    is_active bool default true
);
create table offer(
    id serial primary key ,
    lot_id int not null references lot(id),
    user_id int not null references users(id),
    price double precision not null,
    date timestamp with time zone default now()
);