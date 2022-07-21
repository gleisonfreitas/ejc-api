drop table usuario_permissao;
drop table permissao;
drop table usuario;

CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL,
	codigo_ejc BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_ejc) REFERENCES ejc(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	codigo BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into usuario values(1, 'Administrador', 'admin@ejc.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 1);

INSERT INTO permissao VALUES (1, 'ROLE_PESQUISAR_PESSOA');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao VALUES (3, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissao VALUES (4, 'ROLE_PESQUISAR_EQUIPE');
INSERT INTO permissao VALUES (5, 'ROLE_CADASTRAR_EQUIPE');
INSERT INTO permissao VALUES (6, 'ROLE_REMOVER_EQUIPE');
INSERT INTO permissao VALUES (7, 'ROLE_PESQUISAR_CIRCULO');
INSERT INTO permissao VALUES (8, 'ROLE_CADASTRAR_CIRCULO');
INSERT INTO permissao VALUES (9, 'ROLE_REMOVER_CIRCULO');
INSERT INTO permissao VALUES (10, 'ROLE_CADASTRAR_EDG');
INSERT INTO permissao VALUES (11, 'ROLE_PESQUISAR_EDG');
INSERT INTO permissao VALUES (12, 'ROLE_REMOVER_EDG');
INSERT INTO permissao VALUES (13, 'ROLE_CADASTRAR_IGREJA');
INSERT INTO permissao VALUES (14, 'ROLE_PESQUISAR_IGREJA');
INSERT INTO permissao VALUES (15, 'ROLE_REMOVER_IGREJA');
INSERT INTO permissao VALUES (16, 'ROLE_CADASTRAR_EJC');
INSERT INTO permissao VALUES (17, 'ROLE_PESQUISAR_EJC');
INSERT INTO permissao VALUES (18, 'ROLE_REMOVER_EJC');
INSERT INTO permissao VALUES (19, 'ROLE_PESQUISAR_COMPRA');
INSERT INTO permissao VALUES (20, 'ROLE_CADASTRAR_COMPRA');
INSERT INTO permissao VALUES (21, 'ROLE_REMOVER_COMPRA');
INSERT INTO permissao VALUES (22, 'ROLE_PESQUISAR_PATRIMONIO');
INSERT INTO permissao VALUES (23, 'ROLE_CADASTRAR_PATRIMONIO');
INSERT INTO permissao VALUES (24, 'ROLE_REMOVER_PATRIMONIO');	
INSERT INTO permissao VALUES (25, 'ROLE_PESQUISAR_LANCAMENTO');
INSERT INTO permissao VALUES (26, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao VALUES (27, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao VALUES (28, 'ROLE_AGENDA_CIRCULO');
INSERT INTO permissao VALUES (29, 'ROLE_PESQUISAR_USUARIO');
INSERT INTO permissao VALUES (30, 'ROLE_ALTERAR_SENHA');
INSERT INTO permissao VALUES (31, 'ROLE_REMOVER_USUARIO');
INSERT INTO permissao VALUES (32, 'ROLE_CADASTRAR_USUARIO');


insert into usuario_permissao values (1, 1);
insert into usuario_permissao values (1, 2);
insert into usuario_permissao values (1, 3);
insert into usuario_permissao values (1, 4);
insert into usuario_permissao values (1, 5);
insert into usuario_permissao values (1, 6);
insert into usuario_permissao values (1, 7);
insert into usuario_permissao values (1, 8);
insert into usuario_permissao values (1, 9);
insert into usuario_permissao values (1, 10);
insert into usuario_permissao values (1, 11);
insert into usuario_permissao values (1, 12);
insert into usuario_permissao values (1, 13);
insert into usuario_permissao values (1, 14);
insert into usuario_permissao values (1, 15);
insert into usuario_permissao values (1, 16);
insert into usuario_permissao values (1, 17);
insert into usuario_permissao values (1, 18);
insert into usuario_permissao values (1, 19);
insert into usuario_permissao values (1, 20);
insert into usuario_permissao values (1, 21);
insert into usuario_permissao values (1, 22);
insert into usuario_permissao values (1, 23);
insert into usuario_permissao values (1, 24);
insert into usuario_permissao values (1, 25);
insert into usuario_permissao values (1, 26);
insert into usuario_permissao values (1, 27);
insert into usuario_permissao values (1, 28);
insert into usuario_permissao values (1, 29);
insert into usuario_permissao values (1, 30);
insert into usuario_permissao values (1, 31);
insert into usuario_permissao values (1, 32);