package com.livraria.model;

public class LivroFisico extends Livro {
    private int numeroPaginas;
    private String editora;

    public LivroFisico(
            int id,
            String titulo,
            String descricao,
            String isbn,
            double valorBase,
            int numeroPaginas,
            String editora) {
        super(id, titulo, descricao, isbn, valorBase);
        this.numeroPaginas = numeroPaginas;
        this.editora = editora;
    }

    @Override
    public double calcularValorFinal() {
        // Livros físicos têm taxa de envio fixa de R$ 5.00
        return valorBase + 5.0;
    }

    @Override
    public String getTipo() {
        return "LIVRO FÍSICO";
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getEditora() {
        return editora;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }
}
