package Projeto.controllers;

import java.util.List;
import Projeto.models.Farmacia;
import Projeto.models.Setor;
import Projeto.models.Transportadora;
import Projeto.utils.ValidadorCNPJ;
import Projeto.dtos.*;

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

        return dao.listarSetoresCnpj(Sessao.getCnpjFarmacia());

    }

    public List<Transportadora> listaTransportadoras() {

        return dao.listarTransportadorasCnpj(Sessao.getCnpjFarmacia());

    }

    public List<CompraListagemDTO> listarCompras() {

        return dao.listarComprasCnpj(Sessao.getCnpjFarmacia());

    }

    public List<VendaListagemDTO> listarVendas() {

        return dao.listarVendasCnpj(Sessao.getCnpjFarmacia());
        
    }

    public List<LucroMensalDTO> listarLucrosMensais() {

        return dao.listarLucrosMensaisCnpj(Sessao.getCnpjFarmacia());

    }

    
}    




