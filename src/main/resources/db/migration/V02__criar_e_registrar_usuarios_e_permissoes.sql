CREATE TABLE igreja(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
    pastor VARCHAR(50) NOT NULL,
    logo BLOB
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ejc(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	numero VARCHAR(5) NOT NULL,
    tema VARCHAR(50) NOT NULL,
    escola VARCHAR(250) NOT NULL,
    endereco_escola VARCHAR(250) NOT NULL,
    inicio DATE NOT NULL,
    fim DATE NOT NULL,
    ativo BOOLEAN NOT NULL,
    logo BLOB,
    codigo_igreja BIGINT(20) NOT NULL,
  	FOREIGN KEY (codigo_igreja) REFERENCES igreja(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY,
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