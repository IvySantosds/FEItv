package com.feitv.view;

import com.feitv.dao.PlaylistDAO;
import com.feitv.model.Playlist;

import javax.swing.*;
import java.awt.*;

public class TelaPlaylist extends JFrame {

    private JTextField txtNome;

    private JTextArea txtDescricao;

    private JButton btnSalvar;

    public TelaPlaylist() {

        setTitle("Playlists");

        setSize(400, 400);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel titulo =
                new JLabel("Cadastro de Playlist",
                        SwingConstants.CENTER);

        JPanel painel = new JPanel();

        painel.setLayout(new GridLayout(5, 1, 10, 10));

        txtNome = new JTextField();

        txtDescricao = new JTextArea();

        btnSalvar = new JButton("Salvar Playlist");

        btnSalvar.addActionListener(e -> salvarPlaylist());

        painel.add(new JLabel("Nome da Playlist:"));

        painel.add(txtNome);

        painel.add(new JLabel("Descrição:"));

        painel.add(new JScrollPane(txtDescricao));

        painel.add(btnSalvar);

        add(titulo, BorderLayout.NORTH);

        add(painel, BorderLayout.CENTER);

    }

    private void salvarPlaylist() {

        try {

            Playlist playlist =
                    new Playlist();

            playlist.setNome(
                    txtNome.getText());

            playlist.setDescricao(
                    txtDescricao.getText());

            PlaylistDAO dao =
                    new PlaylistDAO();

            dao.cadastrar(playlist);

            JOptionPane.showMessageDialog(this,
                    "Playlist salva com sucesso!");

            txtNome.setText("");

            txtDescricao.setText("");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new TelaPlaylist().setVisible(true);

        });

    }

}