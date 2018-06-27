/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Renato
 * @param <TIPO>
 */
public class DAOGenerico<TIPO> implements Serializable {

    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;
    @PersistenceContext(unitName = "LOCARFILMES-WEBPU")
    protected EntityManager em;
    protected Class classePersistente;
    protected String mensagem = "";
    protected String ordem;
    protected String filtro = "";
    protected Integer maximoObjetos = 5;
    protected Integer posicaoAtual = 0;
    protected Integer totalObjetos = 0;

    public DAOGenerico() {

    }

    public List<TIPO> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";
        //remove ';- para proteger de injeção SQL
        filtro = filtro.replaceAll("[-;']", "");
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";

                } catch (Exception e) {

                }
            } else {
                where += " where upper(" + ordem + ") like '%" + filtro.toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        System.out.println("JPQL: " + jpql);
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).setFirstResult(posicaoAtual).setMaxResults(maximoObjetos).getResultList();
    }

    public void primeiro() {
        posicaoAtual = 0;
    }

    public void anterior() {
        posicaoAtual -= totalObjetos;
        if (posicaoAtual < 0) {
            posicaoAtual = 0;
        }
    }

    public void proximo() {
        if (posicaoAtual + maximoObjetos < totalObjetos) {
            posicaoAtual += maximoObjetos;
        }
    }

    public void ultimo() {
        int resto = totalObjetos % maximoObjetos;
        if (resto > 0) {
            posicaoAtual = totalObjetos - resto;
        } else {
            posicaoAtual = totalObjetos - maximoObjetos;
        }
    }

    public String getMensagemNavegacao() {
        int ate = posicaoAtual + maximoObjetos;
        if (ate > totalObjetos) {
            ate = totalObjetos;
        }
        return "Listando de " + (posicaoAtual + 1) + " até " + ate + " de " + totalObjetos + " registros";
    }

    public List<TIPO> getListaTodos() {
        String jpql = "from " + classePersistente.getSimpleName() + " order by " + ordem;
        return em.createQuery(jpql).getResultList();
    }

    public void persist(TIPO obj) throws Exception {
        em.persist(obj);
    }

    public void merge(TIPO obj) throws Exception {
        em.merge(obj);
    }

    public TIPO getObjectById(Object id) throws Exception {
        return (TIPO) em.find(classePersistente, id);
    }

   // @RolesAllowed("ADMINISTRADOR")
    public void remover(TIPO obj) throws Exception {
        obj = em.merge(obj);
        em.remove(obj);
    }

    public void setLisaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public void setListaTodos(List<TIPO> listaTodos) {
        this.listaTodos = listaTodos;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }

    public void setClassePersistente(Class classePersistente) {
        this.classePersistente = classePersistente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }
}
