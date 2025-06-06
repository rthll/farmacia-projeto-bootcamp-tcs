CREATE TYPE genero_enum AS ENUM ('Masculino', 'Feminino', 'Outros');
CREATE TYPE estado_enum AS ENUM (
    'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA',
    'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN',
    'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO'
);

CREATE TABLE Farmacia (
    idFarmacia SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cnpj VARCHAR(18) UNIQUE NOT NULL
);

CREATE TABLE Setor (
    idSetor SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    valeRefeicao NUMERIC(10,2) DEFAULT 300.00 CHECK (valeRefeicao >= 0),
    valeAlimentacao NUMERIC(10,2) DEFAULT 300.00 CHECK (valeAlimentacao >= 0),
    planoSaude NUMERIC(10,2) DEFAULT 3000.00 CHECK (planoSaude >= 0),
    planoOdonto NUMERIC(10,2) DEFAULT 3000.00 CHECK (planoOdonto >= 0),
    valeTransporte NUMERIC(10,2) DEFAULT 0 CHECK (valeTransporte >= 0),
    idFarmacia INT REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
);

CREATE TABLE Funcionario (
    idFuncionario SERIAL PRIMARY KEY,
    nomeCompleto VARCHAR(150) NOT NULL,
    idade INT NOT NULL CHECK (idade >= 0),
    genero genero_enum,
    idSetor INT REFERENCES Setor(idSetor) ON DELETE SET NULL,
    salarioBase NUMERIC(10,2) NOT NULL CHECK (salarioBase >= 0),
    idFarmacia INT REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
);

CREATE TABLE Produto (
    idProduto SERIAL PRIMARY KEY,
    nomeProduto VARCHAR(150) NOT NULL,
    valorVenda NUMERIC(10,2) NOT NULL CHECK (valorVenda >= 0),
    valorCusto NUMERIC(10,2) NOT NULL CHECK (valorCusto >= 0),
	quantidade INT NOT NULL DEFAULT 0 CHECK (quantidade >= 0),
    idFarmacia INT REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
);


CREATE TABLE Venda (
    idVenda SERIAL PRIMARY KEY,
    idFuncionario INT REFERENCES Funcionario(idFuncionario) ON DELETE SET NULL,
    dataVenda DATE NOT NULL DEFAULT CURRENT_DATE,
    totalVenda NUMERIC(10,2) DEFAULT 0.00 CHECK (totalVenda >= 0),
    idFarmacia INT REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
);

CREATE TABLE VendaProdutos (
    idVenda INT REFERENCES Venda(idVenda) ON DELETE CASCADE,
    idProduto INT REFERENCES Produto(idProduto) ON DELETE CASCADE,
    qtdVendaProduto INT CHECK (qtdVendaProduto > 0),
    valorVendaProduto NUMERIC(10,2) CHECK (valorVendaProduto >= 0),
    PRIMARY KEY (idVenda, idProduto)
);

CREATE TABLE Compra (
    idCompra SERIAL PRIMARY KEY,
    idFuncionario INT REFERENCES Funcionario(idFuncionario) ON DELETE SET NULL,
    dataCompra DATE NOT NULL DEFAULT CURRENT_DATE,
    totalCompra NUMERIC(10,2) DEFAULT 0.00 CHECK (totalCompra >= 0),
    idFarmacia INT REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
);

CREATE TABLE CompraProdutos (
    idCompra INT REFERENCES Compra(idCompra) ON DELETE CASCADE,
    idProduto INT REFERENCES Produto(idProduto) ON DELETE CASCADE,
    qtdCompraProduto INT CHECK (qtdCompraProduto > 0),
    valorCompraProduto NUMERIC(10,2) CHECK (valorCompraProduto >= 0),
    PRIMARY KEY (idCompra, idProduto)
);

CREATE TABLE Transportadora (
    idTransportadora SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE CoberturaTransportadora (
    idTransportadora INT REFERENCES Transportadora(idTransportadora) ON DELETE CASCADE,
    estado estado_enum,
    PRIMARY KEY (idTransportadora, estado)
);

CREATE TABLE NegociosEmAndamento (
    idNegocio SERIAL PRIMARY KEY,
    idCompra INT UNIQUE REFERENCES Compra(idCompra) ON DELETE SET NULL,
    idVenda INT UNIQUE REFERENCES Venda(idVenda) ON DELETE SET NULL,
    idFarmacia INT REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
);

CREATE TABLE Caixa (
    idCaixa SERIAL PRIMARY KEY,
    valorInicial NUMERIC(12,2) NOT NULL DEFAULT 0.00 CHECK (valorInicial >= 0),
    valorAtual NUMERIC(12,2) NOT NULL DEFAULT 0.00 CHECK (valorAtual >= 0),
    idFarmacia INT REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
);

-- inserts para popular tabelas com exemplos para testes
INSERT INTO Farmacia (nome, cnpj) VALUES
('Farmácia Popular Central', '11.222.333/0001-44'),
('Drogaria Saúde Total', '44.555.666/0001-77'),
('Farmácia Bem Estar', '77.888.999/0001-00');

INSERT INTO Setor (nome, valeRefeicao, valeAlimentacao, planoSaude, planoOdonto, valeTransporte, idFarmacia) VALUES
('Atendimento', 350.00, 320.00, 3100.00, 3050.00, 150.00, 1),
('Farmacêutico', 400.00, 380.00, 3500.00, 3200.00, 100.00, 1),
('Caixa', 330.00, 310.00, 3000.00, 3000.00, 170.00, 1),
('Gerência', 500.00, 450.00, 4000.00, 3500.00, 0.00, 1);

INSERT INTO Setor (nome, valeRefeicao, valeAlimentacao, planoSaude, planoOdonto, valeTransporte, idFarmacia) VALUES
('Atendimento', 360.00, 330.00, 3150.00, 3080.00, 160.00, 2),
('Farmacêutico', 410.00, 390.00, 3550.00, 3250.00, 110.00, 2),
('Estoque', 340.00, 315.00, 3050.00, 3020.00, 180.00, 2);

INSERT INTO Setor (nome, valeRefeicao, valeAlimentacao, planoSaude, planoOdonto, valeTransporte, idFarmacia) VALUES
('Atendimento', 345.00, 325.00, 3120.00, 3060.00, 155.00, 3),
('Farmacêutico', 405.00, 385.00, 3520.00, 3220.00, 105.00, 3);

INSERT INTO Funcionario (nomeCompleto, idade, genero, idSetor, salarioBase, imposto, bonificacao, idFarmacia) VALUES
('Carlos Alberto Silva', 30, 'Masculino', 1, 2500.00, 250.00, 100.00, 1),
('Ana Beatriz Costa', 25, 'Feminino', 2, 4500.00, 450.00, 200.00, 1),
('Roberto Lima Junior', 22, 'Masculino', 3, 1800.00, 180.00, 50.00, 1),
('Mariana Oliveira', 45, 'Feminino', 4, 6000.00, 600.00, 500.00, 1);

INSERT INTO Funcionario (nomeCompleto, idade, genero, idSetor, salarioBase, imposto, bonificacao, idFarmacia) VALUES
('Fernanda Souza', 28, 'Feminino', 5, 2600.00, 260.00, 120.00, 2),
('Lucas Pereira', 35, 'Masculino', 6, 4800.00, 480.00, 250.00, 2),
('Juliana Almeida', 23, 'Feminino', 7, 2000.00, 200.00, 70.00, 2);

INSERT INTO Funcionario (nomeCompleto, idade, genero, idSetor, salarioBase, imposto, bonificacao, idFarmacia) VALUES
('Rafael Santos', 32, 'Masculino', 8, 2550.00, 255.00, 110.00, 3),
('Camila Ferreira', 27, 'Feminino', 9, 4600.00, 460.00, 220.00, 3),
('Tiago Mendes', 29, 'Masculino', 8, 2500.00, 250.00, 100.00, 3);

INSERT INTO Produto (nomeProduto, valorVenda, valorCusto, idFarmacia, quantidade) VALUES
('Dipirona 500mg', 10.00, 5.00, 1, 100),
('Paracetamol 750mg', 12.50, 6.00, 1, 100),
('Amoxicilina 250mg', 30.00, 15.00, 1, 100),
('Protetor Solar FPS50', 55.00, 30.00, 1, 100);

INSERT INTO Produto (nomeProduto, valorVenda, valorCusto, idFarmacia, quantidade) VALUES
('Ibuprofeno 400mg', 15.00, 7.00, 2, 100),
('Vitamina C Efervescente', 25.00, 12.00, 2, 100),
('Xarope Expectorante Adulto', 22.00, 10.00, 2, 100),
('Fralda Descartável Pct M', 40.00, 25.00, 2, 100);

INSERT INTO Produto (nomeProduto, valorVenda, valorCusto, idFarmacia, quantidade) VALUES
('Dorflex Cx 10 Comp', 8.50, 4.00, 3, 100),
('Soro Fisiológico 500ml', 7.00, 3.00, 3, 100),
('Água Oxigenada Vol 10', 5.00, 2.00, 3, 100),
('Curativo Adesivo Cx 20', 12.00, 6.50, 3, 100);

INSERT INTO Venda (idFuncionario, dataVenda, totalVenda, idFarmacia) VALUES
(1, '2025-05-01', 42.50, 1),
(2, '2025-05-02', 55.00, 1);

INSERT INTO Venda (idFuncionario, dataVenda, totalVenda, idFarmacia) VALUES
(5, '2025-05-03', 40.00, 2),
(6, '2025-05-04', 22.00, 2);

INSERT INTO Venda (idFuncionario, dataVenda, totalVenda, idFarmacia) VALUES
(8, '2025-05-05', 15.50, 3),
(9, '2025-05-06', 12.00, 3);

INSERT INTO VendaProdutos (idVenda, idProduto, qtdVendaProduto, valorVendaProduto) VALUES
(1, 1, 2, 10.00), 
(1, 2, 1, 12.50); 
UPDATE Venda SET totalVenda = (SELECT SUM(qtdVendaProduto * valorVendaProduto) FROM VendaProdutos WHERE idVenda = 1) WHERE idVenda = 1;

INSERT INTO VendaProdutos (idVenda, idProduto, qtdVendaProduto, valorVendaProduto) VALUES
(2, 4, 1, 55.00); 
UPDATE Venda SET totalVenda = (SELECT SUM(qtdVendaProduto * valorVendaProduto) FROM VendaProdutos WHERE idVenda = 2) WHERE idVenda = 2;

INSERT INTO VendaProdutos (idVenda, idProduto, qtdVendaProduto, valorVendaProduto) VALUES
(3, 5, 1, 15.00),
(3, 6, 1, 25.00); 
UPDATE Venda SET totalVenda = (SELECT SUM(qtdVendaProduto * valorVendaProduto) FROM VendaProdutos WHERE idVenda = 3) WHERE idVenda = 3;

INSERT INTO VendaProdutos (idVenda, idProduto, qtdVendaProduto, valorVendaProduto) VALUES
(4, 7, 1, 22.00); 
UPDATE Venda SET totalVenda = (SELECT SUM(qtdVendaProduto * valorVendaProduto) FROM VendaProdutos WHERE idVenda = 4) WHERE idVenda = 4;

INSERT INTO VendaProdutos (idVenda, idProduto, qtdVendaProduto, valorVendaProduto) VALUES
(5, 9, 1, 8.50), 
(5, 10, 1, 7.00);
UPDATE Venda SET totalVenda = (SELECT SUM(qtdVendaProduto * valorVendaProduto) FROM VendaProdutos WHERE idVenda = 5) WHERE idVenda = 5;

INSERT INTO VendaProdutos (idVenda, idProduto, qtdVendaProduto, valorVendaProduto) VALUES
(6, 12, 1, 12.00); 
UPDATE Venda SET totalVenda = (SELECT SUM(qtdVendaProduto * valorVendaProduto) FROM VendaProdutos WHERE idVenda = 6) WHERE idVenda = 6;

INSERT INTO Compra (idFuncionario, dataCompra, totalCompra, idFarmacia) VALUES
(4, '2025-04-20', 200.00, 1),
(4, '2025-04-25', 90.00, 1);

INSERT INTO Compra (idFuncionario, dataCompra, totalCompra, idFarmacia) VALUES
(6, '2025-04-22', 155.00, 2);

INSERT INTO Compra (idFuncionario, dataCompra, totalCompra, idFarmacia) VALUES
(9, '2025-04-28', 107.50, 3);

INSERT INTO CompraProdutos (idCompra, idProduto, qtdCompraProduto, valorCompraProduto) VALUES
(1, 1, 20, 5.00),
(1, 3, 5, 15.00); 
UPDATE Compra SET totalCompra = (SELECT SUM(qtdCompraProduto * valorCompraProduto) FROM CompraProdutos WHERE idCompra = 1) WHERE idCompra = 1;

INSERT INTO CompraProdutos (idCompra, idProduto, qtdCompraProduto, valorCompraProduto) VALUES
(2, 2, 15, 6.00);
UPDATE Compra SET totalCompra = (SELECT SUM(qtdCompraProduto * valorCompraProduto) FROM CompraProdutos WHERE idCompra = 2) WHERE idCompra = 2;

INSERT INTO CompraProdutos (idCompra, idProduto, qtdCompraProduto, valorCompraProduto) VALUES
(3, 5, 10, 7.00), 
(3, 8, 3, 25.00);  
UPDATE Compra SET totalCompra = (SELECT SUM(qtdCompraProduto * valorCompraProduto) FROM CompraProdutos WHERE idCompra = 3) WHERE idCompra = 3;

INSERT INTO CompraProdutos (idCompra, idProduto, qtdCompraProduto, valorCompraProduto) VALUES
(4, 9, 10, 4.00),  
(4, 12, 5, 6.50); 
UPDATE Compra SET totalCompra = (SELECT SUM(qtdCompraProduto * valorCompraProduto) FROM CompraProdutos WHERE idCompra = 4) WHERE idCompra = 4;

INSERT INTO Transportadora (nome) VALUES
('Loggi Brasil'),
('Trans Express Rápido'),
('Carga Segura Ltda');

INSERT INTO CoberturaTransportadora (idTransportadora, estado) VALUES
(1, 'SP'),
(1, 'RJ'),
(1, 'MG'),
(1, 'PR');

INSERT INTO CoberturaTransportadora (idTransportadora, estado) VALUES
(2, 'BA'),
(2, 'CE'),
(2, 'PE'),
(2, 'RN'),
(2, 'SC');

INSERT INTO CoberturaTransportadora (idTransportadora, estado) VALUES
(3, 'RS'),
(3, 'GO'),
(3, 'MT'),
(3, 'AM');

INSERT INTO NegociosEmAndamento (idCompra, idVenda, idFarmacia) VALUES
(1, NULL, 1),  
(NULL, 1, 1), 
(3, NULL, 2);  

INSERT INTO Caixa (valorInicial, valorAtual, idFarmacia) VALUES
(500000.00, 15000.75, 1);

INSERT INTO Caixa (valorInicial, valorAtual, idFarmacia) VALUES
(400000.00, 12000.50, 2);

INSERT INTO Caixa (valorInicial, valorAtual, idFarmacia) VALUES
(450000.00, 9800.20, 3);

-- querys gerais para o uso do banco

-- retorna quantidade de produtos presentes no estoque, o nome dos produtos e o valor
SELECT
    P.nomeProduto AS "Nome do Produto",
    P.quantidade AS "Quantidade em Estoque",
    P.valorVenda AS "Valor de Venda (R$)"
FROM
    Produto P
JOIN
    Farmacia Fa ON P.idFarmacia = Fa.idFarmacia
WHERE
    Fa.cnpj = '11.222.333/0001-44'
ORDER BY
    P.nomeProduto;
	
-- retorna dados dos funcionarios de uma determinada farmacia
SELECT
    Fc.idFuncionario,
    Fc.nomeCompleto AS "Nome do Funcionário",
    Fc.idade,
    Fc.genero,
    S.nome AS "Nome do Setor",
    Fa.nome AS "Nome da Farmácia",
    Fa.cnpj AS "CNPJ da Farmácia",
    Fc.salarioBase AS "Salário Base (R$)"
FROM
    Funcionario Fc
JOIN
    Setor S ON Fc.idSetor = S.idSetor
JOIN
    Farmacia Fa ON Fc.idFarmacia = Fa.idFarmacia
WHERE
    Fa.cnpj = '11.222.333/0001-44'
ORDER BY
    Fc.nomeCompleto;


-- retorna os setores com a quantidade de funcionarios e os valores de beneficios de cad asetor
SELECT
    Fa.nome AS "Nome da Farmácia",
    S.nome AS "Nome do Setor",
    COUNT(F.idFuncionario) OVER (PARTITION BY S.idSetor) AS "Funcionários no Setor",
    F.idFuncionario AS "ID do Funcionário",
    F.nomeCompleto AS "Nome do Funcionário",
    F.idade AS "Idade",
    F.genero AS "Gênero",
    F.salarioBase AS "Salário Base (R$)",
    S.valeRefeicao AS "Vale Refeição (R$)",
    S.valeAlimentacao AS "Vale Alimentação (R$)",
    S.planoSaude AS "Plano de Saúde (R$)",
    S.planoOdonto AS "Plano Odontológico (R$)",
    S.valeTransporte AS "Vale Transporte (R$)"
FROM
    Funcionario F
JOIN
    Setor S ON F.idSetor = S.idSetor
JOIN
    Farmacia Fa ON F.idFarmacia = Fa.idFarmacia
WHERE
    Fa.cnpj = '11.222.333/0001-44' 
ORDER BY
    S.nome, F.nomeCompleto;
	
-- salarios respectivos de cada funcionário (com os valores de imposto, vale transporte, vale refeição e alimentação, planos de saúde e odontológico,) e as bonificações por participação nos lucros da empresa;
SELECT
    Fa.nome AS "Nome da Farmácia",
    S.nome AS "Nome do Setor",
    COUNT(F.idFuncionario) OVER (PARTITION BY S.idSetor) AS "Funcionários no Setor",
    F.idFuncionario AS "ID do Funcionário",
    F.nomeCompleto AS "Nome do Funcionário",
    F.salarioBase AS "Salário Base (R$)",
    S.valeRefeicao AS "Vale Refeição (R$)",
    S.valeAlimentacao AS "Vale Alimentação (R$)",
    S.planoSaude AS "Plano de Saúde (R$)",
    S.planoOdonto AS "Plano Odontológico (R$)",
    S.valeTransporte AS "Vale Transporte (R$)"
FROM
    Funcionario F
JOIN
    Setor S ON F.idSetor = S.idSetor
JOIN
    Farmacia Fa ON F.idFarmacia = Fa.idFarmacia
WHERE
    Fa.cnpj = '11.222.333/0001-44'
ORDER BY
    S.nome, F.nomeCompleto;

-- Quantidade de transportadoras parceiras e seus respectivos locais de atendimento no estado (Pelos menos 3 transportadoras devem estar presentes no código)
SELECT * FROM coberturaTransportadora ct JOIN transportadora ta ON ct.idtransportadora = ta.idtransportadora;

-- Retornar o somatório total de todas as vendas de uma farmácia específica
SELECT
    SUM(V.totalVenda) AS "Total Vendido na Farmácia (R$)"
FROM
    Venda V
JOIN
    Farmacia F ON V.idFarmacia = F.idFarmacia
WHERE
    F.cnpj = '11.222.333/0001-44'; 

-- query que deve apresentar um valor total do caixa da empresa e deve apresentar uma estimativa de lucros mensais e anuais com base nas vendas programadas pelos vendedores;
WITH LucroPeriodo AS (
    SELECT
        V.idFarmacia,
        SUM(V.totalVenda) AS total_vendido_periodo,
        SUM(VP.qtdVendaProduto * P.valorCusto) AS custo_total_produtos_vendidos,
        GREATEST( (MAX(V.dataVenda)::date - MIN(V.dataVenda)::date) + 1, 1) AS dias_no_periodo
    FROM
        Venda V
    JOIN VendaProdutos VP ON V.idVenda = VP.idVenda
    JOIN Produto P ON VP.idProduto = P.idProduto
    GROUP BY
        V.idFarmacia
),
VendasUltimos30Dias AS (
    SELECT
        V.idFarmacia,
        SUM(V.totalVenda) AS total_vendido_ultimos_30_dias
    FROM
        Venda V
    WHERE
        V.dataVenda >= (CURRENT_DATE - INTERVAL '30 days') AND V.dataVenda <= CURRENT_DATE
    GROUP BY
        V.idFarmacia
)
SELECT
    Fa.nome AS "Nome da Farmácia",
    (SELECT SUM(C.valorAtual) FROM Caixa C WHERE C.idFarmacia = Fa.idFarmacia) AS "Valor Total em Caixa (R$)",
    COALESCE(LP.total_vendido_periodo, 0) AS "Total de Vendas Registradas (R$)",
    COALESCE(V30.total_vendido_ultimos_30_dias, 0) AS "Vendas nos Últimos 30 Dias (R$)",
    COALESCE((LP.total_vendido_periodo - LP.custo_total_produtos_vendidos), 0) AS "Lucro Bruto no Período Registrado (R$)",
    ROUND( (COALESCE((LP.total_vendido_periodo - LP.custo_total_produtos_vendidos), 0) / NULLIF(LP.dias_no_periodo, 0)) * 365, 2) AS "Estimativa de Lucro Anual (R$)"
FROM
    Farmacia Fa
LEFT JOIN 
    LucroPeriodo LP ON Fa.idFarmacia = LP.idFarmacia
LEFT JOIN
    VendasUltimos30Dias V30 ON Fa.idFarmacia = V30.idFarmacia
WHERE
    Fa.cnpj = '11.222.333/0001-44';

