package Projeto.models;

public class Farmacia {
    private String nome;
    private String cnpjFarmacia;
    private int id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getCnpj() {
        return cnpjFarmacia;
    }

    public Farmacia(String nome, String cnpjFarmacia, int id) {
        this.nome = nome;
        this.cnpjFarmacia = cnpjFarmacia;
        this.id = id;
    }
}