package br.com.alura.loja.dao;

import br.com.alura.loja.model.Cliente;

import javax.persistence.EntityManager;

public class ClienteDAO {
    private EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Cliente cliente){
        this.em.persist(cliente);
    }

    public Cliente buscarPorId(Long id){
        return this.em.find(Cliente.class, id);
    }

}
