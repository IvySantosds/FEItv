package com.feitv;

import com.feitv.dao.UsuarioDAO;
import com.feitv.model.Usuario;

public class TesteLogin {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.login("admin@feitv.com", "123456");

        if (user != null) {
            System.out.println("Login com sucesso! Usuário: " + user.getNome());
            System.out.println("É administrador? " + user.isAdmin());
        } else {
            System.out.println("Falha no login!");
        }
    }
}