package com.feitv.dao;

import com.feitv.model.Video;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO {

    public void cadastrar(Video v) {
        String sql = "INSERT INTO videos (titulo, url, categoria, duracao, curtidas) VALUES (?, ?, ?, ?, 0)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, v.getTitulo());
            stmt.setString(2, v.getUrl());
            stmt.setString(3, v.getCategoria());
            stmt.setString(4, v.getDuracao());
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Video> listar() {
        return buscarPorNome(""); 
    }

    public void curtirVideo(int id) {
        String sql = "UPDATE videos SET curtidas = curtidas + 1 WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Video> buscarPorNome(String nome) {
        List<Video> lista = new ArrayList<>();
        String sql = "SELECT * FROM videos WHERE titulo ILIKE ? ORDER BY id";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Video v = new Video();
                v.setId(rs.getInt("id"));
                v.setTitulo(rs.getString("titulo"));
                v.setUrl(rs.getString("url"));
                v.setCurtidas(rs.getInt("curtidas"));
                v.setCategoria(rs.getString("categoria"));
                v.setDuracao(rs.getString("duracao"));
                lista.add(v);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void excluir(int id) {
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM videos WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) { e.printStackTrace(); }
    }
}