INSERT INTO cliente (nome, cpf, email) VALUES 
('João Silva', '12345678900', 'joao.silva@example.com'),
('Maria Oliveira', '98765432100', 'maria.oliveira@example.com');

INSERT INTO conta (numero_conta, saldo, tipo_conta, cliente_id) VALUES 
('0001', 1000.00, 'CONTA_CORRENTE', 1),
('0002', 5000.00, 'CONTA_POUPANCA', 2);

INSERT INTO transacao (conta_origem_id, conta_destino_id, valor, data) VALUES 
(1, 2, 200.00, '2023-01-01 10:00:00'),
(2, 1, 150.00, '2023-01-02 11:00:00');