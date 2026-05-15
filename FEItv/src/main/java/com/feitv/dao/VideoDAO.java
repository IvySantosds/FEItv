/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.feitv.dao;

import com.feitv.model.SerieFilme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO {

    public List<SerieFilme> listarVideos() {

        List<SerieFilme> lista = new ArrayList<>();

        String sql = "SELECT * FROM videos";

        try {

            Connection con = Conexao.conectar();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                SerieFilme video = new SerieFilme();

                video.setIdVideo(
                        rs.getInt("id_video")
                );

                video.setTitulo(
                        rs.getString("titulo")
                );

                video.setDescricao(
                        rs.getString("descricao")
                );

                video.setUrlVideo(
                        rs.getString("url_video")
                );

                lista.add(video);

            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return lista;

    }
}
