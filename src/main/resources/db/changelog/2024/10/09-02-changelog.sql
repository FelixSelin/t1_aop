-- liquibase formatted sql

-- changeset felix:1728504316123-1
CREATE SEQUENCE IF NOT EXISTS transactions_seq START WITH 1 INCREMENT BY 50;

-- changeset felix:1728504316123-2
CREATE TABLE transactions
(
    id         BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

-- changeset felix:1728504316123-3
ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

