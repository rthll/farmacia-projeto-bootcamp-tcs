package Projeto;

public class Farmacia {
    private String nome;
    private String cnpj;
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
        return cnpj;
    }

    public Farmacia(String nome, String cnpj, int id) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.id = id;
    }
}