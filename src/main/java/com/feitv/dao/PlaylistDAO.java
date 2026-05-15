package com.feitv.dao;

import com.feitv.model.Playlist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    public void cadastrar(Playlist p) {
        String sql = "INSERT INTO playlists (nome) VALUES (?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void atualizar(Playlist p) {
        String sql = "UPDATE playlists SET nome = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getId());
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM playlists WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Playlist> listar() {
        List<Playlist> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM playlists ORDER BY id")) {
            while (rs.next()) {
                Playlist p = new Playlist();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                lista.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}