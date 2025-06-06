package Projeto.controllers;

import Projeto.models.Produto;

public class ProdutoCtrl {
    SessaoDAO dao;
    ProdutoDAO daoProduto;

    public ProdutoCtrl() {
        dao = new SessaoDAO;
        daoProduto = new ProdutoDAO;
    }

    public void registrarProduto(String nomeProduto, int idProduto, double valorVenda, double valorCusto) {
        try {
            Produto produto = new Produto(dao.getCnpjLogado, nomeProduto, idProduto, valorVenda, valorCusto);
            daoProduto.criarProduto(produto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public double calcularValorVenda(int idProduto) {
        try{
        double valorVenda = daoProduto.somarProdutos(idProduto);
        return valorVenda;} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
