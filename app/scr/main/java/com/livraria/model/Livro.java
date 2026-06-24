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

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public double getValorBase() { return valorBase; }
}