package dao;

import models.Farmacia;
import java.sql.*;
import java.util.ArrayList;

public class FarmaciaDAO {
    static Connection con = null;
    static String url = "jdbc:postgresql://localhost:5432/smartcondo"; // atualizar banco
    static String driver = "org.postgresql.Driver";
    static String usuario = "postgres";
    static String senha = "niver2500"; // atualizar senha

    public void adicionar(Farmacia farmacia) {
        Connection con = null;
        PreparedStatement p = null;

        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.out.println("Falha na criação");
        }

        try {
            con = DriverManager.getConnection(url, usuario, senha);
            String adiciona = "INSERT INTO Farmacia (nome, cnpj) VALUES (?, ?)";
            p = con.prepareStatement(adiciona);
            String cnpj = farmacia.getCnpj().replaceAll("[^0-9]", "");
            long cnpjLong = Long.parseLong(cnpj);
            p.setLong(1, cnpjLong);
            p.setString(2, farmacia.getNome());
            p.execute();
            p.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Falha na inserção: " + e.getMessage());
        }

    }

    public boolean verificarCNPJ(String cnpj) {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            con = DriverManager.getConnection(url, usuario, senha);
            String sql = "SELECT cnpj FROM Farmacia WHERE cnpj = ?";
            p = con.prepareStatement(sql);
            p.setLong(1, Long.parseLong(cnpj));
            rs = p.executeQuery();
            if (rs.next()) { 
                existe = true;
            }
            rs.close();
            p.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro na verificação do CNPJ em Farmácia: " + e.getMessage());
        }
        return existe;
    }

    /* 
    public boolean verificarCNPJ(String cnpj) {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            con = DriverManager.getConnection(url, usuario, senha);
            String adiciona = "INSERT INTO Farmacia (nome, cnpj) VALUES (?, ?)";
            p = con.prepareStatement(adiciona);

            String nome = farmacia.getNome();
            String cnpjLong = farmacia.getCnpj().replaceAll("[^0-9]", "");

            p.setString(1, nome);
            p.setString(2, cnpj);

            p.execute();
            p.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Falha na inserção: " + e.getMessage());
        }
        return existe;
    }
    */
    
    public ArrayList<Funcionario> consultarTodosFuncionarios() {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try {
            con = DriverManager.getConnection(url, usuario, senha);
            String sql = "SELECT p.nome, f.\"cargoFuncionario\", f.\"turnoFuncionario\" " +
                    "FROM \"Pessoa\" p " +
                    "JOIN \"Funcionario\" f ON p.cnpj = f.cnpj";
            p = con.prepareStatement(sql);
            rs = p.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCargoFuncionario(rs.getString("cargoFuncionario"));
                funcionario.setTurnoFuncionario(rs.getString("turnoFuncionario"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (p != null)
                    p.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return funcionarios;
    }

    public String consultarDados(String cnpj) {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;
        String dados = "";

        try {
            con = DriverManager.getConnection(url, usuario, senha);
            String sql = "SELECT p.cnpj, p.nome, p.endereco, p.telefone, p.email, f.\"cargoFuncionario\", f.\"turnoFuncionario\""
                    +
                    "FROM \"Pessoa\" p JOIN \"Funcionario\" f ON p.cnpj = f.cnpj " +
                    "WHERE p.cnpj = ?";
            p = con.prepareStatement(sql);
            p.setLong(1, Long.parseLong(cnpj));
            rs = p.executeQuery();

            if (rs.next()) {
                dados = "Nome: " + rs.getString("nome") + "\n"
                        + "cnpj: " + rs.getString("cnpj") + "\n"
                        + "Endereço: " + rs.getString("endereco") + "\n"
                        + "Telefone: " + rs.getString("telefone") + "\n"
                        + "Email: " + rs.getString("email") + "\n"
                        + "Função: " + rs.getString("cargoFuncionario") + "\n"
                        + "Turno: " + rs.getString("turnoFuncionario");
            }
            rs.close();
            p.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Erro ao verificar: " + e.getMessage());
        }

        return dados;
    }
}