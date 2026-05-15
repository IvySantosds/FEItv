package com.feitv;

import com.feitv.dao.Conexao;
import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        try {

            Connection con = Conexao.conectar();

            System.out.println("Conectado com sucesso!");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}