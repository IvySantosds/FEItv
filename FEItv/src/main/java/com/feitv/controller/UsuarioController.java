/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.feitv.controller;

import com.feitv.dao.UsuarioDAO;
import com.feitv.model.Usuario;

public class UsuarioController {

    public void cadastrar(Usuario usuario) {

        UsuarioDAO dao = new UsuarioDAO();

        dao.cadastrar(usuario);

    }

    public boolean login(String email, String senha) {

        UsuarioDAO dao = new UsuarioDAO();

        return dao.login(email, senha);

    }
}
