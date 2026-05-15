/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.feitv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CurtidaDAO {

    public void curtir(int idUser, int idVideo) {

        String sql =
        "INSERT INTO curtidas (id_user, id_video, tipo) VALUES (?, ?, 'like')";

        try {

            Connection con = Conexao.conectar();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setInt(1, idUser);
            stmt.setInt(2, idVideo);

            stmt.execute();

            stmt.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void descurtir(int idUser, int idVideo) {

        String sql =
        "INSERT INTO curtidas (id_user, id_video, tipo) VALUES (?, ?, 'dislike')";

        try {

            Connection con = Conexao.conectar();

            PreparedStatement stmt =
                    con.prepareStatement(sql);

            stmt.setInt(1, idUser);
            stmt.setInt(2, idVideo);

            stmt.execute();

            stmt.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
