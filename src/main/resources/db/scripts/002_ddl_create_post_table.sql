create table post (
                    id serial primary key,
                    name varchar(512),
                    text text,
                    link varchar(1024) unique,
                    created timestamp
);