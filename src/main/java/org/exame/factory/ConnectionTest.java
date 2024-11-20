package org.exame.factory;

import java.sql.Connection;

public class ConnectionTest {
    public static void main(String[] args) throws Exception {
        Connection test = new ConnectionFactory().getConnection();
        System.out.println("Conexão aberta!");
        test.close();
    }
}
