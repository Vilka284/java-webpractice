INSERT INTO role (id, role_name)
VALUES (1, 'user'),
       (2, 'manager'),
       (3, 'admin');

INSERT INTO "group" (id, group_name, parent_group_id)
VALUES (1, 'Sport', null),
       (2, 'Hunting', null),
       (3, 'Tools', null),
       (4, 'Clothes', 1),
       (5, 'Shoes', 1);

INSERT INTO "user" (user_name, password, role_id)
VALUES ('Andrii', '12345678', 3),
       ('Antonio', '12345678', 2),
       ('Helen', '12345678', 1);

INSERT INTO item (item_name, price, quantity, group_id)
VALUES ('Baseball bat', 50.20, 15, 1),
       ('Football ball', 30, 25, 1),
       ('Volleyball ball', 30, 25, 1),
       ('Hunting rifle', 240, 5, 2),
       ('Bow', 100, 10, 2),
       ('Repair kit', 80, 30, 3),
       ('Sport trousers', 45, 20, 4),
       ('T-shirt', 40, 20, 4),
       ('Shorts', 30, 20, 4),
       ('Football shoes', 60, 20, 5),
       ('Volleyball shoes', 65, 20, 5);