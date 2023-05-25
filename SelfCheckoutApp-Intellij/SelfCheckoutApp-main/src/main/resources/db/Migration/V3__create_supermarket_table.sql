CREATE TABLE IF NOT EXISTS supermarket
(
    id             serial primary key,
    account    varchar(255)                    not null,
    address    varchar(255)                    not null,
    collaboration_start_date    varchar(255)                    not null,
    data_format    varchar(255)                    not null,
    email_contact    varchar(255)                    not null,
    name           varchar(255)                    not null,
    latitude        double                          not null,
    longitude       double                          not null,
    endpoint    varchar(255)    not null
);
