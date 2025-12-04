CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tipo_conta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE conta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_conta VARCHAR(20) NOT NULL UNIQUE,
    saldo DECIMAL(19,2) NOT NULL,
    tipo_conta VARCHAR(50),
    cliente_id BIGINT,
    CONSTRAINT fk_conta_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE transacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conta_origem_id BIGINT NOT NULL,
    conta_destino_id BIGINT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conta_origem_id) REFERENCES conta(id),
    FOREIGN KEY (conta_destino_id) REFERENCES conta(id)
);