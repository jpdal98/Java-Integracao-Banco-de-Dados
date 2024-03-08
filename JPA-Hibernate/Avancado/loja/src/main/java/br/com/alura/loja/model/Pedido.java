package br.com.alura.loja.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data = LocalDate.now();

    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;

    // FetchType.LAZY serve para não trazer as entidades que estão relacionadas, a n ser que eu queira acessa-la
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    // o atributo cascade serve para propagar as operações realizadas em uma entidade e seu relacionamento.
    // neste caso, como a existencia de um item_pedido depende de um objeto pedido, usamos o cascade
    // para determinar que, tudo o que acontecer com o pedido deve acontecer ao item_pedido
    @OneToMany(mappedBy = "pedido", cascade=CascadeType.ALL)
    private List<ItemPedido> itemPedidos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionarPedido(ItemPedido item){
        item.setPedido(this);
        this.itemPedidos.add(item);
        this.valorTotal = this.valorTotal.add(item.getValor());
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido pedido)) return false;
        return Objects.equals(id, pedido.id) && Objects.equals(data, pedido.data) && Objects.equals(cliente, pedido.cliente) && Objects.equals(valorTotal, pedido.valorTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, cliente, valorTotal);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", data=" + data +
                ", valorTotal=" + valorTotal +
                ", cliente=" + cliente +
                ", itemPedidos=" + itemPedidos +
                '}';
    }
}
