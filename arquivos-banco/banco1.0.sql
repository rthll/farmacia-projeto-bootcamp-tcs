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
    valeRefeicao NUMERIC(10,2) DEFAULT 300.00,
    valeAlimentacao NUMERIC(10,2) DEFAULT 300.00,
    planoSaude NUMERIC(10,2) DEFAULT 3000.00,
    planoOdonto NUMERIC(10,2) DEFAULT 3000.00,
    valeTransporte NUMERIC(10,2) DEFAULT 0
);

CREATE TABLE Funcionario (
    idFuncionario SERIAL PRIMARY KEY,
    nomeCompleto VARCHAR(150) NOT NULL,
    idade INT NOT NULL,
    genero genero_enum,
    idSetor INT REFERENCES Setor(idSetor),
    salarioBase NUMERIC(10,2) NOT NULL,
    imposto NUMERIC(10,2),
    bonificacao NUMERIC(10,2) DEFAULT 0.00,
    idFarmacia INT REFERENCES Farmacia(idFarmacia)
);

CREATE TABLE Produto (
    idProduto SERIAL PRIMARY KEY,
    nomeProduto VARCHAR(150) NOT NULL,
    valorVenda NUMERIC(10,2) NOT NULL,
    valorCusto NUMERIC(10,2) NOT NULL,
    idFarmacia INT REFERENCES Farmacia(idFarmacia)
);

CREATE TABLE Estoque (
    idProduto INT PRIMARY KEY REFERENCES Produto(idProduto),
    saldoEstoque INT NOT NULL,
    idFarmacia INT REFERENCES Farmacia(idFarmacia)
);

CREATE TABLE Venda (
    idVenda SERIAL PRIMARY KEY,
    idFuncionario INT REFERENCES Funcionario(idFuncionario),
    dataVenda DATE NOT NULL DEFAULT CURRENT_DATE,
    totalVenda NUMERIC(10,2) DEFAULT 0.00,
    idFarmacia INT REFERENCES Farmacia(idFarmacia)
);

CREATE TABLE VendaProdutos (
    idVenda INT REFERENCES Venda(idVenda),
    idProduto INT REFERENCES Produto(idProduto),
    qtdVendaProduto INT,
    valorVendaProduto NUMERIC(10,2),
    PRIMARY KEY (idVenda, idProduto)
);

CREATE TABLE Compra (
    idCompra SERIAL PRIMARY KEY,
    idFuncionario INT REFERENCES Funcionario(idFuncionario),
    dataCompra DATE NOT NULL DEFAULT CURRENT_DATE,
    totalCompra NUMERIC(10,2) DEFAULT 0.00,
    idFarmacia INT REFERENCES Farmacia(idFarmacia)
);

CREATE TABLE CompraProdutos (
    idCompra INT REFERENCES Compra(idCompra),
    idProduto INT REFERENCES Produto(idProduto),
    qtdCompraProduto INT,
    valorCompraProduto NUMERIC(10,2),
    PRIMARY KEY (idCompra, idProduto)
);

CREATE TABLE Transportadora (
    idTransportadora SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE Local (
    idTransportadora INT REFERENCES Transportadora(idTransportadora),
    estado estado_enum,
    PRIMARY KEY (idTransportadora, estado)
);

CREATE TABLE NegociosEmAndamento (
    idNegocio SERIAL PRIMARY KEY,
    idCompra INT REFERENCES Compra(idCompra),
    idVenda INT REFERENCES Venda(idVenda),
    idFarmacia INT REFERENCES Farmacia(idFarmacia)
);


CREATE TABLE Caixa (
    idCaixa SERIAL PRIMARY KEY,
    valorInicial NUMERIC(12,2) NOT NULL DEFAULT 200000.00,
    valorAtual NUMERIC(12,2) NOT NULL DEFAULT 200000.00,
    idFarmacia INT REFERENCES Farmacia(idFarmacia)
);




