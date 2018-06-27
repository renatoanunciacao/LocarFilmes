/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AutorizacaoDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.modelo.Autorizacao;
import br.edu.ifsul.modelo.Pessoa;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Renato
 */
@Named(value = "controlePessoa")
@ViewScoped
public class ControlePessoa implements Serializable{
    @EJB
    private PessoaDAO<Pessoa> dao;
    private Pessoa objeto;
    private Boolean editando;
    @EJB
    private AutorizacaoDAO<Autorizacao> daoAutorizacao;
    private Autorizacao autorizacao;
    private Boolean editandoAutorizacao;
    
    public ControlePessoa(){
        editando = false;
    }
    
    public String listar() {
        setEditando((Boolean) false);
        return "/privado/pessoa/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        editandoAutorizacao = false;
        objeto = new Pessoa();
    }
    
    public void alterar(Object id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoAutorizacao = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: "
                    + Util.getMensagemErro(e));
        }
    }

    public void excluir(Object id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remover(objeto);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover o objeto: " + Util.getMensagemErro(e));
        }
    }

    public void salvar() {
        try {
            if (objeto.getCodigo() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            editando = false;
            Util.mensagemInformacao("Objeto persistido com sucesso");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir o objeto: " + Util.getMensagemErro(e));
        }
    }

    public void novaAutorizacao() {
        editandoAutorizacao = true;
    }

    public void salvarAutorizacao() {
        if (objeto.getAutorizacoes().contains(autorizacao)) {
            Util.mensagemErro("Pessoa já possui está autorização");
        } else {
            objeto.getAutorizacoes().add(autorizacao);
            Util.mensagemInformacao("Autorização adicionada com sucesso");
        }
        editandoAutorizacao = false;
    }

    public void removerAutorizacao(Autorizacao obj){
        objeto.getAutorizacoes().remove(obj);
        Util.mensagemInformacao("Autorização removida com sucesso");
    }
    
    public PessoaDAO<Pessoa> getDao() {
        return dao;
    }

    public void setDao(PessoaDAO<Pessoa> dao) {
        this.dao = dao;
    }

    public Pessoa getObjeto() {
        return objeto;
    }

    public void setObjeto(Pessoa objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public AutorizacaoDAO<Autorizacao> getDaoAutorizacao() {
        return daoAutorizacao;
    }

    public void setDaoAutorizacao(AutorizacaoDAO<Autorizacao> daoAutorizacao) {
        this.daoAutorizacao = daoAutorizacao;
    }

    public Autorizacao getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(Autorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    public Boolean getEditandoAutorizacao() {
        return editandoAutorizacao;
    }

    public void setEditandoAutorizacao(Boolean editandoAutorizacao) {
        this.editandoAutorizacao = editandoAutorizacao;
    }
    
    
    
    
}
