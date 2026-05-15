package com.feitv.dao;

import com.feitv.model.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    public void cadastrar(Playlist playlist)
            throws Exception {

        Connection conn =
                Conexao.conectar();

        String sql =
                "INSERT INTO playlist(nome, descricao) VALUES (?, ?)";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setString(1,
                playlist.getNome());

        stmt.setString(2,
                playlist.getDescricao());

        stmt.execute();

        stmt.close();

        conn.close();

    }

    public List<Playlist> listar()
            throws Exception {

        List<Playlist> lista =
                new ArrayList<>();

        Connection conn =
                Conexao.conectar();

        String sql =
                "SELECT * FROM playlist";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        ResultSet rs =
                stmt.executeQuery();

        while (rs.next()) {

            Playlist p =
                    new Playlist();

            p.setId(
                    rs.getInt("id"));

            p.setNome(
                    rs.getString("nome"));

            p.setDescricao(
                    rs.getString("descricao"));

            lista.add(p);

        }

        rs.close();

        stmt.close();

        conn.close();

        return lista;

    }

    public void atualizar(Playlist playlist)
            throws Exception {

        Connection conn =
                Conexao.conectar();

        String sql =
                "UPDATE playlist SET nome = ?, descricao = ? WHERE id = ?";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setString(1,
                playlist.getNome());

        stmt.setString(2,
                playlist.getDescricao());

        stmt.setInt(3,
                playlist.getId());

        stmt.executeUpdate();

        stmt.close();

        conn.close();

    }

    public void excluir(int id)
            throws Exception {

        Connection conn =
                Conexao.conectar();

        String sql =
                "DELETE FROM playlist WHERE id = ?";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setInt(1, id);

        stmt.executeUpdate();

        stmt.close();

        conn.close();

    }

}