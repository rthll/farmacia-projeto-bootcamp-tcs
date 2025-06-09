package projeto.models;


import java.util.ArrayList;

public class MovimentoCaixa {
    private String cnpjFarmacia;

    private ArrayList<Venda> vendas = new ArrayList<Venda>();
    private ArrayList<Compra> compras = new ArrayList<Compra>();

    public String getCnpjFarmacia() {
        return cnpjFarmacia;
    }

    public MovimentoCaixa(String cnpjFarmacia, ArrayList<Venda> vendas, ArrayList<Compra> compras) {
        this.cnpjFarmacia = cnpjFarmacia;
        this.vendas = vendas;
        this.compras = compras;
    }
}
