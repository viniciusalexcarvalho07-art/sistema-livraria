package com.livraria.model;

public class Ebook extends Livro {
    private double tamanhoArquivoMb;

    public Ebook(int id, String titulo, String descricao, String isbn, double valorBase, double tamanhoArquivoMb) {
        super(id, titulo, descricao, isbn, valorBase);
        this.tamanhoArquivoMb = tamanhoArquivoMb;
    }

    @Override
    public double calcularValorFinal() {
        // Regra do projeto: Ebooks têm 15% de desconto
        return valorBase * 0.85; 
    }
}