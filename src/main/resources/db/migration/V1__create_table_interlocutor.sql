CREATE TABLE tb_interlocutor
(
    id         UUID         NOT NULL,
    full_name  VARCHAR(255) NOT NULL,
    about_me   TEXT,
    birth_date DATE         NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    state      VARCHAR(255),
    city       VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tb_interlocutor PRIMARY KEY (id)
);

ALTER TABLE tb_interlocutor
    ADD CONSTRAINT uc_tb_interlocutor_email UNIQUE (email);