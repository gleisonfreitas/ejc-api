CREATE TABLE padrinho(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    trabalhando BOOLEAN NOT NULL,
    codigo_pessoa BIGINT(20) NOT NULL,
  	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE filiacao(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	pai VARCHAR(50) NOT NULL,
	mae VARCHAR(50) NOT NULL,
    telefone_pai VARCHAR(20),
    telefone_mae VARCHAR(20),
    codigo_pessoa BIGINT(20) NOT NULL,
  	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;