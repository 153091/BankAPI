DROP TABLE IF EXISTS credit_cards;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    age        INTEGER                 NOT NULL
);

CREATE TABLE accounts
(
    id         INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    user_id INTEGER NOT NULL,
    number     VARCHAR NOT NULL,
    balance     DOUBLE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE cards
(
    id         INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    account_id INTEGER                 NOT NULL,
    number     VARCHAR                 NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);
