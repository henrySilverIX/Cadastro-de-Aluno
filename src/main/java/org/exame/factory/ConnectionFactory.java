package org.exame.factory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    //Senha do workbench: 2#J5E8@s*8$WgokH
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost/secondExam", "root", "2#J5E8@s*8$WgokH");
        }
        catch(SQLException excecao){
            throw new RuntimeException(excecao);
        }
    }
}