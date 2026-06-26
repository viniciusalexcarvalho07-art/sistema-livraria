package com.livraria.dao;

public class LivroConsultas {

    // LIVRO FÍSICO - CREATE TABLE
    public static final String CREATE_LIVRO_FISICO =
            "CREATE TABLE IF NOT EXISTS livro_fisico ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "titulo TEXT NOT NULL, "
                    + "descricao TEXT, "
                    + "isbn TEXT UNIQUE NOT NULL, "
                    + "valor_base REAL NOT NULL, "
                    + "numero_paginas INTEGER NOT NULL, "
                    + "editora TEXT NOT NULL"
                    + ")";

    // EBOOK - CREATE TABLE
    public static final String CREATE_EBOOK =
            "CREATE TABLE IF NOT EXISTS ebook ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "titulo TEXT NOT NULL, "
                    + "descricao TEXT, "
                    + "isbn TEXT UNIQUE NOT NULL, "
                    + "valor_base REAL NOT NULL, "
                    + "tamanho_arquivo_mb REAL NOT NULL"
                    + ")";

    // LIVRO FÍSICO - INSERT
    public static final String INSERT_LIVRO_FISICO =
            "INSERT INTO livro_fisico (titulo, descricao, isbn, valor_base, numero_paginas, editora) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

    // EBOOK - INSERT
    public static final String INSERT_EBOOK =
            "INSERT INTO ebook (titulo, descricao, isbn, valor_base, tamanho_arquivo_mb) "
                    + "VALUES (?, ?, ?, ?, ?)";

    // LIVRO FÍSICO - SELECT ALL
    public static final String SELECT_ALL_LIVRO_FISICO =
            "SELECT * FROM livro_fisico ORDER BY titulo";

    // EBOOK - SELECT ALL
    public static final String SELECT_ALL_EBOOK = "SELECT * FROM ebook ORDER BY titulo";

    // LIVRO FÍSICO - SELECT BY ID
    public static final String SELECT_LIVRO_FISICO_BY_ID =
            "SELECT * FROM livro_fisico WHERE id = ?";

    // EBOOK - SELECT BY ID
    public static final String SELECT_EBOOK_BY_ID = "SELECT * FROM ebook WHERE id = ?";

    // LIVRO FÍSICO - UPDATE
    public static final String UPDATE_LIVRO_FISICO =
            "UPDATE livro_fisico SET titulo = ?, descricao = ?, isbn = ?, valor_base = ?, "
                    + "numero_paginas = ?, editora = ? WHERE id = ?";

    // EBOOK - UPDATE
    public static final String UPDATE_EBOOK =
            "UPDATE ebook SET titulo = ?, descricao = ?, isbn = ?, valor_base = ?, "
                    + "tamanho_arquivo_mb = ? WHERE id = ?";

    // LIVRO FÍSICO - DELETE
    public static final String DELETE_LIVRO_FISICO = "DELETE FROM livro_fisico WHERE id = ?";

    // EBOOK - DELETE
    public static final String DELETE_EBOOK = "DELETE FROM ebook WHERE id = ?";
}
