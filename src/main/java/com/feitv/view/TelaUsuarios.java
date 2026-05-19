package com.feitv.view;

import com.feitv.dao.UsuarioDAO;
import com.feitv.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaUsuarios extends JFrame {

    private JList<String> listaUsuarios;

    private DefaultListModel<String> modeloLista;

    public TelaUsuarios() {

        setTitle("Usuários");

        setSize(500, 400);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel titulo =
                new JLabel("Usuários Cadastrados",
                        SwingConstants.CENTER);

        modeloLista =
                new DefaultListModel<>();

        listaUsuarios =
                new JList<>(modeloLista);

        add(titulo, BorderLayout.NORTH);

        add(new JScrollPane(listaUsuarios),
                BorderLayout.CENTER);

        carregarUsuarios();

    }

    private void carregarUsuarios() {

        try {

            modeloLista.clear();

            UsuarioDAO dao =
                    new UsuarioDAO();

            List<Usuario> usuarios =
                    dao.listar();

            for (Usuario u : usuarios) {

                modeloLista.addElement(
                        "ID: " +
                        u.getId() +
                        " | Nome: " +
                        u.getNome() +
                        " | Email: " +
                        u.getEmail());

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar usuários: "
                            + e.getMessage());

        }

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new TelaUsuarios().setVisible(true);

        });

    }

}