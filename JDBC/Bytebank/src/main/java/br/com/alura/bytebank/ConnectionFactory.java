package br.com.alura.bytebank;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection recuperarConexao (){

        try {
            return createDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/byte_bank");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}
