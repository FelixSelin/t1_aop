-- liquibase formatted sql

-- changeset felix:1728504281933-1
CREATE SEQUENCE IF NOT EXISTS account_seq START WITH 1 INCREMENT BY 50;

-- changeset felix:1728504281933-2
CREATE TABLE account
(
    id           BIGINT NOT NULL,
    account_type VARCHAR(255),
    balance      DECIMAL,
    client_id    BIGINT NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

-- changeset felix:1728504281933-3
ALTER TABLE account
    ADD CONSTRAINT FK_ACCOUNT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

