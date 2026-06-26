package com.livraria.database;

import com.livraria.dao.LivroConsultas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:livraria.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void inicializarBanco() {
        // try-with-resources: Connection e Statement são fechados automaticamente
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            stmt.execute(LivroConsultas.CREATE_LIVRO_FISICO);
            stmt.execute(LivroConsultas.CREATE_EBOOK);

            System.out.println("✓ Banco de dados inicializado com sucesso");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
        }
    }
}
