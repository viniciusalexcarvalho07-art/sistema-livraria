package com.livraria.model;

public class Ebook extends Livro {
    private double tamanhoArquivoMb;

    public Ebook(
            int id,
            String titulo,
            String descricao,
            String isbn,
            double valorBase,
            double tamanhoArquivoMb) {
        super(id, titulo, descricao, isbn, valorBase);
        this.tamanhoArquivoMb = tamanhoArquivoMb;
    }

    @Override
    public double calcularValorFinal() {
        // Ebooks têm 15% de desconto
        return valorBase * 0.85;
    }

    @Override
    public String getTipo() {
        return "EBOOK";
    }

    public double getTamanhoArquivoMb() {
        return tamanhoArquivoMb;
    }

    public void setTamanhoArquivoMb(double tamanhoArquivoMb) {
        this.tamanhoArquivoMb = tamanhoArquivoMb;
    }
}
