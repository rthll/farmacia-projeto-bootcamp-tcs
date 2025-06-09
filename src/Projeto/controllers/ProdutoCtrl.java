package Controllers;

import Models.Farmacia;
import Models.Produto;
import dao.ProdutoDAO;

public class ProdutoCtrl {
    private ProdutoDAO dao;

    public ProdutoCtrl() {
        this.dao = new ProdutoDAO();
    }

    public void registrarProduto(String nomeProduto, double valorVenda, double valorCusto) {
        Produto produto = new Produto(Sessao.getCnpjFarmaciaLogada(), nomeProduto, idProduto, valorVenda, valorCusto);
        dao.criarProduto(produto);
    }
    public double calcularValorEstoque(int idProduto) {
        try{
        double valorEstoque = daoProduto.somarProdutos(idProduto);
        return valorEstoque;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
