package Projeto.models;

import java.util.EnumSet;

public class Transportadora {

    private String cnpjFarmacia;
    private String nome;
    private EnumSet<Local> locaisAtendidos;

    public String getCnpjFarmacia() {
        return cnpjFarmacia;
    }

    public Transportadora() {
        locaisAtendidos = EnumSet.noneOf(Local.class);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EnumSet<Local> getLocaisAtendidos() {
        return locaisAtendidos;
    }

    public void setLocaisAtendidos(EnumSet<Local> locaisAtendidos) {
        this.locaisAtendidos = locaisAtendidos;
    }

}
