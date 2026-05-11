package com.feitv.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/FEITV";

    private static final String USER = "postgres";

    private static final String PASSWORD = "ivy";

    public static Connection conectar() throws Exception {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );

    }
}