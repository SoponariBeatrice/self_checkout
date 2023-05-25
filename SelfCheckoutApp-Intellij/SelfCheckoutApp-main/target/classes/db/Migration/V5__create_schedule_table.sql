CREATE TABLE IF NOT EXISTS schedule (
                                        id serial PRIMARY KEY,
                                        start_time time NOT NULL,
                                        end_time time NOT NULL,
                                        FOREIGN KEY (id) REFERENCES supermarket(id)
);

insert into schedule (id, start_time, end_time)
values(1, '08:00:00', '22:00:00');
insert into schedule (id, start_time, end_time)
values(2, '10:00:00', '22:00:00');
insert into schedule (id, start_time, end_time)
values(3, '08:00:00', '20:00:00');