package com.feitv.view;

import com.feitv.dao.EstatisticaDAO;
import com.feitv.model.Video;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaEstatisticas extends JFrame {

    public TelaEstatisticas() {
        setTitle("Estatísticas do Sistema");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel Superior com os Totais
        JPanel painelCards = new JPanel(new GridLayout(1, 2, 10, 10));
        painelCards.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblUsuarios = new JLabel("Usuários: carregando...");
        JLabel lblVideos = new JLabel("Vídeos: carregando...");
        
        painelCards.add(lblUsuarios);
        painelCards.add(lblVideos);
        add(painelCards, BorderLayout.NORTH);

        // Painel Central com o Top 5
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaTop5 = new JList<>(modeloLista);
        JScrollPane scroll = new JScrollPane(listaTop5);
        scroll.setBorder(BorderFactory.createTitledBorder("Top 5 Vídeos Mais Curtidos"));
        
        add(scroll, BorderLayout.CENTER);

        // Carregar dados do Banco
        try {
            EstatisticaDAO dao = new EstatisticaDAO();
            lblUsuarios.setText("Total de Usuários: " + dao.getTotalUsuarios());
            lblVideos.setText("Total de Vídeos: " + dao.getTotalVideos());

            List<Video> top = dao.getTop5Videos();
            for (Video v : top) {
                modeloLista.addElement(v.getTitulo() + " (" + v.getCurtidas() + " curtidas)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estatísticas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaEstatisticas().setVisible(true));
    }
}