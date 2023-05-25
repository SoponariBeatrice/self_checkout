CREATE TABLE IF NOT EXISTS category
(
    id   serial primary key,
    name varchar(255) unique not null
);

INSERT INTO category(id, name ) VALUES (1, 'popcorn');
INSERT INTO category(id, name ) VALUES (2, 'juices');
INSERT INTO category(id, name ) VALUES (3, 'chocolate');
INSERT INTO category(id, name ) VALUES (4, 'chips');
INSERT INTO category(id, name ) VALUES (5, 'ice cream');
INSERT INTO category(id, name ) VALUES (6, 'alcohols');
INSERT INTO category(id, name ) VALUES (7, 'dairy products');
INSERT INTO category(id, name ) VALUES (8, 'personal care');
INSERT INTO category(id, name ) VALUES (9, 'pet shop');
INSERT INTO category(id, name ) VALUES (10, 'meats');









