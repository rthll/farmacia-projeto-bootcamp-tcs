package Projeto.controllers;

import java.util.List;
import Projeto.models.Funcionario;
import Projeto.models.Setor;
import Projeto.models.Transportadora;
import Projeto.utils.ValidadorCNPJ;


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


            boolean sucesso = dao.adicionar(Funcionario);
            
    }

    public boolean verificarFuncionario(String cnpjFuncionario) {

        return dao.verificarCnpj(cnpjFuncionario);
        
    }
    

    public void cadastrar(String nome, int idade, double salario) {
        
        Funcionario Funcionario = new Funcionario();
        Funcionario.setNome(nome);
        Funcionario.setCnpj(cnpjFuncionario);
        
    
        boolean resultado = salvar(Funcionario);
        System.out.println(resultado);

    }


    public List<Setor> listarSetores() {

        return dao.listarSetoresPorFuncionario(Sessao.getCnpjFuncionario());

    }

    public List<Transportadora> listaTransportadoras() {

        return dao.listarTransportadorasPorFuncionario(Sessao.getCnpjFuncionario());

    }

    public List<Funcionario> listarFuncionarios() {

        return dao.listarFuncionarios
        
    }

    
}    




