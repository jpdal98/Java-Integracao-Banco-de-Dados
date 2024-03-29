package br.com.alura.bytebank.domain.dao;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;
import br.com.alura.bytebank.domain.conta.Conta;
import br.com.alura.bytebank.domain.conta.DadosAberturaConta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private Connection conn;

    public ContaDAO(Connection connection){
        this.conn = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta) {
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), cliente, BigDecimal.ZERO, true);

        String sql = "INSERT INTO conta(numero, saldo, cliente_nome, cliente_cpf, cliente_email, esta_ativo)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //primeiro parametro serve para definir a que coluna o segundo parametro pertence
            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, dadosDaConta.dadosCliente().nome());
            preparedStatement.setString(4, dadosDaConta.dadosCliente().cpf());
            preparedStatement.setString(5, dadosDaConta.dadosCliente().email());
            preparedStatement.setBoolean(6, Boolean.TRUE);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        }catch (
                SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listarContas(){
        Set<Conta> contas = new HashSet<>();
        String sql = "SELECT * FROM conta WHERE esta_ativo = true";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Integer numeroConta = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String clienteNome = resultSet.getString(3);
                String clienteCpf = resultSet.getString(4);
                String clienteEmail = resultSet.getString(5);
                Boolean estaAtiva = resultSet.getBoolean(6);
                Cliente cliente = new Cliente(new DadosCadastroCliente(clienteNome, clienteCpf, clienteEmail));
                contas.add(new Conta(numeroConta, cliente, saldo, estaAtiva));
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }catch (
                SQLException e){
            throw new RuntimeException(e);
        }
        return contas;
    }

    public Conta buscarConta(Integer numero){
        Conta conta = null;
        String sql = "SELECT * FROM conta WHERE numero = ? AND esta_ativo = true";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer numeroConta = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String clienteNome = resultSet.getString(3);
                String clienteCpf = resultSet.getString(4);
                String clienteEmail = resultSet.getString(5);
                Boolean estaAtiva = resultSet.getBoolean(6);
                Cliente cliente = new Cliente(new DadosCadastroCliente(clienteNome, clienteCpf, clienteEmail));
                conta = new Conta(numeroConta, cliente, saldo, estaAtiva);
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }catch (
                SQLException e){
            throw new RuntimeException(e);
        }
        return conta;
    }

    public void alterar(Integer numeroDaConta, BigDecimal valor){
        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";
        try{
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, valor);
            preparedStatement.setInt(2, numeroDaConta);
            preparedStatement.execute();

            preparedStatement.execute();
            conn.commit();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void encerrarLogico(Integer numeroDaConta){
        String sql = "UPDATE conta SET esta_ativo = false WHERE numero = ?";
        try{
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, numeroDaConta);
            preparedStatement.execute();

            preparedStatement.execute();
            conn.commit();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void ativarLogico(Integer numeroDaConta){
        String sql = "UPDATE conta SET esta_ativo = true WHERE numero = ?";
        try{
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, numeroDaConta);
            preparedStatement.execute();

            preparedStatement.execute();
            conn.commit();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer numeroDaConta){
        String sql = "DELETE FROM conta WHERE numero = ?";
        try{
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, numeroDaConta);
            preparedStatement.execute();

            preparedStatement.execute();
            conn.commit();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
