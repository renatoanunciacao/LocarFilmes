/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Pessoa;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Renato
 */
@Stateful
public class PessoaDAO<TIPO> extends DAOGenerico<Pessoa> implements Serializable {

    public PessoaDAO() {
        super();
        classePersistente = Pessoa.class;
        ordem = "nome";
        maximoObjetos = 3;
    }

    @Override
    public Pessoa getObjectById(Object codigo) throws Exception {
        Pessoa obj = em.find(Pessoa.class, codigo);
        /**
         * Alinha obj.getAutorizações
         */
        obj.getAutorizacoes().size();
        return obj;
    }

    public Pessoa localizarPorNomeUsuario(String nomePessoa) {
        Query query = em.createQuery("from Pessoa where upper(nickname) =:nomePessoa");
        query.setParameter("nomePessoa", nomePessoa.toUpperCase());
        Pessoa obj = (Pessoa) query.getSingleResult();
        obj.getAutorizacoes().size();
        return obj;
    }
}
