create table wallet
(
    id            serial         not null,
    amount        numeric(38, 2) not null,
    currency      varchar(255)   not null,
    name          varchar(255)   not null,
    wallet_number varchar(255)   not null unique,
    user_id       integer not null,
    created_at timestamp(6) not null,
    primary key (id),
    foreign key (user_id)
        references users,
    unique(user_id, name)
);


INSERT INTO wallet (amount, currency, name, wallet_number, user_id, created_at)
values
    (100.05,
     'USD',
     'MY1', 'n848712', 1, '2016-06-22 19:10:25-07'),
    (100.05,
     'USD',
     'MY2', 'n848711', 1, '2016-06-22 19:10:25-07'),
    (100.05,
     'USD',
     'MY3', 'n848722', 1, '2016-06-22 19:10:25-07'),
    (100.05,
     'USD',
     'MY4', 'a848712', 1, '2016-06-22 19:10:25-07'),
    (100.05,
     'USD',
     'MY5', 'b848712', 1, '2016-06-22 19:10:25-07'),
    (100.05,
     'USD',
     'MY6', 'c848712', 1, '2016-06-22 19:10:25-07');