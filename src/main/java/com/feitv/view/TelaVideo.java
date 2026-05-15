package com.feitv.view;

import com.feitv.dao.VideoDAO;
import com.feitv.model.Video;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaVideo extends JFrame {

    private JTextField txtTitulo;

    private JTextField txtUrl;

    private JTextField txtDuracao;

    private JTextField txtCategoria;

    private JButton btnSalvar;

    private JButton btnAtualizar;

    private JButton btnCurtir;

    private JButton btnExcluir;

    private JList<String> listaVideos;

    private DefaultListModel<String> modeloLista;

    private List<Video> videos;

    public TelaVideo() {

        setTitle("Vídeos");

        setSize(700, 500);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel titulo =
                new JLabel("Cadastro de Vídeos",
                        SwingConstants.CENTER);

        JPanel painel = new JPanel();

        painel.setLayout(new GridLayout(12, 1, 10, 10));

        txtTitulo = new JTextField();

        txtUrl = new JTextField();

        txtDuracao = new JTextField();

        txtCategoria = new JTextField();

        btnSalvar = new JButton("Salvar Vídeo");

        btnAtualizar = new JButton("Atualizar Lista");

        btnCurtir = new JButton("Curtir ❤️");

        btnExcluir = new JButton("Excluir Vídeo");

        modeloLista = new DefaultListModel<>();

        listaVideos = new JList<>(modeloLista);

        videos = new ArrayList<>();

        btnSalvar.addActionListener(e -> salvarVideo());

        btnAtualizar.addActionListener(e -> carregarVideos());

        btnCurtir.addActionListener(e -> curtirVideo());

        btnExcluir.addActionListener(e -> excluirVideo());

        painel.add(new JLabel("Título:"));

        painel.add(txtTitulo);

        painel.add(new JLabel("URL:"));

        painel.add(txtUrl);

        painel.add(new JLabel("Duração:"));

        painel.add(txtDuracao);

        painel.add(new JLabel("Categoria:"));

        painel.add(txtCategoria);

        painel.add(btnSalvar);

        painel.add(btnCurtir);

        painel.add(btnExcluir);

        painel.add(btnAtualizar);

        add(titulo, BorderLayout.NORTH);

        add(painel, BorderLayout.WEST);

        add(new JScrollPane(listaVideos),
                BorderLayout.CENTER);

        carregarVideos();

    }

    private void salvarVideo() {

        try {

            Video video =
                    new Video();

            video.setTitulo(
                    txtTitulo.getText());

            video.setUrl(
                    txtUrl.getText());

            video.setDuracao(
                    txtDuracao.getText());

            video.setCategoria(
                    txtCategoria.getText());

            VideoDAO dao =
                    new VideoDAO();

            dao.cadastrar(video);

            JOptionPane.showMessageDialog(this,
                    "Vídeo salvo com sucesso!");

            txtTitulo.setText("");

            txtUrl.setText("");

            txtDuracao.setText("");

            txtCategoria.setText("");

            carregarVideos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    private void curtirVideo() {

        try {

            int indice =
                    listaVideos.getSelectedIndex();

            if (indice == -1) {

                JOptionPane.showMessageDialog(this,
                        "Selecione um vídeo!");

                return;

            }

            Video video =
                    videos.get(indice);

            VideoDAO dao =
                    new VideoDAO();

            dao.curtirVideo(video.getId());

            JOptionPane.showMessageDialog(this,
                    "Vídeo curtido ❤️");

            carregarVideos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    private void excluirVideo() {

        try {

            int indice =
                    listaVideos.getSelectedIndex();

            if (indice == -1) {

                JOptionPane.showMessageDialog(this,
                        "Selecione um vídeo!");

                return;

            }

            Video video =
                    videos.get(indice);

            VideoDAO dao =
                    new VideoDAO();

            dao.excluir(video.getId());

            JOptionPane.showMessageDialog(this,
                    "Vídeo excluído!");

            carregarVideos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    private void carregarVideos() {

        try {

            modeloLista.clear();

            VideoDAO dao =
                    new VideoDAO();

            videos =
                    dao.listar();

            for (Video v : videos) {

                modeloLista.addElement(
                        v.getTitulo() +
                        " | " +
                        v.getCategoria() +
                        " | " +
                        v.getDuracao() +
                        " | Curtidas: " +
                        v.getCurtidas());

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar vídeos: "
                            + e.getMessage());

        }

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new TelaVideo().setVisible(true);

        });

    }

}