package Projeto.controllers;

import Projeto.models.Setor;

public class SetorCtrl {

    SessaoDAO dao;
    SetorDAO daoSetor;
    public SetorCtrl() {
        dao = new SessaoDAO;
        daoSetor = new SetorDAO
    }

    public void cadastrarSetor(String nomeSetor, double valeTransporte, double valeRefeicao, double valeAlimentacao, double planoSaude) {
        try {
            Setor setor = new Setor(dao.getCnpjLogado(),nomeSetor,valeTransporte,  valeRefeicao,  valeAlimentacao, planoSaude);
            daoSetor.criarSetor(setor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}