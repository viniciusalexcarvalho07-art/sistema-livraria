package com.livraria.dao;

import com.livraria.database.DatabaseManager;
import com.livraria.model.Ebook;
import com.livraria.model.Livro;
import com.livraria.model.LivroFisico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void inserirLivroFisico(LivroFisico livro) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(LivroConsultas.INSERT_LIVRO_FISICO)) {

            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getDescricao());
            pstmt.setString(3, livro.getIsbn());
            pstmt.setDouble(4, livro.getValorBase());
            pstmt.setInt(5, livro.getNumeroPaginas());
            pstmt.setString(6, livro.getEditora());

            pstmt.executeUpdate();
        }
    }

    public void inserirEbook(Ebook ebook) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(LivroConsultas.INSERT_EBOOK)) {

            pstmt.setString(1, ebook.getTitulo());
            pstmt.setString(2, ebook.getDescricao());
            pstmt.setString(3, ebook.getIsbn());
            pstmt.setDouble(4, ebook.getValorBase());
            pstmt.setDouble(5, ebook.getTamanhoArquivoMb());

            pstmt.executeUpdate();
        }
    }

    public List<Livro> buscarTodos() throws SQLException {
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(LivroConsultas.SELECT_ALL_LIVRO_FISICO)) {

            while (rs.next()) {
                LivroFisico livro =
                        new LivroFisico(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("descricao"),
                                rs.getString("isbn"),
                                rs.getDouble("valor_base"),
                                rs.getInt("numero_paginas"),
                                rs.getString("editora"));
                livros.add(livro);
            }
        }

        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(LivroConsultas.SELECT_ALL_EBOOK)) {

            while (rs.next()) {
                Ebook ebook =
                        new Ebook(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("descricao"),
                                rs.getString("isbn"),
                                rs.getDouble("valor_base"),
                                rs.getDouble("tamanho_arquivo_mb"));
                livros.add(ebook);
            }
        }

        return livros;
    }

    public Livro buscarPorId(int id, String tipo) throws SQLException {
        if ("LIVRO FÍSICO".equals(tipo)) {
            try (Connection conn = DatabaseManager.getConnection();
                    PreparedStatement pstmt =
                            conn.prepareStatement(LivroConsultas.SELECT_LIVRO_FISICO_BY_ID)) {

                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new LivroFisico(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("descricao"),
                                rs.getString("isbn"),
                                rs.getDouble("valor_base"),
                                rs.getInt("numero_paginas"),
                                rs.getString("editora"));
                    }
                }
            }
        } else {
            try (Connection conn = DatabaseManager.getConnection();
                    PreparedStatement pstmt =
                            conn.prepareStatement(LivroConsultas.SELECT_EBOOK_BY_ID)) {

                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new Ebook(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("descricao"),
                                rs.getString("isbn"),
                                rs.getDouble("valor_base"),
                                rs.getDouble("tamanho_arquivo_mb"));
                    }
                }
            }
        }
        return null;
    }

    public void atualizarLivroFisico(
            int id,
            String titulo,
            String descricao,
            String isbn,
            double valorBase,
            int numeroPaginas,
            String editora)
            throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(LivroConsultas.UPDATE_LIVRO_FISICO)) {

            pstmt.setString(1, titulo);
            pstmt.setString(2, descricao);
            pstmt.setString(3, isbn);
            pstmt.setDouble(4, valorBase);
            pstmt.setInt(5, numeroPaginas);
            pstmt.setString(6, editora);
            pstmt.setInt(7, id);

            pstmt.executeUpdate();
        }
    }

    public void atualizarEbook(
            int id,
            String titulo,
            String descricao,
            String isbn,
            double valorBase,
            double tamanhoArquivoMb)
            throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(LivroConsultas.UPDATE_EBOOK)) {

            pstmt.setString(1, titulo);
            pstmt.setString(2, descricao);
            pstmt.setString(3, isbn);
            pstmt.setDouble(4, valorBase);
            pstmt.setDouble(5, tamanhoArquivoMb);
            pstmt.setInt(6, id);

            pstmt.executeUpdate();
        }
    }

    public void deletarLivroFisico(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt =
                        conn.prepareStatement(LivroConsultas.DELETE_LIVRO_FISICO)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void deletarEbook(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(LivroConsultas.DELETE_EBOOK)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void deletar(int id, String tipo) throws SQLException {
        if ("LIVRO FÍSICO".equals(tipo)) {
            deletarLivroFisico(id);
        } else {
            deletarEbook(id);
        }
    }
}
