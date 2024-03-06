import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.model.Categoria;
import br.com.alura.loja.model.Produto;
import br.com.alura.loja.util.JPAutil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class LojaApplication {

    public static void main (String[] args){


        EntityManager em = JPAutil.getEntityManager();
        Categoria celulares = new Categoria("Celulares");
        Produto celular = new Produto(
                "Xiaomi Readmi 8", "top", new BigDecimal("1300"), celulares);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ProdutoDAO produtoDAO = new ProdutoDAO(em);

        em.getTransaction().begin();
        categoriaDAO.cadastrar(celulares);
        celulares.setNome("Nokia");

        //metodo flush() da JPA permite sincronizar os dados com o banco, ele salva mas não efetua o commit,
        // ou seja, ainda é possivel realizar mais transações com o bando de dados
        em.flush();
        //metodo clear() da JPA apaga todas as alterações feitas nas entidades durante o estado managed, estado este
        // que a JPA consegue gerenciar as informações que seram passadas para o BD.
        em.clear();

        //metodo merge() da JPA permite com quem possamos recuperar a referencia de quando a entidade estava num
        // estado que podia ser gerenciado pela JPA
        celulares = em.merge(celulares);
        celulares.setNome("Xiaomi");
        em.flush();

        //metodo remove() da JPA serve para excluir um objeto que antes havia sido salvo
        //do banco de dados
        //em.remove(celulares);
        //em.flush();

        produtoDAO.cadastrar(celular);
        celular.setNome("Xiaomi readmi 12");
        em.flush();

        //metodo commit() da JPA permite voce sincronizar os dados com o banco, e salva-los,
        // contudo, diferente do metodo flush(), apos salvar as modificações ele finaliza
        // a transação, impedindo de se realizar novas transações ou
        // rollback()
        em.getTransaction().commit();
        em.close();

    }
}
