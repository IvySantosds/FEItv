package com.feitv.view;

import com.feitv.dao.PlaylistDAO;
import com.feitv.model.Playlist;
import com.feitv.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaPlaylist extends JFrame {

    private JTextField txtNome;
    private JButton btnSalvar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JList<String> listaPlaylists;
    private DefaultListModel<String> modeloLista;
    private List<Playlist> playlists;
    private Usuario usuario; // ✅ adicionado

    public TelaPlaylist(Usuario user) { 
        this.usuario = user;

        setTitle("Playlists");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Minhas Playlists", SwingConstants.CENTER);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(6, 1, 10, 10));

        txtNome = new JTextField();
        btnSalvar = new JButton("Salvar Playlist");
        btnAtualizar = new JButton("Atualizar Lista");
        btnExcluir = new JButton("Excluir Playlist");

        modeloLista = new DefaultListModel<>();
        listaPlaylists = new JList<>(modeloLista);
        playlists = new ArrayList<>();

        btnSalvar.addActionListener(e -> salvarPlaylist());
        btnAtualizar.addActionListener(e -> carregarPlaylists());
        btnExcluir.addActionListener(e -> excluirPlaylist());
        listaPlaylists.addListSelectionListener(e -> preencherCampos());

        painel.add(new JLabel("Nome da Playlist:"));
        painel.add(txtNome);
        painel.add(btnSalvar);
        painel.add(btnExcluir);
        painel.add(btnAtualizar);

        add(titulo, BorderLayout.NORTH);
        add(painel, BorderLayout.WEST);
        add(new JScrollPane(listaPlaylists), BorderLayout.CENTER);

        carregarPlaylists();
    }

    private void salvarPlaylist() {
        try {
            Playlist playlist = new Playlist();
            playlist.setNome(txtNome.getText());
            playlist.setIdUsuario(usuario.getId());

            PlaylistDAO dao = new PlaylistDAO();
            dao.cadastrar(playlist);

            JOptionPane.showMessageDialog(this, "Playlist salva com sucesso!");
            txtNome.setText("");
            carregarPlaylists();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void excluirPlaylist() {
        try {
            int indice = listaPlaylists.getSelectedIndex();
            if (indice == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma playlist!");
                return;
            }

            Playlist playlist = playlists.get(indice);
            int confirmar = JOptionPane.showConfirmDialog(
                    this, "Deseja excluir a playlist?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                PlaylistDAO dao = new PlaylistDAO();
                dao.excluir(playlist.getId());
                JOptionPane.showMessageDialog(this, "Playlist excluída!");
                txtNome.setText("");
                carregarPlaylists();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void preencherCampos() {
        int indice = listaPlaylists.getSelectedIndex();
        if (indice != -1) {
            Playlist playlist = playlists.get(indice);
            txtNome.setText(playlist.getNome());
        }
    }

    private void carregarPlaylists() {
        try {
            modeloLista.clear();
            PlaylistDAO dao = new PlaylistDAO();
            playlists = dao.listarPorUsuario(usuario.getId());
            for (Playlist p : playlists) {
                modeloLista.addElement(p.getNome());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar playlists: " + e.getMessage());
        }
    }
}