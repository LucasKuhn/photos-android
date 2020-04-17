package com.example.photos.model;

public class Photo {
    private int id;
    private String titulo;
    private String descricao;
    private String url;

    public Photo() {}

    public Photo(String titulo, String descricao, String url) {
        super();
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
