-- liquibase formatted sql

-- changeset felix:1728504661640-1
CREATE SEQUENCE IF NOT EXISTS time_limit_exceed_log_seq START WITH 1 INCREMENT BY 50;

-- changeset felix:1728504661640-2
CREATE TABLE time_limit_exceed_log
(
    id               BIGINT NOT NULL,
    method_signature VARCHAR(255),
    execution_time   BIGINT,
    CONSTRAINT pk_time_limit_exceed_log PRIMARY KEY (id)
);

