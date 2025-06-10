package Projeto.dtos;

import java.time.LocalDate;

public class VendaListagemDTO {
    
    private int idVenda;
    private String nomeFuncionario;
    private LocalDate dataVenda;
    private int qtdProdutos;
    private double valorFinal;


    public VendaListagemDTO() {

        this.idVenda = 0;
        this.nomeFuncionario = "";
        this.dataVenda = null;
        this.qtdProdutos = 0;
        this.valorFinal = 0;

    }


    public void setIdVenda(int idVenda) {

        this.idVenda = idVenda;

    }

    public int getIdVenda() {

        return idVenda;

    }

    public void setNomeFuncionario(String nomeFuncionario) {

        this.nomeFuncionario = nomeFuncionario;

    }

    public String getNomeFuncionario() {

        return nomeFuncionario;

    }

    public void setDataVenda(LocalDate dataVenda) {

        this.dataVenda = dataVenda;

    }

    public LocalDate getDataVenda() {

        return dataVenda;

    }

    public void setQtdProdutos(int qtdProdutos) {

        this.qtdProdutos = qtdProdutos;

    }

    public int getQtdProdutos() {
        
        return qtdProdutos;

    }

    public void setValorFinal(double valorFinal) {

        this.valorFinal = valorFinal;

    }

    public double getValorFinal() {

        return valorFinal;
        
    }

}
