# Farmácia Projeto Bootcamp TCS

Projeto acadêmico desenvolvido durante o Bootcamp 2x2y da TCS com foco na modelagem de um sistema de gestão para farmácia em Java. O repositório concentra regras de negócio e estruturas de domínio para cadastro de farmácia, produtos, compras, vendas, setores, funcionários, transportadoras e indicadores financeiros.

## Visão geral

A proposta do sistema é apoiar a operação interna de uma farmácia por meio de uma base orientada a objetos, separando responsabilidades em camadas como:

- `models` para representar entidades do domínio.
- `controllers` para centralizar regras e fluxos de aplicação.
- `dtos` para transportar dados de listagem e relatórios.
- `daos` para acesso a dados.
- `utils` para validações auxiliares.

## Contexto do negócio

- cadastro de farmácia com validação de CNPJ;
- gestão de produtos e controle de valores de custo e venda;
- registro de compras e vendas;
- organização de setores e benefícios internos;
- cadastro e associação de funcionários;
- gerenciamento de transportadoras e cobertura por estado;
- consolidação de dados para consultas e relatórios, incluindo lucro mensal.

## Escopo funcional identificado

| Área | Objetivo no projeto |
| --- | --- |
| Farmácia | Representar a unidade principal do sistema e associar operações ao CNPJ da empresa |
| Produtos | Registrar itens comercializados, custo, preço de venda e quantidade |
| Compras | Modelar entradas de estoque e valores de aquisição |
| Vendas | Modelar saídas de produtos e cálculo de total vendido |
| Funcionários | Representar colaboradores, idade, gênero e salário |
| Setores | Organizar áreas internas e benefícios oferecidos |
| Transportadoras | Cadastrar parceiros logísticos e mapear estados atendidos |
| Caixa e relatórios | Apoiar visão financeira com totalizações e lucro mensal |

## Arquitetura 

### Organização por camadas

| Camada | Responsabilidade | Exemplos |
| --- | --- | --- |
| `controllers` | Coordenação de fluxos e regras de aplicação | `FarmaciaCtrl`, `ProdutoCtrl`, `TransportadoraCtrl` |
| `models` | Entidades e enums do domínio | `Farmacia`, `Produto`, `Funcionario`, `Estado`, `Genero` |
| `dtos` | Estruturas de listagem e transferência de dados | `TransportadoraDTO`, `SetorListagemDTO`, `LucroMensalDTO` |
| `daos` | Persistência e comunicação com banco | `TransportadoraDAO` |
| `utils` | Regras auxiliares compartilhadas | `ValidadorCNPJ` |

### Estrutura de diretórios

```text
src/
\-- Projeto/
    +-- controllers/
    +-- daos/
    +-- dtos/
    +-- models/
    \-- utils/
```

## Tecnologias e conceitos utilizados

| Tecnologia / conceito | Como aparece no repositório |
| --- | --- |
| Java | Linguagem principal de toda a modelagem |
| Programação orientada a objetos | Classes de domínio, encapsulamento, construtores, getters e setters |
| JDBC | Acesso a banco em `TransportadoraDAO` |
| PostgreSQL | Inferido pela dependência `postgresql-42.7.6` e pela sintaxe SQL utilizada |
| SQL transacional | Uso de `commit`, `rollback` e `batch` para persistir transportadoras e coberturas |
| DTO / DAO | Separação entre transporte de dados e persistência |
| Enums | `Estado`, `Genero` e `Local` para padronização de valores do domínio |
| Collections | Uso de `List`, `Set`, `ArrayList` e `HashSet` |
| API de datas do Java | Uso de `LocalDate` em compras e listagens |
| IntelliJ IDEA | Projeto configurado como módulo Java com arquivo `.iml` |
| Git / GitHub | Versionamento do código-fonte |

## Habilidades trabalhadas no projeto

- modelagem de domínio para um sistema de negócio;
- aplicação de princípios de orientação a objetos;
- separação de responsabilidades em camadas;
- uso de DAO e DTO para estruturar persistência e listagens;
- manipulação de coleções e enums;
- validação de dados de entrada, como CNPJ;
- integração com banco relacional via JDBC;
- noções de transação e consistência na gravação de dados;
- construção de regras para compras, vendas, estoque e lucro;
- organização de projeto Java em ambiente de IDE.

## Principais entidades do domínio

| Entidade | Papel no sistema |
| --- | --- |
| `Farmacia` | Identifica a empresa por nome e CNPJ |
| `Produto` | Define item de estoque com custo, preço e quantidade |
| `Compra` | Representa movimentação de entrada com data e funcionário associado |
| `Venda` | Representa movimentação de saída relacionada a produto e funcionário |
| `Funcionario` | Modela colaboradores da farmácia |
| `Setor` | Organiza departamentos e benefícios internos |
| `Transportadora` | Representa parceiras logísticas e estados atendidos |
| `Caixa` | Guarda valores financeiros da operação |

## Persistência e banco de dados


- `TransportadoraDAO` utiliza `Connection`, `PreparedStatement`, `Statement` e `ResultSet`;
- há consultas para listagem geral e busca por identificador;
- o salvamento é transacional e também grava a cobertura por estado;
- a modelagem SQL sugere tabelas como `transportadora` e `coberturatransportadora`;
- existe indício de enum no banco com o tipo `estado_enum`.


## Como abrir o projeto

### Pré-requisitos

- JDK configurado na máquina;
- IntelliJ IDEA ou outra IDE com suporte a projeto Java;
- driver JDBC do PostgreSQL, se você quiser continuar a evolução da camada de persistência;
- banco PostgreSQL disponível, caso a próxima etapa seja concluir o acesso a dados.

### Passos sugeridos

1. Clone o repositório.
2. Abra o projeto na IDE apontando a pasta raiz.
3. Configure o JDK do projeto.
4. Verifique a dependência do PostgreSQL já referenciada no módulo IntelliJ.
5. Corrija as classes faltantes e os erros de compilação.
6. Adicione um ponto de entrada, testes ou uma interface para executar os fluxos.
