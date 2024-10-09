-- liquibase formatted sql

-- changeset felix:1728504400693-1
CREATE SEQUENCE IF NOT EXISTS data_source_error_log_seq START WITH 1 INCREMENT BY 50;

-- changeset felix:1728504400693-2
CREATE TABLE data_source_error_log
(
    id               BIGINT NOT NULL,
    stack_trace      TEXT,
    message          VARCHAR(255),
    method_signature VARCHAR(255),
    CONSTRAINT pk_data_source_error_log PRIMARY KEY (id)
);

