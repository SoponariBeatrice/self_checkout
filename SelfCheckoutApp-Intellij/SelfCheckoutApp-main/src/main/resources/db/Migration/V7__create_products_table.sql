CREATE TABLE IF NOT EXISTS product(
  id serial primary key,
  barcode varchar(255) not null,
  description varchar(255) not null,
  name varchar(255) not null,
  price float not null,
  quantity float not null,
  supermarket_id BIGINT UNSIGNED,
  category_id BIGINT UNSIGNED,
   FOREIGN KEY(supermarket_id) references supermarket (id),
    FOREIGN KEY(category_id) references category (id)
);
