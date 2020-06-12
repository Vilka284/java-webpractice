DROP DATABASE IF EXISTS java_task;
CREATE DATABASE java_task;
\c java_task

CREATE TABLE IF NOT EXISTS role
(
    id        INT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "group"
(
    id              INT PRIMARY KEY,
    group_name      VARCHAR(50) NOT NULL,
    parent_group_id INT         NULL, /* Allow null value to be able insert first data */
    FOREIGN KEY (parent_group_id) REFERENCES "group" (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "user"
(
    id        SERIAL PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    password  VARCHAR(50) NOT NULL,
    role_id   INT         NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE IF NOT EXISTS item
(
    id        SERIAL PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    price     FLOAT(2)     NOT NULL,
    quantity  INT          NOT NULL,
    group_id  INT          NOT NULL,
    FOREIGN KEY (group_id) REFERENCES "group" (id)
);

CREATE TABLE IF NOT EXISTS "order"
(
    id      SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_item
(
    id       SERIAL PRIMARY KEY,
    item_id  INT NOT NULL,
    order_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES "order" (id) ON DELETE CASCADE
);