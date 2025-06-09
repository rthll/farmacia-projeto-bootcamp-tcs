package projeto.controllers;

import projeto.models.Compra;
import projeto.models.Produto;

public class ComprasCtrl {
    private double valorCompra;
    private double qtdCompra;
    private double totalCompra;
    SessaoDAO dao;
    ProdutoDAO daoProduto;

    public double getValorCompra() {
        return valorCompra;
    }
    public double getQtdCompra() {
        return qtdCompra;
    }
    public void setValorCompra(double valorCompra) {
        this.valorCompra = produto.getValorCusto();
    }
    public void setQtdCompra(double qtdVenda) {
        this.qtdCompra = qtdVenda;
    }
    Produto produto = new Produto();

    public void setTotalCompra(){
        this.totalCompra = qtdCompra * valorCompra;
    }

    public double getTotalCompra () {
        return totalCompra;
    }

    public comprasCtrl(){
        dao = new SessaoDAO();
        daoProduto = new ProdutoDao();
    }

    

}
