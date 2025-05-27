CREATE TABLE tb_interlocutor_type
(
    id          BIGINT       NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tb_interlocutor_type PRIMARY KEY (id)
);

ALTER TABLE tb_interlocutor_type
    ADD CONSTRAINT uc_tb_interlocutor_type_description UNIQUE (description);