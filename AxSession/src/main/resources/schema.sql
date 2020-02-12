DROP TABLE IF EXISTS ax_session;
create table ax_session
(
    session varchar(60) not null,
    uuid varchar(60) not null,
    date datetime not null,
    constraint ax_session_session_uindex
        unique (session)
);

alter table ax_session
    add primary key (session);