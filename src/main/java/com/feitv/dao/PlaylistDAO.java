package com.feitv.dao;

import com.feitv.model.Playlist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    public void cadastrar(Playlist p) {
        String sql = "INSERT INTO lista_favoritos (nome, id_user) VALUES (?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdUsuario());
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void excluir(int id) {
        String deletaVideos = "DELETE FROM lista_videos    WHERE id_lista = ?";
        String deletaLista  = "DELETE FROM lista_favoritos WHERE id_lista = ?";
        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement s = con.prepareStatement(deletaVideos)) {
                    s.setInt(1, id); s.execute();
                }
                try (PreparedStatement s = con.prepareStatement(deletaLista)) {
                    s.setInt(1, id); s.execute();
                }
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Playlist> listar() {
        List<Playlist> lista = new ArrayList<>();
        String sql = "SELECT * FROM lista_favoritos ORDER BY id_lista";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Playlist p = new Playlist();
                p.setId(rs.getInt("id_lista"));
                p.setNome(rs.getString("nome"));
                p.setIdUsuario(rs.getInt("id_user"));
                lista.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<Playlist> listarPorUsuario(int idUsuario) {
        List<Playlist> lista = new ArrayList<>();
        String sql = "SELECT * FROM lista_favoritos WHERE id_user = ? ORDER BY id_lista";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Playlist p = new Playlist();
                p.setId(rs.getInt("id_lista"));
                p.setNome(rs.getString("nome"));
                p.setIdUsuario(rs.getInt("id_user"));
                lista.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void adicionarVideoAFavoritos(int idLista, int idVideo) {
        String sql = "INSERT INTO lista_videos (id_lista, id_video) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idLista);
            stmt.setInt(2, idVideo);
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void removerVideoDaPlaylist(int idLista, int idVideo) {
        String sql = "DELETE FROM lista_videos WHERE id_lista = ? AND id_video = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idLista);
            stmt.setInt(2, idVideo);
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }
}