CREATE TABLE dados_complementares(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	tamanho_blusa VARCHAR(20),
    profissao VARCHAR(50),
    religiao VARCHAR(20) NOT NULL,
    igreja VARCHAR(50),
    restricao_alimentar VARCHAR(50),
    medicamento VARCHAR(50),
    alergia VARCHAR(50),
    conducao VARCHAR(20),
    instrumento VARCHAR(30),
    codigo_pessoa BIGINT(20) NOT NULL,
  FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE historico(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	quantidade INT NOT NULL,
    coordenador BOOLEAN,
    equipe VARCHAR(20) NOT NULL,
    codigo_pessoa BIGINT(20) NOT NULL,
  FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;