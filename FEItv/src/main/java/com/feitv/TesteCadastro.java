package com.feitv;

import com.feitv.dao.UsuarioDAO;
import com.feitv.model.Usuario;

public class TesteCadastro {

    public static void main(String[] args) {

        Usuario usuario = new Usuario();

        usuario.setNome("Lucas");
        usuario.setEmail("lucas2@gmail.com");
        usuario.setSenha("123");

        UsuarioDAO dao = new UsuarioDAO();

        dao.cadastrar(usuario);

    }
}