CREATE TABLE circulo(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	cor VARCHAR(20) NOT NULL,
    codigo_coordenador_um BIGINT(20) NOT NULL,
    codigo_coordenador_dois BIGINT(20) NOT NULL,
    codigo_coordenador_tres BIGINT(20),
    codigo_ejc BIGINT(20) NOT NULL,
  	FOREIGN KEY (codigo_coordenador_um) REFERENCES pessoa(codigo),
  	FOREIGN KEY (codigo_coordenador_dois) REFERENCES pessoa(codigo),
  	FOREIGN KEY (codigo_coordenador_tres) REFERENCES pessoa(codigo),
  	FOREIGN KEY (codigo_ejc) REFERENCES ejc(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE circulo_pessoa (
	codigo_circulo BIGINT(20) NOT NULL,
	codigo_pessoa BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_circulo, codigo_pessoa),
	FOREIGN KEY (codigo_circulo) REFERENCES circulo(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;