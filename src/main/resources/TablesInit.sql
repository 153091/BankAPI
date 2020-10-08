DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id IDENTITY NOT NULL PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    age        INTEGER                 NOT NULL
);

CREATE TABLE accounts
(
    id IDENTITY NOT NULL PRIMARY KEY,
    user_id    INTEGER NOT NULL,
    number     VARCHAR NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE cards
(
    id IDENTITY NOT NULL PRIMARY KEY,
    account_id INTEGER                 NOT NULL,
    number     VARCHAR                 NOT NULL,
    balance    DOUBLE                  NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);
