ALTER TABLE tb_interlocutor
    ADD type_id BIGINT NOT NULL DEFAULT 2 ;

ALTER TABLE tb_interlocutor
    ADD CONSTRAINT FK_TB_INTERLOCUTOR_ON_TYPE FOREIGN KEY (type_id) REFERENCES tb_interlocutor_type (id);