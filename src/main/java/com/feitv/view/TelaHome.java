package com.feitv.view;

import com.feitv.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaHome extends JFrame {

    public TelaHome(Usuario user) {
        setTitle("FEItv - " + (user.isAdmin() ? "Painel Administrador" : "Painel Usuário"));
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 10, 10));

        JLabel lblBoasVindas = new JLabel("Olá, " + user.getNome(), SwingConstants.CENTER);
        lblBoasVindas.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblBoasVindas);

        JButton btnPlaylist = new JButton("Playlists");
        JButton btnVideos = new JButton("Vídeos / Buscar");
        JButton btnPerfil = new JButton("Consultar Usuários (Admin)");
        JButton btnEstatisticas = new JButton("Estatísticas (Admin)");
        JButton btnSair = new JButton("Sair");

        btnPlaylist.addActionListener(e -> new TelaPlaylist(user).setVisible(true));
        
        btnVideos.addActionListener(e -> new TelaVideo(user).setVisible(true));

        btnPerfil.addActionListener(e -> {
            try {
                new TelaUsuarios().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnEstatisticas.addActionListener(e -> new TelaEstatisticas().setVisible(true));

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLoginNova().setVisible(true);
        });

        if (!user.isAdmin()) {
            btnPerfil.setVisible(false);
            btnEstatisticas.setVisible(false);
        }

        add(btnPlaylist);
        add(btnVideos);
        add(btnPerfil);
        add(btnEstatisticas);
        add(btnSair);
    }
}