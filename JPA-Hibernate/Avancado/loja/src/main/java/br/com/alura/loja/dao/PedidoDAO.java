package br.com.alura.loja.dao;

import br.com.alura.loja.model.Pedido;

import javax.persistence.EntityManager;

public class PedidoDAO {

    private EntityManager em;

    public PedidoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido){
        this.em.persist(pedido);
    }
}
