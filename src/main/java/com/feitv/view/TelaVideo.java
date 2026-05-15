package com.feitv.view;

import com.feitv.dao.VideoDAO;
import com.feitv.model.Video;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaVideo extends JFrame {

    private JTextField txtTitulo, txtUrl, txtCategoria, txtDuracao, txtBusca;
    private JTable tabelaVideos;
    private DefaultTableModel modeloTabela;
    private VideoDAO dao = new VideoDAO();

    public TelaVideo() {

        setTitle("Gerenciar Vídeos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCadastro = new JPanel(new GridLayout(5, 2, 5, 5));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Administração de Vídeos"));

        painelCadastro.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painelCadastro.add(txtTitulo);

        painelCadastro.add(new JLabel("URL:"));
        txtUrl = new JTextField();
        painelCadastro.add(txtUrl);

        painelCadastro.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        painelCadastro.add(txtCategoria);

        painelCadastro.add(new JLabel("Duração:"));
        txtDuracao = new JTextField();
        painelCadastro.add(txtDuracao);

        JButton btnSalvar = new JButton("Salvar Vídeo");
        btnSalvar.addActionListener(e -> salvarVideo());
        painelCadastro.add(btnSalvar);

        JButton btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.addActionListener(e -> excluirVideo());
        painelCadastro.add(btnExcluir);

        JPanel painelBuscaCurtir = new JPanel(new BorderLayout(5, 5));
        painelBuscaCurtir.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel painelBusca = new JPanel(new BorderLayout());
        painelBusca.add(new JLabel("Buscar por nome: "), BorderLayout.WEST);
        txtBusca = new JTextField();
        painelBusca.add(txtBusca, BorderLayout.CENTER);
        JButton btnBuscar = new JButton("Pesquisar");
        btnBuscar.addActionListener(e -> carregarVideos(txtBusca.getText()));
        painelBusca.add(btnBuscar, BorderLayout.EAST);

        JButton btnCurtir = new JButton("Curtir Vídeo Selecionado");
        btnCurtir.setBackground(new Color(100, 200, 100));
        btnCurtir.addActionListener(e -> curtirVideo());

        painelBuscaCurtir.add(painelBusca, BorderLayout.CENTER);
        painelBuscaCurtir.add(btnCurtir, BorderLayout.SOUTH);

        JPanel painelNorte = new JPanel(new BorderLayout(10, 10));
        painelNorte.add(painelCadastro, BorderLayout.NORTH);
        painelNorte.add(painelBuscaCurtir, BorderLayout.SOUTH);

        add(painelNorte, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Título", "URL", "Categoria", "Duração", "Curtidas"}, 0);
        tabelaVideos = new JTable(modeloTabela);
        add(new JScrollPane(tabelaVideos), BorderLayout.CENTER);

        carregarVideos("");
    }

    private void carregarVideos(String nome) {
        modeloTabela.setRowCount(0);
        List<Video> videos = dao.buscarPorNome(nome);
        for (Video v : videos) {
            modeloTabela.addRow(new Object[]{
                v.getId(), 
                v.getTitulo(), 
                v.getUrl(), 
                v.getCategoria(), 
                v.getDuracao(), 
                v.getCurtidas()
            });
        }
    }

    private void salvarVideo() {
        if (txtTitulo.getText().isEmpty() || txtUrl.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha ao menos Título e URL.");
            return;
        }
        Video v = new Video();
        v.setTitulo(txtTitulo.getText());
        v.setUrl(txtUrl.getText());
        v.setCategoria(txtCategoria.getText());
        v.setDuracao(txtDuracao.getText());
        
        dao.cadastrar(v);
        
        txtTitulo.setText("");
        txtUrl.setText("");
        txtCategoria.setText("");
        txtDuracao.setText("");
        carregarVideos("");
    }

    private void excluirVideo() {
        int linha = tabelaVideos.getSelectedRow();
        if (linha != -1) {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?");
            if (confirm == JOptionPane.YES_OPTION) {
                dao.excluir(id);
                carregarVideos("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um vídeo para excluir.");
        }
    }

    private void curtirVideo() {
        int linha = tabelaVideos.getSelectedRow();
        if (linha != -1) {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            dao.curtirVideo(id);
            carregarVideos(txtBusca.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um vídeo na tabela para curtir.");
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaVideo().setVisible(true));
    }
}