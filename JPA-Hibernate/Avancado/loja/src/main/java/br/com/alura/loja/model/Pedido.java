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

    private Long id;

    private LocalDate data = LocalDate.now();

    private BigDecimal valorTotal;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedidos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Cliente cliente, BigDecimal valorTotal) {
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

    public void adicionarPedido(ItemPedido item){
        item.setPedido(this);
        this.itemPedidos.add(item);
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
