package Projeto.models;

public class Produto {
    
    private String cnpjFarmacia;
    private String nomeProduto;
    private int idProduto;
    private double qtdProduto;
    private double valorVenda;
    private double valorCusto;


    public Produto() {
        this.idProduto = 0;
        this.qtdProduto = 0;
        this.valorVenda = 0;
        this.valorCusto = 0;
    }

    public String getCnpjFarmacia() {

        return cnpjFarmacia;

    }

    public void nomeProduto(String nomeProduto) {
        
        this.nomeProduto = nomeProduto;

    }

    public String getNomeProduto() {

        return nomeProduto;
    }

    public void setIdProduto(int idProduto) {
        
        this.idProduto = idProduto;

    }

    public int getIdProduto() {

        return idProduto;

    }

    public void setQtdProduto(double qtdProduto) {
        
        this.qtdProduto = qtdProduto;

    }
    public double getQtdProduto() {
     
        return qtdProduto;

    }

    public void setValorVenda(double valorVenda) {
     
        this.valorVenda = valorVenda;

    }

    public double getValorVenda() {

        return valorVenda;

    }

    public void setValorCusto(double valorCusto) {

        this.valorCusto = valorCusto;

    }

    public double getValorCusto() {
     
        return valorCusto;
        
    }


}
