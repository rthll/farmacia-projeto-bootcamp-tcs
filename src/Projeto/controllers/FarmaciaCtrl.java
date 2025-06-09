package projeto.controllers;

import java.util.List;
import projeto.models.Farmacia;
import projeto.models.Setor;
import projeto.models.Transportadora;
import projeto.utils.ValidadorCNPJ;


public class FarmaciaCtrl {
    
    private FarmaciaDAO dao;

    public FarmaciaCtrl(String nome, String cnpj) {

        this.dao = new FarmaciaDAO();

    }
    
    public boolean salvar(Farmacia farmacia) {

        if (farmacia.getNome() == null || farmacia.getNome().isEmpty() || 
            farmacia.getCnpj() == null || farmacia.getCnpj().isEmpty() || 
            !ValidadorCNPJ.isValidCNPJ(farmacia.getCnpj())) {

             return false;

        }


            boolean sucesso = dao.adicionar(farmacia);
            
    }

    public boolean verificarFarmacia(String cnpjFarmacia) {

        return dao.verificarCnpj(cnpjFarmacia);
        
    }
    

    public void cadastrar(String nome, String cnpjFarmacia) {
        
        Farmacia farmacia = new Farmacia(nome, cnpjFarmacia);
        farmacia.setNome(nome);
        farmacia.setCnpj(cnpjFarmacia);
        
    
        boolean resultado = salvar(farmacia);
        System.out.println(resultado);

    }


    public List<Setor> listarSetores() {

        return dao.listarSetoresPorFarmacia(Sessao.getCnpjFarmacia());

    }

    public List<Transportadora> listaTransportadoras() {

        return dao.listarTransportadorasPorFarmacia(Sessao.getCnpjFarmacia());

    }

    public List<Farmacia> listarFarmacias() {

        return dao.listarFarmacias
        
    }

    
}    




