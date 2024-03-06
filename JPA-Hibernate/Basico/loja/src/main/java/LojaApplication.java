import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.model.Produto;
import br.com.alura.loja.util.JPAutil;

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

        EntityManager em = JPAutil.getEntityManager();
        ProdutoDAO dao = new ProdutoDAO(em);

        em.getTransaction().begin();
        dao.cadastrar(celular);
        em.getTransaction().commit();
        em.close();

    }
}
