https://dev.mysql.com/downloads/

Download MySQL Community Server

Últimas versões:
- 5.5
- 5.6
- 5.7
- 8.0 (atual)

DROP TABLE if exists banco.produto;

CREATE TABLE banco.produto (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(30) NOT NULL,
  lance_minimo DECIMAL(8, 2) NOT NULL,
  data_cadastro DATE NOT NULL,
  data_venda DATE DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8mb4;

INSERT INTO banco.PRODUTO(NOME, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 40 POL', 2000, curdate());

INSERT INTO banco.PRODUTO(NOME, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 55 POL', 2500, curdate());
