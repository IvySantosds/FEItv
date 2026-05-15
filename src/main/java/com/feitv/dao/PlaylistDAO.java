package com.feitv.dao;

import com.feitv.model.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PlaylistDAO {

    public void cadastrar(Playlist playlist)
            throws Exception {

        Connection conexao =
                Conexao.conectar();

        String sql =
                "INSERT INTO playlist(nome, descricao) VALUES (?, ?)";

        PreparedStatement ps =
                conexao.prepareStatement(sql);

        ps.setString(1,
                playlist.getNome());

        ps.setString(2,
                playlist.getDescricao());

        ps.execute();

        ps.close();

        conexao.close();

    }

}