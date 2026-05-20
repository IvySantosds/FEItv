package com.feitv.dao;

import com.feitv.model.Video;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO {

    public void cadastrar(Video v) {
        String sql = "INSERT INTO videos (titulo, descricao, url_video, id_admin) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, v.getTitulo());
            stmt.setString(2, v.getDescricao());
            stmt.setString(3, v.getUrl());
            stmt.setInt(4, v.getIdAdmin());
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Video> listar() {
        return buscarPorNome("");
    }

    public List<Video> buscarPorNome(String nome) {
        List<Video> lista = new ArrayList<>();
        String sql = """
            SELECT v.id_video, v.titulo, v.descricao, v.url_video,
                   COUNT(CASE WHEN c.tipo = 'like' THEN 1 END) AS curtidas
            FROM videos v
            LEFT JOIN curtidas c ON v.id_video = c.id_video
            WHERE v.titulo ILIKE ?
            GROUP BY v.id_video, v.titulo, v.descricao, v.url_video
            ORDER BY v.id_video
        """;
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Video v = new Video();
                v.setId(rs.getInt("id_video"));
                v.setTitulo(rs.getString("titulo"));
                v.setDescricao(rs.getString("descricao"));
                v.setUrl(rs.getString("url_video"));
                v.setCurtidas(rs.getInt("curtidas"));
                lista.add(v);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<Video> buscarPorPlaylist(int idLista) {
        List<Video> lista = new ArrayList<>();
        String sql = """
            SELECT v.id_video, v.titulo, v.url_video
            FROM videos v
            INNER JOIN lista_videos lv ON v.id_video = lv.id_video
            WHERE lv.id_lista = ?
            ORDER BY v.id_video
        """;
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idLista);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Video v = new Video();
                v.setId(rs.getInt("id_video"));
                v.setTitulo(rs.getString("titulo"));
                v.setUrl(rs.getString("url_video"));
                lista.add(v);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void curtirVideo(int idVideo, int idUsuario) {
        String sql = """
            INSERT INTO curtidas (id_user, id_video, tipo)
            VALUES (?, ?, 'like')
            ON CONFLICT (id_user, id_video)
            DO UPDATE SET tipo = 'like'
        """;
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idVideo);
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void descurtirVideo(int idVideo, int idUsuario) {
        String sql = """
            INSERT INTO curtidas (id_user, id_video, tipo)
            VALUES (?, ?, 'dislike')
            ON CONFLICT (id_user, id_video)
            DO UPDATE SET tipo = 'dislike'
        """;
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idVideo);
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void excluir(int id) {
        String deletaCurtidas  = "DELETE FROM curtidas    WHERE id_video = ?";
        String deletaLista     = "DELETE FROM lista_videos WHERE id_video = ?";
        String deletaVideo     = "DELETE FROM videos       WHERE id_video = ?";

        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement s = con.prepareStatement(deletaCurtidas)) {
                    s.setInt(1, id); s.execute();
                }
                try (PreparedStatement s = con.prepareStatement(deletaLista)) {
                    s.setInt(1, id); s.execute();
                }
                try (PreparedStatement s = con.prepareStatement(deletaVideo)) {
                    s.setInt(1, id); s.execute();
                }
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void registrarHistorico(int idUsuario, int idVideo) {
        System.out.println("Usuário " + idUsuario + " assistiu vídeo " + idVideo);
    }
}