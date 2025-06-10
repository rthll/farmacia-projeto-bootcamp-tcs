package Projeto.daos;

import Projeto.models.Compra;
import Projeto.models.ProdutosCompra;
import Projeto.models.Produto;
import Projeto.dtos.CompraListagemDTO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    private Connection connection;

    public CompraDAO(Connection connection) {
        this.connection = connection;
    }

    public int inserirCompra(Compra compra, List<ProdutosCompra> produtosNaCompra) throws SQLException {
        String sqlCompra = "INSERT INTO Compras (idFuncionario, dataCompra, cnpjFarmacia) VALUES (?, ?, ?)";
        String sqlProdutosCompra = "INSERT INTO ProdutosCompra (idNotaCompra, idProduto, cnpjFarmacia, quantidade) VALUES (?, ?, ?, ?)";
        String sqlAtualizaEstoque = "UPDATE Produtos SET qtdProduto = qtdProduto + ? WHERE idProduto = ? AND cnpjFarmacia = ?";

        PreparedStatement stmtCompra = null;
        PreparedStatement stmtProdutosCompra = null;
        PreparedStatement stmtAtualizaEstoque = null;
        ResultSet rs = null;
        int idCompraGerado = -1;

        boolean originalAutoCommit = this.connection.getAutoCommit();
        try {
            this.connection.setAutoCommit(false);


            stmtCompra = connection.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);
            stmtCompra.setInt(1, compra.getIdFuncionario());
            stmtCompra.setDate(2, Date.valueOf(compra.getDataCompra()));
            stmtCompra.setString(3, compra.getCnpjFarmacia());
            int affectedRows = stmtCompra.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar compra, nenhuma linha afetada.");
            }

            rs = stmtCompra.getGeneratedKeys();
            if (rs.next()) {
                idCompraGerado = rs.getInt(1);
                compra.setIdCompra(idCompraGerado);
            } else {
                throw new SQLException("Falha ao criar compra, nenhum ID gerado.");
            }

            stmtProdutosCompra = connection.prepareStatement(sqlProdutosCompra);
            stmtAtualizaEstoque = connection.prepareStatement(sqlAtualizaEstoque);

            for (ProdutosCompra prodCompra : produtosNaCompra) {
                stmtProdutosCompra.setInt(1, idCompraGerado);
                stmtProdutosCompra.setInt(2, prodCompra.getIdProduto());
                stmtProdutosCompra.setString(3, compra.getCnpjFarmacia());
                stmtProdutosCompra.setInt(4, prodCompra.getQuantidade());
                stmtProdutosCompra.addBatch();

                stmtAtualizaEstoque.setDouble(1, prodCompra.getQuantidade());
                stmtAtualizaEstoque.setInt(2, prodCompra.getIdProduto());
                stmtAtualizaEstoque.setString(3, compra.getCnpjFarmacia());
                stmtAtualizaEstoque.addBatch();
            }
            stmtProdutosCompra.executeBatch();
            stmtAtualizaEstoque.executeBatch();

            connection.commit();
            return idCompraGerado;

        } catch (SQLException e) {
            this.connection.rollback();
            System.err.println("Erro ao inserir compra, produtos e atualizar estoque: " + e.getMessage());
            throw e;
        } finally {
            try {
                if (stmtCompra != null) stmtCompra.close();
                if (stmtProdutosCompra != null) stmtProdutosCompra.close();
                if (stmtAtualizaEstoque != null) stmtAtualizaEstoque.close();
                if (rs != null) rs.close();
                this.connection.setAutoCommit(originalAutoCommit);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatements ou ResultSet ou resetar autoCommit: " + e.getMessage());
                throw e;
            }
        }
    }

    public Compra buscarCompraPorId(int idCompra) throws SQLException {
        Compra compra = null;
        String sqlPrincipal = "SELECT idCompra, idFuncionario, dataCompra, cnpjFarmacia FROM Compras WHERE idCompra = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlPrincipal)) {
            pstmt.setInt(1, idCompra);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    compra = new Compra();
                    compra.setIdCompra(rs.getInt("idCompra"));
                    compra.setIdFuncionario(rs.getInt("idFuncionario"));
                    compra.setDataCompra(rs.getDate("dataCompra").toLocalDate());
                }
            }
        }
        return compra;
    }

    public List<CompraListagemDTO> listarTodasCompras() throws SQLException {
        List<CompraListagemDTO> compras = new ArrayList<>();
        String sql = "SELECT " +
                "c.idCompra, " +
                "f.nome AS nomeFuncionario, " +
                "c.dataCompra, " +
                "SUM(pc.quantidade) AS qtdProdutos, " +
                "SUM(p.valorCusto * pc.quantidade) AS valorFinal " +
                "FROM Compras c " +
                "JOIN Funcionarios f ON c.idFuncionario = f.idFuncionario " +
                "LEFT JOIN ProdutosCompra pc ON c.idCompra = pc.idNotaCompra AND c.cnpjFarmacia = pc.cnpjFarmacia " +
                "LEFT JOIN Produtos p ON pc.idProduto = p.idProduto AND pc.cnpjFarmacia = p.cnpjFarmacia " +
                "GROUP BY c.idCompra, f.nome, c.dataCompra " +
                "ORDER BY c.dataCompra DESC, c.idCompra DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CompraListagemDTO dto = new CompraListagemDTO();
                dto.setIdCompra(rs.getInt("idCompra"));
                dto.setNomeFuncionario(rs.getString("nomeFuncionario"));
                dto.setDataCompra(rs.getDate("dataCompra").toLocalDate());
                dto.setQtdProdutos(rs.getInt("qtdProdutos"));
                dto.setValorFinal(rs.getDouble("valorFinal"));
                compras.add(dto);
            }
        }
        return compras;
    }

    public boolean atualizarCompra(Compra compra) throws SQLException {
        String sql = "UPDATE Compras SET idFuncionario = ?, dataCompra = ?, cnpjFarmacia = ? WHERE idCompra = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, compra.getIdFuncionario());
            pstmt.setDate(2, Date.valueOf(compra.getDataCompra()));
            pstmt.setString(3, compra.getCnpjFarmacia());
            pstmt.setInt(4, compra.getIdCompra());
            return pstmt.executeUpdate() > 0;
        }
    }


    public boolean excluirCompra(int idCompra) throws SQLException {
        String sqlSelectProdutosCompra = "SELECT idProduto, quantidade, cnpjFarmacia FROM ProdutosCompra WHERE idNotaCompra = ?";
        String sqlDeleteProdutosCompra = "DELETE FROM ProdutosCompra WHERE idNotaCompra = ?";
        String sqlDeleteCompra = "DELETE FROM Compras WHERE idCompra = ?";
        String sqlDiminuiEstoque = "UPDATE Produtos SET qtdProduto = qtdProduto - ? WHERE idProduto = ? AND cnpjFarmacia = ?";

        PreparedStatement stmtSelect = null;
        PreparedStatement stmtDiminuiEstoque = null;
        PreparedStatement stmtProdutosCompra = null;
        PreparedStatement stmtCompra = null;
        ResultSet rs = null;

        boolean originalAutoCommit = this.connection.getAutoCommit();
        try {
            this.connection.setAutoCommit(false);
            stmtSelect = connection.prepareStatement(sqlSelectProdutosCompra);
            stmtSelect.setInt(1, idCompra);
            rs = stmtSelect.executeQuery();

            List<ProdutosCompra> produtosParaDiminuir = new ArrayList<>();
            while (rs.next()) {
                ProdutosCompra pc = new ProdutosCompra();
                pc.setIdProduto(rs.getInt("idProduto"));
                pc.setQuantidade(rs.getInt("quantidade"));
                Sessao.setCnpjFarmacia(rs.getString("cnpjFarmacia"));
                produtosParaDiminuir.add(pc);
            }

            stmtDiminuiEstoque = connection.prepareStatement(sqlDiminuiEstoque);
            for (ProdutosCompra pc : produtosParaDiminuir) {
                stmtDiminuiEstoque.setDouble(1, pc.getQuantidade());
                stmtDiminuiEstoque.setInt(2, pc.getIdProduto());
                stmtDiminuiEstoque.setString(3, Sessao.setCnpjFarmacia());
                stmtDiminuiEstoque.addBatch();
            }
            stmtDiminuiEstoque.executeBatch();

            stmtProdutosCompra = connection.prepareStatement(sqlDeleteProdutosCompra);
            stmtProdutosCompra.setInt(1, idCompra);
            stmtProdutosCompra.executeUpdate();

            stmtCompra = connection.prepareStatement(sqlDeleteCompra);
            stmtCompra.setInt(1, idCompra);
            int affectedRows = stmtCompra.executeUpdate();

            if (affectedRows > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            this.connection.rollback();
            System.err.println("Erro ao excluir compra e produtos associados, ou ao diminuir estoque: " + e.getMessage());
            throw e;
        } finally {
            try {
                if (stmtSelect != null) stmtSelect.close();
                if (stmtDiminuiEstoque != null) stmtDiminuiEstoque.close();
                if (stmtProdutosCompra != null) stmtProdutosCompra.close();
                if (stmtCompra != null) stmtCompra.close();
                if (rs != null) rs.close();
                this.connection.setAutoCommit(originalAutoCommit);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatements ou ResultSet ou resetar autoCommit: " + e.getMessage());
                throw e;
            }
        }
    }

    public boolean adicionarProdutoNaCompra(int idCompra, int idProduto, String cnpjFarmacia, int quantidade) throws SQLException {
        String sqlProdutosCompra = "INSERT INTO ProdutosCompra (idNotaCompra, idProduto, cnpjFarmacia, quantidade) VALUES (?, ?, ?, ?)";
        String sqlAtualizaEstoque = "UPDATE Produtos SET qtdProduto = qtdProduto + ? WHERE idProduto = ? AND cnpjFarmacia = ?";
        PreparedStatement stmtProdutosCompra = null;
        PreparedStatement stmtAtualizaEstoque = null;

        boolean originalAutoCommit = this.connection.getAutoCommit();
        try {
            this.connection.setAutoCommit(false);

            stmtProdutosCompra = connection.prepareStatement(sqlProdutosCompra);
            stmtProdutosCompra.setInt(1, idCompra);
            stmtProdutosCompra.setInt(2, idProduto);
            stmtProdutosCompra.setString(3, cnpjFarmacia);
            stmtProdutosCompra.setInt(4, quantidade);
            int affectedRowsProdutosCompra = stmtProdutosCompra.executeUpdate();

            stmtAtualizaEstoque = connection.prepareStatement(sqlAtualizaEstoque);
            stmtAtualizaEstoque.setDouble(1, quantidade);
            stmtAtualizaEstoque.setInt(2, idProduto);
            stmtAtualizaEstoque.setString(3, cnpjFarmacia);
            int affectedRowsEstoque = stmtAtualizaEstoque.executeUpdate();

            if (affectedRowsProdutosCompra > 0 && affectedRowsEstoque > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            this.connection.rollback();
            System.err.println("Erro ao adicionar produto na compra e atualizar estoque: " + e.getMessage());
            throw e;
        } finally {
            try {
                if (stmtProdutosCompra != null) stmtProdutosCompra.close();
                if (stmtAtualizaEstoque != null) stmtAtualizaEstoque.close();
                this.connection.setAutoCommit(originalAutoCommit);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatements ou resetar autoCommit: " + e.getMessage());
                throw e;
            }
        }
    }

    public boolean removerProdutoDaCompra(int idCompra, int idProduto) throws SQLException {
        String sqlSelectQuantidade = "SELECT quantidade, cnpjFarmacia FROM ProdutosCompra WHERE idNotaCompra = ? AND idProduto = ?";
        String sqlDeleteProdutosCompra = "DELETE FROM ProdutosCompra WHERE idNotaCompra = ? AND idProduto = ?";
        String sqlDiminuiEstoque = "UPDATE Produtos SET qtdProduto = qtdProduto - ? WHERE idProduto = ? AND cnpjFarmacia = ?";

        PreparedStatement stmtSelect = null;
        PreparedStatement stmtDelete = null;
        PreparedStatement stmtDiminuiEstoque = null;
        ResultSet rs = null;
        int quantidade = 0;
        String cnpjFarmacia = null;

        boolean originalAutoCommit = this.connection.getAutoCommit();
        try {
            this.connection.setAutoCommit(false);

            stmtSelect = connection.prepareStatement(sqlSelectQuantidade);
            stmtSelect.setInt(1, idCompra);
            stmtSelect.setInt(2, idProduto);
            rs = stmtSelect.executeQuery();

            if (rs.next()) {
                quantidade = rs.getInt("quantidade");
                cnpjFarmacia = rs.getString("cnpjFarmacia");
            } else {
                System.err.println("Produto " + idProduto + " não encontrado na compra " + idCompra + ".");
                return false;
            }

            stmtDelete = connection.prepareStatement(sqlDeleteProdutosCompra);
            stmtDelete.setInt(1, idCompra);
            stmtDelete.setInt(2, idProduto);
            int affectedRowsDelete = stmtDelete.executeUpdate();

            stmtDiminuiEstoque = connection.prepareStatement(sqlDiminuiEstoque);
            stmtDiminuiEstoque.setDouble(1, quantidade);
            stmtDiminuiEstoque.setInt(2, idProduto);
            stmtDiminuiEstoque.setString(3, cnpjFarmacia);
            int affectedRowsEstoque = stmtDiminuiEstoque.executeUpdate();

            if (affectedRowsDelete > 0 && affectedRowsEstoque > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            this.connection.rollback();
            System.err.println("Erro ao remover produto da compra e diminuir estoque: " + e.getMessage());
            throw e;
        } finally {
            try {
                if (stmtSelect != null) stmtSelect.close();
                if (stmtDelete != null) stmtDelete.close();
                if (stmtDiminuiEstoque != null) stmtDiminuiEstoque.close();
                if (rs != null) rs.close();
                this.connection.setAutoCommit(originalAutoCommit);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatements ou ResultSet ou resetar autoCommit: " + e.getMessage());
                throw e; // Relança para garantir que o erro seja propagado
            }
        }
    }
}