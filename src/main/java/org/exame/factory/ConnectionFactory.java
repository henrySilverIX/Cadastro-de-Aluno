package org.exame.factory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection(){
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost/secondExam", "root", "2#J5E8@s*8$WgokH");
        }
        catch(SQLException excecao){
            throw new RuntimeException(excecao);
        }
    }
}