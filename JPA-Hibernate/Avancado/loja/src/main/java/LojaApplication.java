import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.model.*;
import br.com.alura.loja.util.JPAutil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class LojaApplication {

    public static void main (String[] args){
        populandoBaseDeDados();
        //testeProdutoConsultas();
        testeCadastroPedido();
        testeConsultasAvancadas();
    }

    private static void populandoBaseDeDados(){
        EntityManager em = JPAutil.getEntityManager();
        Categoria celulares = new Categoria("Celulares");
        Categoria videoGames = new Categoria("Video Games");
        Categoria computadores = new Categoria("Computadores");
        Produto celular = new Produto(
                "Xiaomi Readmi 8", "top", new BigDecimal("800"), celulares);
        Produto videoGame = new Produto(
                "Playstation 5", "top", new BigDecimal("3200"), videoGames);
        Produto computador = new Produto(
                "Macbook pro ", "top", new BigDecimal("6000"), computadores);
        Cliente cliente = new Cliente("João Pedro", "074.839.423-04");

        ClienteDAO clienteDAO = new ClienteDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ProdutoDAO produtoDAO = new ProdutoDAO(em);

        em.getTransaction().begin();
        categoriaDAO.cadastrar(celulares);
        categoriaDAO.cadastrar(videoGames);
        categoriaDAO.cadastrar(computadores);
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
        celulares.setNome("Celulares");
        em.flush();

        //metodo remove() da JPA serve para excluir um objeto que antes havia sido salvo
        //do banco de dados
        //em.remove(celulares);
        //em.flush();

        produtoDAO.cadastrar(celular);
        produtoDAO.cadastrar(videoGame);
        produtoDAO.cadastrar(computador);

        celular.setNome("Xiaomi readmi 12");
        em.flush();

        clienteDAO.cadastrar(cliente);

        //metodo commit() da JPA permite voce sincronizar os dados com o banco, e salva-los,
        // contudo, diferente do metodo flush(), apos salvar as modificações ele finaliza
        // a transação, impedindo de se realizar novas transações ou
        // rollback()
        em.getTransaction().commit();
        em.close();
    }

    private static void testeProdutoConsultas(){
        EntityManager em = JPAutil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        Produto p = produtoDAO.buscarPorId(1L);
        System.out.println(p.getPreco());

        List<Produto> produtos = produtoDAO.buscarProdutos();
        produtos.forEach(p2 -> System.out.println(p.getNome()));

        produtos = produtoDAO.buscarPorNome("Xiaomi readmi 12");
        produtos.forEach(p2 -> System.out.println(p.getNome()));

        produtos = produtoDAO.buscarPorNomeDaCategoria("Celulares");
        produtos.forEach(p2 -> System.out.println(p.getNome()));

        BigDecimal precoProduto = produtoDAO.buscarPrecoPorNomeDoProduto("Xiaomi readmi 12");
        System.out.println("Preço do produto: " + precoProduto);
        em.close();
    }

    private static void testeCadastroPedido(){
        EntityManager em = JPAutil.getEntityManager();

        ClienteDAO  clienteDAO = new ClienteDAO(em);
        Cliente cliente = clienteDAO.buscarPorId(1L);
        ProdutoDAO  produtoDAO = new ProdutoDAO(em);
        Produto produto1 = produtoDAO.buscarPorId(1L);
        Produto produto2 = produtoDAO.buscarPorId(2L);
        Produto produto3 = produtoDAO.buscarPorId(3L);
        Pedido pedido1 = new Pedido(cliente);
        Pedido pedido2 = new Pedido(cliente);
        Pedido pedido3 = new Pedido(cliente);
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedido1.adicionarPedido(new ItemPedido(10, pedido1, produto1));
        pedido2.adicionarPedido(new ItemPedido(5, pedido2, produto2));
        pedido3.adicionarPedido(new ItemPedido(8, pedido3, produto3));

        em.getTransaction().begin();

        pedidoDAO.cadastrar(pedido1);
        pedidoDAO.cadastrar(pedido2);
        pedidoDAO.cadastrar(pedido3);

        em.getTransaction().commit();
        em.close();
    }

    private static void testeConsultasAvancadas(){
        EntityManager em = JPAutil.getEntityManager();

        PedidoDAO pedidoDAO = new PedidoDAO(em);


        em.getTransaction().begin();

        System.out.println("Valor total: " + pedidoDAO.valorTotalVendido());
        List<RelatorioDeVendasVo> relatorios = pedidoDAO.relatorioDeVendas();
        relatorios.forEach(System.out::println);

        em.getTransaction().commit();
        em.close();
    }
}
