package com.livraria.model;

public abstract class Livro {
    protected int id;
    protected String titulo;
    protected String descricao;
    protected String isbn;
    protected double valorBase;

    public Livro(int id, String titulo, String descricao, String isbn, double valorBase) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.isbn = isbn;
        this.valorBase = valorBase;
    }

    public abstract double calcularValorFinal();

    public abstract String getTipo();

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %s - ISBN: %s - R$ %.2f -> R$ %.2f",
                getTipo(), titulo, isbn, valorBase, calcularValorFinal());
    }
}
