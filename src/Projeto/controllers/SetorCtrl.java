package Projeto.controllers;

import Projeto.models.Setor;

public class SetorCtrl {

    SessaoDAO dao;

    public SetorCtrl() {
        dao = new SessaoDAO;
    }

    public void cadastrarSetor(String nomeSetor, double valeTransporte, double valeRefeicao, double valeAlimentacao, double planoSaude) {
        try {
            Setor setor = new Setor(Sessao.getCnpjLogado(),nomeSetor,valeTransporte,  valeRefeicao,  valeAlimentacao, planoSaude);
            dao.criarSetor(setor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
