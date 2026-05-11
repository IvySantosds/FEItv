package com.feitv;

import com.feitv.dao.UsuarioDAO;

public class TesteLogin {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        boolean login =
                dao.login(
                        "lucas2@gmail.com",
                        "123"
                );

        if (login) {

            System.out.println("Login realizado com sucesso!");

        } else {

            System.out.println("Email ou senha incorretos!");

        }

    }
}