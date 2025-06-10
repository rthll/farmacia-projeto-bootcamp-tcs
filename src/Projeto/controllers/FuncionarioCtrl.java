package Projeto.controllers;

import java.util.List;
import Projeto.models.Funcionario;
import Projeto.models.Setor;
import Projeto.models.Transportadora;
import Projeto.utils.ValidadorCNPJ;
import Projeto.models.Genero;

public class FuncionarioCtrl {
    
    private FuncionarioDAO dao;

    public FuncionarioCtrl(String nome, String cnpj) {

        this.dao = new FuncionarioDAO();

    }
    
    public boolean salvar(Funcionario Funcionario) {

        if (Funcionario.getNome() == null || Funcionario.getNome().isEmpty() || 
            Funcionario.getIdade() == 0 ||  Funcionario.getSalario() == 0 ){

                return false;

            }

            return dao.adicionar(Funcionario);


            
    }


    public void cadastrar(String nome, int idade, double salario, Genero genero, int idSetor) {
        
        Funcionario Funcionario = new Funcionario(nome, idade, salario, genero, idSetor);

    }


    public double getImposto(Funcionario Funcionario) {
        if (Funcionario.getSalario() <= 2428.80 && Funcionario.getSalario() > 0) {
            return 0;
        }else if (Funcionario.getSalario() <= 2826.65 && Funcionario.getSalario() > 2428.80){
            return Funcionario.getSalario() * 0.075;
        } else if (Funcionario.getSalario() <=3751.05  && Funcionario.getSalario() > 2826.65) {
            return Funcionario.getSalario() * 0.15;
        } else if (Funcionario.getSalario() <=4664.68  && Funcionario.getSalario() > 3751.05) {
            return Funcionario.getSalario() * 0.225;
        } else if (Funcionario.getSalario() > 4664.68) {
            return Funcionario.getSalario() * 0.275;
        }else {
            return 0;
        }
    }

    public double gerSalarioLiquido(Funcionario Funcionario) {
        return Funcionario.getSalario() - getImposto(Funcionario);
    }


    public boolean apagar(int idFuncionario) {
     
        return dao.remover(idFuncionario);

    }
}    




