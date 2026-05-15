package com.feitv.dao;

import com.feitv.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public void cadastrar(Usuario usuario) {

        String sql =
                "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try {

            Connection con = Conexao.conectar();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            stmt.execute();
            stmt.close();

            System.out.println("Usuário cadastrado com sucesso!");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public boolean login(String email, String senha) {

        String sql =
                "SELECT * FROM usuario WHERE email = ? AND senha = ?";

        try {

            Connection con = Conexao.conectar();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return true;

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
}