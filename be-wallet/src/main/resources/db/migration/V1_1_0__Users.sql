create table if not exists users
(
    id         serial       not null,
    password   varchar(255) not null,
    role       varchar(255) not null,
    username   varchar(255) not null unique,
    created_at timestamp(6) not null,
    primary key (id)
);

INSERT INTO users (username, role, password, created_at)
values ('test',
        'USER',
        '$2a$10$ahpDdUKfT7weAlGX.jpQ5OLXyH6TZcASZCGjihNfDWaCHj.J7b2q.',
        '2016-06-22 19:10:25-07');
