package com.feitv.view;

import com.feitv.dao.PlaylistDAO;
import com.feitv.model.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaPlaylist extends JFrame {

    private JTextField txtNome;

    private JTextArea txtDescricao;

    private JButton btnSalvar;

    private JButton btnAtualizar;

    private JButton btnEditar;

    private JButton btnExcluir;

    private JList<String> listaPlaylists;

    private DefaultListModel<String> modeloLista;

    private List<Playlist> playlists;

    public TelaPlaylist() {

        setTitle("Playlists");

        setSize(700, 500);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel titulo =
                new JLabel("Cadastro de Playlist",
                        SwingConstants.CENTER);

        JPanel painel = new JPanel();

        painel.setLayout(new GridLayout(8, 1, 10, 10));

        txtNome = new JTextField();

        txtDescricao = new JTextArea();

        btnSalvar = new JButton("Salvar Playlist");

        btnAtualizar = new JButton("Atualizar Lista");

        btnEditar = new JButton("Editar Playlist");

        btnExcluir = new JButton("Excluir Playlist");

        modeloLista = new DefaultListModel<>();

        listaPlaylists = new JList<>(modeloLista);

        playlists = new ArrayList<>();

        btnSalvar.addActionListener(e -> salvarPlaylist());

        btnAtualizar.addActionListener(e -> carregarPlaylists());

        btnEditar.addActionListener(e -> editarPlaylist());

        btnExcluir.addActionListener(e -> excluirPlaylist());

        listaPlaylists.addListSelectionListener(e -> preencherCampos());

        painel.add(new JLabel("Nome da Playlist:"));

        painel.add(txtNome);

        painel.add(new JLabel("Descrição:"));

        painel.add(new JScrollPane(txtDescricao));

        painel.add(btnSalvar);

        painel.add(btnEditar);

        painel.add(btnExcluir);

        painel.add(btnAtualizar);

        add(titulo, BorderLayout.NORTH);

        add(painel, BorderLayout.WEST);

        add(new JScrollPane(listaPlaylists),
                BorderLayout.CENTER);

        carregarPlaylists();

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

            carregarPlaylists();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    private void editarPlaylist() {

        try {

            int indice =
                    listaPlaylists.getSelectedIndex();

            if (indice == -1) {

                JOptionPane.showMessageDialog(this,
                        "Selecione uma playlist!");

                return;

            }

            Playlist playlist =
                    playlists.get(indice);

            playlist.setNome(
                    txtNome.getText());

            playlist.setDescricao(
                    txtDescricao.getText());

            PlaylistDAO dao =
                    new PlaylistDAO();

            dao.atualizar(playlist);

            JOptionPane.showMessageDialog(this,
                    "Playlist atualizada!");

            carregarPlaylists();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    private void excluirPlaylist() {

        try {

            int indice =
                    listaPlaylists.getSelectedIndex();

            if (indice == -1) {

                JOptionPane.showMessageDialog(this,
                        "Selecione uma playlist!");

                return;

            }

            Playlist playlist =
                    playlists.get(indice);

            int confirmar =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja excluir a playlist?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmar == JOptionPane.YES_OPTION) {

                PlaylistDAO dao =
                        new PlaylistDAO();

                dao.excluir(
                        playlist.getId());

                JOptionPane.showMessageDialog(this,
                        "Playlist excluída!");

                txtNome.setText("");

                txtDescricao.setText("");

                carregarPlaylists();

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    private void preencherCampos() {

        int indice =
                listaPlaylists.getSelectedIndex();

        if (indice != -1) {

            Playlist playlist =
                    playlists.get(indice);

            txtNome.setText(
                    playlist.getNome());

            txtDescricao.setText(
                    playlist.getDescricao());

        }

    }

    private void carregarPlaylists() {

        try {

            modeloLista.clear();

            PlaylistDAO dao =
                    new PlaylistDAO();

            playlists =
                    dao.listar();

            for (Playlist p : playlists) {

                modeloLista.addElement(
                        p.getNome() +
                        " - " +
                        p.getDescricao());

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar playlists: "
                            + e.getMessage());

        }

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new TelaPlaylist().setVisible(true);

        });

    }

}