package br.com.alura.bytebank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    public static void main (String... x){

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bytebank",
                    "postgres",
                    "postgres");

            System.out.println("recuperei a conexão");
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
