package com.livraria;

import com.livraria.database.DatabaseManager;
import com.livraria.ui.JanelaLivraria;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar banco de dados
        DatabaseManager.inicializarBanco();

        // Inicializar interface gráfica no Event Dispatch Thread
        SwingUtilities.invokeLater(
                () -> {
                    JanelaLivraria janela = new JanelaLivraria();
                    janela.setVisible(true);
                });
    }
}
