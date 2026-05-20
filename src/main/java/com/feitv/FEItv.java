package com.feitv;
import com.feitv.view.TelaLoginNova;

public class FEItv {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaLoginNova().setVisible(true);
        });
    }
}