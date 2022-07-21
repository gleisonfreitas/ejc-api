CREATE TABLE equipe(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
    codigo_coordenador_um BIGINT(20) NOT NULL,
    codigo_coordenador_dois BIGINT(20) NOT NULL,
    codigo_coordenador_tres BIGINT(20),
    codigo_ejc BIGINT(20) NOT NULL,
  	FOREIGN KEY (codigo_coordenador_um) REFERENCES pessoa(codigo),
  	FOREIGN KEY (codigo_coordenador_dois) REFERENCES pessoa(codigo),
  	FOREIGN KEY (codigo_coordenador_tres) REFERENCES pessoa(codigo),
  	FOREIGN KEY (codigo_ejc) REFERENCES ejc(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE equipe_pessoa (
	codigo_equipe BIGINT(20) NOT NULL,
	codigo_pessoa BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_equipe, codigo_pessoa),
	FOREIGN KEY (codigo_equipe) REFERENCES equipe(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;