package com.feitv.model;

public class Video {
    private int id;
    private String titulo;
    private String url;
    private int curtidas;
    private String categoria;
    private String duracao;

    public Video() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public int getCurtidas() { return curtidas; }
    public void setCurtidas(int curtidas) { this.curtidas = curtidas; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }
}