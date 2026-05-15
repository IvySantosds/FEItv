package com.feitv.dao;

import com.feitv.model.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class VideoDAO {

    public void cadastrar(Video video)
            throws Exception {

        Connection conn =
                Conexao.conectar();

        String sql =
                "INSERT INTO video(titulo, url, duracao, categoria) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setString(1,
                video.getTitulo());

        stmt.setString(2,
                video.getUrl());

        stmt.setString(3,
                video.getDuracao());

        stmt.setString(4,
                video.getCategoria());

        stmt.execute();

        stmt.close();

        conn.close();

    }

    public List<Video> listar()
            throws Exception {

        List<Video> lista =
                new ArrayList<>();

        Connection conn =
                Conexao.conectar();

        String sql =
                "SELECT * FROM video";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        ResultSet rs =
                stmt.executeQuery();

        while (rs.next()) {

            Video v =
                    new Video();

            v.setId(
                    rs.getInt("id"));

            v.setTitulo(
                    rs.getString("titulo"));

            v.setUrl(
                    rs.getString("url"));

            v.setDuracao(
                    rs.getString("duracao"));

            v.setCategoria(
                    rs.getString("categoria"));

            v.setCurtidas(
                    rs.getInt("curtidas"));

            lista.add(v);

        }

        rs.close();

        stmt.close();

        conn.close();

        return lista;

    }

    public void curtirVideo(int id)
            throws Exception {

        Connection conn =
                Conexao.conectar();

        String sql =
                "UPDATE video SET curtidas = curtidas + 1 WHERE id = ?";

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setInt(1, id);

        stmt.executeUpdate();

        stmt.close();

        conn.close();

    }
public void excluir(int id)
        throws Exception {

    Connection conn =
            Conexao.conectar();

    String sql =
            "DELETE FROM video WHERE id = ?";

    PreparedStatement stmt =
            conn.prepareStatement(sql);

    stmt.setInt(1, id);

    stmt.executeUpdate();

    stmt.close();

    conn.close();

}
}