import br.com.alura.loja.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class LojaApplication {

    public static void main (String[] args){

        Produto celular = new Produto();
        celular.setNome("Xiaomi Readmi 8");
        celular.setDescricao("top");
        celular.setPreco(new BigDecimal("1300"));

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("loja");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(celular);
        em.getTransaction().commit();
        em.close();
    }
}
