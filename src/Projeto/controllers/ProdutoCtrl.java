package projeto.controllers;

import models.Produto;
import models.Funcionario;

public class ProdutoCtrl {
    SessaoDAO dao;
    ProdutoDAO daoProduto;

    public ProdutoCtrl() {
        dao = new SessaoDAO;
        daoProduto = new ProdutoDAO;
    }

    public boolean registrarProduto(String nomeProduto, int idProduto, double valorVenda, double valorCusto, int qtdProduto) {
     
        
        if (nomeProduto == null || nomeProduto.isEmpty() || 
        valorVenda == 0 ||  valorCusto == 0 ||
        qtdProduto == 0){
            
            return false;
            
        }
        
        Produto produto = new Produto(dao.getCnpjLogado, nomeProduto, idProduto, valorVenda, valorCusto);

            return daoProduto.criarProduto(produto);
            
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
