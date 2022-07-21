CREATE TABLE edg(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	funcao VARCHAR(50) NOT NULL,
    codigo_pessoa BIGINT(20) NOT NULL,
    codigo_ejc BIGINT(20) NOT NULL,
  	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo),
  	FOREIGN KEY (codigo_ejc) REFERENCES ejc(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;