package com.feitv.view;

import com.feitv.dao.PlaylistDAO;
import com.feitv.dao.VideoDAO;
import com.feitv.model.Playlist;
import com.feitv.model.Usuario;
import com.feitv.model.Video;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaPlaylist extends JFrame {

    private JTextField txtNome;
    private JList<String> listaPlaylists;
    private DefaultListModel<String> modeloPlaylists;
    private List<Playlist> playlists;

    private JList<String> listaVideos;
    private DefaultListModel<String> modeloVideos;
    private List<Video> videosNaPlaylist;

    private Usuario usuario;
    private PlaylistDAO pDao = new PlaylistDAO();
    private VideoDAO vDao = new VideoDAO();

    public TelaPlaylist(Usuario user) {
        this.usuario = user;

        setTitle("Playlists");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelEsquerdo = new JPanel(new BorderLayout(5, 5));
        painelEsquerdo.setBorder(BorderFactory.createTitledBorder("Minhas Playlists"));
        painelEsquerdo.setPreferredSize(new Dimension(260, 0));

        JPanel painelNome = new JPanel(new BorderLayout(5, 5));
        painelNome.add(new JLabel("Nome:"), BorderLayout.WEST);
        txtNome = new JTextField();
        painelNome.add(txtNome, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton btnSalvar  = new JButton("Criar Playlist");
        JButton btnExcluir = new JButton("Excluir Playlist");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);

        modeloPlaylists  = new DefaultListModel<>();
        listaPlaylists   = new JList<>(modeloPlaylists);
        listaPlaylists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel painelTopo = new JPanel(new BorderLayout(5, 5));
        painelTopo.add(painelNome,    BorderLayout.NORTH);
        painelTopo.add(painelBotoes,  BorderLayout.SOUTH);

        painelEsquerdo.add(painelTopo,                    BorderLayout.NORTH);
        painelEsquerdo.add(new JScrollPane(listaPlaylists), BorderLayout.CENTER);

        JPanel painelDireito = new JPanel(new BorderLayout(5, 5));
        painelDireito.setBorder(BorderFactory.createTitledBorder("Vídeos na Playlist"));

        modeloVideos      = new DefaultListModel<>();
        listaVideos       = new JList<>(modeloVideos);
        videosNaPlaylist  = new ArrayList<>();
        listaVideos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnRemoverVideo = new JButton("Remover Vídeo Selecionado");
        btnRemoverVideo.setBackground(new Color(255, 150, 150));

        painelDireito.add(new JScrollPane(listaVideos), BorderLayout.CENTER);
        painelDireito.add(btnRemoverVideo,              BorderLayout.SOUTH);

        add(painelEsquerdo, BorderLayout.WEST);
        add(painelDireito,  BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarPlaylist());
        btnExcluir.addActionListener(e -> excluirPlaylist());
        btnRemoverVideo.addActionListener(e -> removerVideo());

        listaPlaylists.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarVideosDaPlaylist();
            }
        });

        carregarPlaylists();
    }


    private void salvarPlaylist() {
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome para a playlist!");
            return;
        }
        try {
            Playlist p = new Playlist();
            p.setNome(nome);
            p.setIdUsuario(usuario.getId());
            pDao.cadastrar(p);
            txtNome.setText("");
            carregarPlaylists();
            JOptionPane.showMessageDialog(this, "Playlist criada!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void excluirPlaylist() {
        int indice = listaPlaylists.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma playlist!");
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(
                this, "Excluir a playlist e todos os seus vídeos?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                pDao.excluir(playlists.get(indice).getId());
                modeloPlaylists.remove(indice);
                playlists.remove(indice);
                modeloVideos.clear();
                videosNaPlaylist.clear();
                txtNome.setText("");
                JOptionPane.showMessageDialog(this, "Playlist excluída!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }

    private void carregarPlaylists() {
        try {
            modeloPlaylists.clear();
            playlists = pDao.listarPorUsuario(usuario.getId());
            for (Playlist p : playlists) {
                modeloPlaylists.addElement(p.getNome());
            }
            modeloVideos.clear();
            videosNaPlaylist = new ArrayList<>();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar playlists: " + e.getMessage());
        }
    }


    private void carregarVideosDaPlaylist() {
        int indice = listaPlaylists.getSelectedIndex();
        modeloVideos.clear();
        videosNaPlaylist = new ArrayList<>();
        if (indice == -1) return;

        try {
            int idLista = playlists.get(indice).getId();
            videosNaPlaylist = vDao.buscarPorPlaylist(idLista);
            if (videosNaPlaylist.isEmpty()) {
                modeloVideos.addElement("— Nenhum vídeo nesta playlist —");
            } else {
                for (Video v : videosNaPlaylist) {
                    modeloVideos.addElement(v.getTitulo() + "  |  " + v.getUrl());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar vídeos: " + e.getMessage());
        }
    }

    private void removerVideo() {
        int indicePlaylist = listaPlaylists.getSelectedIndex();
        if (indicePlaylist == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma playlist!");
            return;
        }
        int indiceVideo = listaVideos.getSelectedIndex();
        if (indiceVideo == -1 || videosNaPlaylist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um vídeo para remover!");
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(
                this, "Remover este vídeo da playlist?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                int idLista = playlists.get(indicePlaylist).getId();
                int idVideo = videosNaPlaylist.get(indiceVideo).getId();
                pDao.removerVideoDaPlaylist(idLista, idVideo);
                modeloVideos.remove(indiceVideo);
                videosNaPlaylist.remove(indiceVideo);
                JOptionPane.showMessageDialog(this, "Vídeo removido da playlist!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }
}