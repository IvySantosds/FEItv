package com.feitv.view;

import com.feitv.dao.VideoDAO;
import com.feitv.dao.PlaylistDAO;
import com.feitv.model.Video;
import com.feitv.model.Playlist;
import com.feitv.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class TelaVideo extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtUrl;
    private JTextField txtDescricao;
    private JTextField txtBusca;

    private JTable tabelaVideos;
    private DefaultTableModel modeloTabela;

    private VideoDAO dao = new VideoDAO();
    private PlaylistDAO pDao = new PlaylistDAO();

    private Usuario usuario;

    public TelaVideo(Usuario user) {

        this.usuario = user;

        setTitle("Vídeos - " + (user.isAdmin() ? "Administração" : "Explorar"));
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCadastro = new JPanel(new GridLayout(4, 2, 5, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Administração de Vídeos"));

        painelCadastro.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painelCadastro.add(txtTitulo);

        painelCadastro.add(new JLabel("URL:"));
        txtUrl = new JTextField();
        painelCadastro.add(txtUrl);

        painelCadastro.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        painelCadastro.add(txtDescricao);

        JButton btnSalvar = new JButton("Salvar Vídeo");
        btnSalvar.addActionListener(e -> salvarVideo());
        painelCadastro.add(btnSalvar);

        JButton btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.addActionListener(e -> excluirVideo());
        painelCadastro.add(btnExcluir);

        if (!user.isAdmin()) {
            painelCadastro.setVisible(false);
        }

        JPanel painelAcoesUsuario = new JPanel(new BorderLayout(5, 5));
        painelAcoesUsuario.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel painelBusca = new JPanel(new BorderLayout());
        painelBusca.add(new JLabel("Buscar por nome: "), BorderLayout.WEST);
        txtBusca = new JTextField();
        painelBusca.add(txtBusca, BorderLayout.CENTER);
        JButton btnBuscar = new JButton("Pesquisar");
        btnBuscar.addActionListener(e -> carregarVideos(txtBusca.getText()));
        painelBusca.add(btnBuscar, BorderLayout.EAST);

        JPanel painelBotoesAcao = new JPanel(new GridLayout(1, 4, 5, 5));

        JButton btnCurtir = new JButton("Curtir");
        btnCurtir.setBackground(new Color(100, 200, 100));
        btnCurtir.addActionListener(e -> curtirVideo());

        JButton btnDescurtir = new JButton("Descurtir");
        btnDescurtir.setBackground(new Color(255, 150, 150));
        btnDescurtir.addActionListener(e -> descurtirVideo());

        JButton btnFavoritar = new JButton("Favoritar");
        btnFavoritar.setBackground(new Color(255, 215, 0));
        btnFavoritar.addActionListener(e -> adicionarAosFavoritos());

        JButton btnAssistir = new JButton("Assistir");
        btnAssistir.setBackground(new Color(100, 150, 255));
        btnAssistir.addActionListener(e -> assistirVideo());

        painelBotoesAcao.add(btnCurtir);
        painelBotoesAcao.add(btnDescurtir);
        painelBotoesAcao.add(btnFavoritar);
        painelBotoesAcao.add(btnAssistir);

        painelAcoesUsuario.add(painelBusca, BorderLayout.NORTH);
        painelAcoesUsuario.add(painelBotoesAcao, BorderLayout.SOUTH);

        JPanel painelNorte = new JPanel(new BorderLayout(10, 10));
        painelNorte.add(painelCadastro, BorderLayout.NORTH);
        painelNorte.add(painelAcoesUsuario, BorderLayout.SOUTH);

        add(painelNorte, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Título", "URL", "Curtidas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaVideos = new JTable(modeloTabela);
        add(new JScrollPane(tabelaVideos), BorderLayout.CENTER);

        carregarVideos("");
    }

    private void carregarVideos(String nome) {
        try {
            modeloTabela.setRowCount(0);
            List<Video> videos = dao.buscarPorNome(nome);
            for (Video v : videos) {
                modeloTabela.addRow(new Object[]{
                        v.getId(),
                        v.getTitulo(),
                        v.getUrl(),
                        v.getCurtidas()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar vídeos: " + e.getMessage());
        }
    }

    private void salvarVideo() {
        try {
            String titulo = txtTitulo.getText().trim();
            String url = txtUrl.getText().trim();

            if (titulo.isEmpty() || url.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título e URL são obrigatórios!");
                return;
            }

            Video v = new Video();
            v.setTitulo(titulo);
            v.setUrl(url);
            v.setDescricao(txtDescricao.getText().trim());
            v.setIdAdmin(usuario.getId());

            dao.cadastrar(v);

            txtTitulo.setText("");
            txtUrl.setText("");
            txtDescricao.setText("");

            carregarVideos("");
            JOptionPane.showMessageDialog(this, "Vídeo salvo com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar vídeo: " + e.getMessage());
        }
    }

    private void excluirVideo() {
        try {
            int linha = tabelaVideos.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um vídeo!");
                return;
            }

            int linhaModelo = tabelaVideos.convertRowIndexToModel(linha);
            int id = (int) modeloTabela.getValueAt(linhaModelo, 0);

            int confirmar = JOptionPane.showConfirmDialog(
                    this, "Deseja excluir este vídeo?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                dao.excluir(id);
                carregarVideos("");
                JOptionPane.showMessageDialog(this, "Vídeo excluído!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir vídeo: " + e.getMessage());
        }
    }

    private void curtirVideo() {
        try {
            int linha = tabelaVideos.getSelectedRow();
            if (linha != -1) {
                int linhaModelo = tabelaVideos.convertRowIndexToModel(linha);
                int id = (int) modeloTabela.getValueAt(linhaModelo, 0);
                dao.curtirVideo(id, usuario.getId());
                carregarVideos(txtBusca.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao curtir vídeo: " + e.getMessage());
        }
    }

    private void descurtirVideo() {
        try {
            int linha = tabelaVideos.getSelectedRow();
            if (linha != -1) {
                int linhaModelo = tabelaVideos.convertRowIndexToModel(linha);
                int id = (int) modeloTabela.getValueAt(linhaModelo, 0);
                dao.descurtirVideo(id, usuario.getId());
                carregarVideos(txtBusca.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao descurtir vídeo: " + e.getMessage());
        }
    }

    private void adicionarAosFavoritos() {
        try {
            int linha = tabelaVideos.getSelectedRow();
            if (linha == -1) return;

            int linhaModelo = tabelaVideos.convertRowIndexToModel(linha);
            int videoId = (int) modeloTabela.getValueAt(linhaModelo, 0);

            List<Playlist> playlists = pDao.listarPorUsuario(usuario.getId());

            if (playlists.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Você não possui playlists. Crie uma primeiro!");
                return;
            }

            String[] nomes = playlists.stream()
                    .map(Playlist::getNome)
                    .toArray(String[]::new);

            String sel = (String) JOptionPane.showInputDialog(
                    this, "Escolha a playlist:",
                    "Favoritar", JOptionPane.PLAIN_MESSAGE,
                    null, nomes, nomes[0]);

            if (sel != null) {
                int pId = playlists.stream()
                        .filter(p -> p.getNome().equals(sel))
                        .findFirst()
                        .get()
                        .getId();

                pDao.adicionarVideoAFavoritos(pId, videoId);
                JOptionPane.showMessageDialog(this, "Adicionado aos favoritos!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao favoritar: " + e.getMessage());
        }
    }

    private void assistirVideo() {
        try {
            int linha = tabelaVideos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um vídeo.");
                return;
            }

            int linhaModelo = tabelaVideos.convertRowIndexToModel(linha);
            int videoId = (int) modeloTabela.getValueAt(linhaModelo, 0);
            String titulo = (String) modeloTabela.getValueAt(linhaModelo, 1);
            String url = (String) modeloTabela.getValueAt(linhaModelo, 2);

            if (url == null || url.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Este vídeo não possui URL cadastrada.");
                return;
            }

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }

            dao.registrarHistorico(usuario.getId(), videoId);
            Desktop.getDesktop().browse(new URI(url));
            JOptionPane.showMessageDialog(this, "Assistindo: " + titulo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao abrir vídeo: " + e.getMessage());
        }
    }
}