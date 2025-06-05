package projeto.models;

public class Funcionario {

    private String nome;
    private int id;
    private int idade;
    private Genero genero;
    private double salario;
    private String cnpjFarmacia;


    public Funcionario() {

        this.nome = "";
        this.id = 0;
        this.idade = 0;
        this.genero = null;
        this.salario = 0;
        
    }
    
    public String getCnpjFarmacia() {
        
        return cnpjFarmacia;

    }

    public void setNome(String nome) {
        
        this.nome = nome;
        
    }

    public String getNome() {
        
        return nome;

    }

    public void setID(int id) {

        this.id = id;

    }

    public int getID() {

        return id;

    }

    public void setIdade(int idade) {

        this.idade = idade;

    }

    public int getIdade() {

        return idade;

    }

    public void setGenero(Genero genero) {

        this.genero = genero;

    }


    public Genero getGenero() {

        return genero;

    }

    public void setSalario(double salario) {
    
        this.salario = salario;

    }

    public double getSalario() {

        return salario;

    }




    
}