/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.feitv.model;

/**
 *
 * @author usuário
 */
public class SerieFilme extends Video implements Situacao {

    @Override
    public void curtir() {

        System.out.println("Vídeo curtido");

    }

    @Override
    public void descurtir() {

        System.out.println("Vídeo descurtido");

    }
}
