package com.feitv.view;

import com.feitv.dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;

public class TelaLoginNova extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public TelaLoginNova() {

        setTitle("FEItv Login");

        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridLayout(7, 1, 10, 10));

        JLabel lblTitulo = new JLabel("FEItv Login", SwingConstants.CENTER);

        JLabel lblEmail = new JLabel("Email:");

        txtEmail = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");

        txtSenha = new JPasswordField();

        btnEntrar = new JButton("Entrar");

        btnEntrar.addActionListener(e -> fazerLogin());

        add(lblTitulo);

        add(lblEmail);

        add(txtEmail);

        add(lblSenha);

        add(txtSenha);

        add(btnEntrar);

    }

    private void fazerLogin() {

        try {

            String email = txtEmail.getText();

            String senha = new String(txtSenha.getPassword());

            UsuarioDAO dao = new UsuarioDAO();

            boolean login = dao.login(email, senha);

            if (login) {

                JOptionPane.showMessageDialog(this,
                        "Login realizado com sucesso!");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Email ou senha incorretos!");

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Erro: " + e.getMessage());

        }

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new TelaLoginNova().setVisible(true);

        });

    }

}