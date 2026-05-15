package com.feitv.view;

import com.feitv.dao.UsuarioDAO;
import com.feitv.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaLoginNova extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;

    public TelaLoginNova() {
        setTitle("FEItv Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        add(new JLabel("FEItv Login", SwingConstants.CENTER));
        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(e -> fazerLogin());
        add(btnEntrar);

        JButton btnCadastrar = new JButton("Não tem conta? Cadastre-se");
        btnCadastrar.addActionListener(e -> new TelaCadastroUsuario().setVisible(true));
        add(btnCadastrar);
    }

    private void fazerLogin() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario user = dao.login(txtEmail.getText(), new String(txtSenha.getPassword()));

            if (user != null) {
                new TelaHome(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaLoginNova().setVisible(true));
    }
}