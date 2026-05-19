package com.feitv.dao;

import com.feitv.model.Video;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstatisticaDAO {

    public int getTotalUsuarios() throws Exception {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getTotalVideos() throws Exception {
        String sql = "SELECT COUNT(*) FROM videos";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<Video> getTop5Videos() throws Exception {
        List<Video> lista = new ArrayList<>();
        String sql = """
            SELECT v.id_video, v.titulo,
                   COUNT(CASE WHEN c.tipo = 'like' THEN 1 END) AS curtidas
            FROM videos v
            LEFT JOIN curtidas c ON v.id_video = c.id_video
            GROUP BY v.id_video, v.titulo
            ORDER BY curtidas DESC
            LIMIT 5
        """;
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Video v = new Video();
                v.setTitulo(rs.getString("titulo"));
                v.setCurtidas(rs.getInt("curtidas"));
                lista.add(v);
            }
        }
        return lista;
    }
}