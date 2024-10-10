-- liquibase formatted sql

-- changeset felix:1728573584245-1
ALTER TABLE transactions
    ADD amount DECIMAL(19, 2);
