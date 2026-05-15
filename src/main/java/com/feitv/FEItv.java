package com.feitv;

import com.feitv.view.TelaHome;

public class FEItv {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new TelaHome().setVisible(true);

        });

    }

}