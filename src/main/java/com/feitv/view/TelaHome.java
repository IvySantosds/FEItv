package com.feitv.view;

import javax.swing.*;
import java.awt.*;

public class TelaHome extends JFrame {

    public TelaHome() {

        setTitle("FEItv Home");

        setSize(400, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titulo =
                new JLabel("FEItv",
                        SwingConstants.CENTER);

        JButton btnPlaylist =
                new JButton("Playlists");

        JButton btnVideos =
                new JButton("Vídeos");

        JButton btnPerfil =
                new JButton("Perfil");

        JButton btnEstatisticas =
                new JButton("Estatísticas");

        JButton btnSair =
                new JButton("Sair");

        btnPlaylist.addActionListener(e -> {

            new TelaPlaylist().setVisible(true);

        });

        btnVideos.addActionListener(e -> {

            new TelaVideo().setVisible(true);

        });

        btnPerfil.addActionListener(e -> {

            new TelaUsuarios().setVisible(true);

        });

        btnEstatisticas.addActionListener(e -> {

            new TelaEstatisticas().setVisible(true);

        });

        btnSair.addActionListener(e -> {

            dispose();

        });

        add(titulo);

        add(btnPlaylist);

        add(btnVideos);

        add(btnPerfil);

        add(btnEstatisticas);

        add(btnSair);

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new TelaHome().setVisible(true);

        });

    }

}