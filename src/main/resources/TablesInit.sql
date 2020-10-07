DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS acc_seq;
DROP SEQUENCE IF EXISTS cards_seq;

CREATE SEQUENCE user_seq START WITH 0;
CREATE SEQUENCE acc_seq START WITH 0;
CREATE SEQUENCE cards_seq START WITH 0;

CREATE TABLE users
(
    id         INTEGER   DEFAULT user_seq.nextval PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    age        INTEGER                 NOT NULL
);

CREATE TABLE accounts
(
    id         INTEGER DEFAULT acc_seq.nextval PRIMARY KEY,
    user_id    INTEGER NOT NULL,
    number     VARCHAR NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE cards
(
    id         INTEGER   DEFAULT cards_seq.nextval PRIMARY KEY,
    account_id INTEGER                 NOT NULL,
    number     VARCHAR                 NOT NULL,
    balance    DOUBLE                  NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);
