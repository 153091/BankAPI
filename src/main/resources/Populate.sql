DELETE
FROM cards;
DELETE
FROM accounts;
DELETE
FROM users;

INSERT INTO users (name, age)
VALUES ('Ivan', 20);

INSERT INTO accounts (user_id, number)
VALUES (0, '11 22 33');

INSERT INTO cards (account_id, number, balance)
VALUES (0, '111 222 444', 200);