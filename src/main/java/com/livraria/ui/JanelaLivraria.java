package com.livraria.ui;

import com.livraria.dao.LivroDAO;
import com.livraria.model.Ebook;
import com.livraria.model.Livro;
import com.livraria.model.LivroFisico;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JanelaLivraria extends JFrame {
    private final LivroDAO dao = new LivroDAO();

    private JTable tabelaLivros;
    private DefaultTableModel modeloTabela;

    private JComboBox<String> comboTipo;
    private JTextField campoTitulo, campoCodigo, campoIsbn, campoValor;
    private JTextArea campoDescricao;
    private JTextField campoEspecifico1, campoEspecifico2; // Para dados específicos
    private JLabel labelEspecifico1, labelEspecifico2;

    private Livro livroSelecionado;

    public JanelaLivraria() {
        setTitle("Sistema de Livraria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        inicializarComponentes();
        carregarLivros();
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel da tabela
        painelPrincipal.add(criarPainelTabela(), BorderLayout.CENTER);

        // Painel do formulário
        painelPrincipal.add(criarPainelFormulario(), BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Livros Cadastrados"));

        String[] colunas = {"ID", "Tipo", "Título", "ISBN", "Valor Base", "Valor Final"};
        modeloTabela =
                // Desabilita a edição de ID (deve ser único e imutável)
                new DefaultTableModel(colunas, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        tabelaLivros = new JTable(modeloTabela);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaLivros
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {
                            if (!e.getValueIsAdjusting()) {
                                selecionarLivro();
                            }
                        });

        JScrollPane scroll = new JScrollPane(tabelaLivros);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder("Formulário"));

        // Linha 1: Tipo e ID
        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        linha1.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[] {"LIVRO FÍSICO", "EBOOK"});
        comboTipo.addActionListener(e -> atualizarCamposEspecificos());
        linha1.add(comboTipo);

        linha1.add(new JLabel("ID:"));
        campoCodigo = new JTextField(5);
        campoCodigo.setEditable(false);
        linha1.add(campoCodigo);

        painel.add(linha1);

        // Linha 2: Título e ISBN
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        linha2.add(new JLabel("Título:"));
        campoTitulo = new JTextField(20);
        linha2.add(campoTitulo);

        linha2.add(new JLabel("ISBN:"));
        campoIsbn = new JTextField(15);
        linha2.add(campoIsbn);

        painel.add(linha2);

        // Linha 3: Descrição
        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        linha3.add(new JLabel("Descrição:"));
        campoDescricao = new JTextArea(2, 50);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        linha3.add(new JScrollPane(campoDescricao));
        painel.add(linha3);

        // Linha 4: Valor e campos específicos
        JPanel linha4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        linha4.add(new JLabel("Valor Base:"));
        campoValor = new JTextField(10);
        linha4.add(campoValor);

        labelEspecifico1 = new JLabel("Nº Páginas:");
        campoEspecifico1 = new JTextField(10);
        linha4.add(labelEspecifico1);
        linha4.add(campoEspecifico1);

        labelEspecifico2 = new JLabel("Editora:");
        campoEspecifico2 = new JTextField(10);
        linha4.add(labelEspecifico2);
        linha4.add(campoEspecifico2);

        painel.add(linha4);

        // Linha 5: Botões
        JPanel linha5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> novo());
        linha5.add(btnNovo);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        linha5.add(btnSalvar);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(e -> deletar());
        linha5.add(btnDeletar);

        painel.add(linha5);

        return painel;
    }

    private void atualizarCamposEspecificos() {
        String tipo = (String) comboTipo.getSelectedItem();
        if ("LIVRO FÍSICO".equals(tipo)) {
            labelEspecifico1.setText("Nº Páginas:");
            labelEspecifico2.setText("Editora:");
            labelEspecifico2.setVisible(true);
            campoEspecifico2.setVisible(true);
        } else {
            labelEspecifico1.setText("Tamanho (MB):");
            labelEspecifico2.setVisible(false);
            campoEspecifico2.setVisible(false);
        }
    }

    private void carregarLivros() {
        try {
            List<Livro> livros = dao.buscarTodos();
            modeloTabela.setRowCount(0);

            for (Livro livro : livros) {
                Object[] linha = {
                    livro.getId(),
                    livro.getTipo(),
                    livro.getTitulo(),
                    livro.getIsbn(),
                    String.format("%.2f", livro.getValorBase()),
                    String.format("%.2f", livro.calcularValorFinal())
                };
                modeloTabela.addRow(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar livros: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarLivro() {
        int linha = tabelaLivros.getSelectedRow();
        if (linha >= 0) {
            try {
                int id = (Integer) modeloTabela.getValueAt(linha, 0);
                String tipo = (String) modeloTabela.getValueAt(linha, 1);

                livroSelecionado = dao.buscarPorId(id, tipo);

                if (livroSelecionado != null) {
                    campoCodigo.setText(String.valueOf(livroSelecionado.getId()));
                    comboTipo.setSelectedItem(tipo);
                    campoTitulo.setText(livroSelecionado.getTitulo());
                    campoIsbn.setText(livroSelecionado.getIsbn());
                    campoDescricao.setText(livroSelecionado.getDescricao());
                    campoValor.setText(String.valueOf(livroSelecionado.getValorBase()));

                    if (livroSelecionado instanceof LivroFisico) {
                        LivroFisico lf = (LivroFisico) livroSelecionado;
                        campoEspecifico1.setText(String.valueOf(lf.getNumeroPaginas()));
                        campoEspecifico2.setText(lf.getEditora());
                    } else if (livroSelecionado instanceof Ebook) {
                        Ebook ebook = (Ebook) livroSelecionado;
                        campoEspecifico1.setText(String.valueOf(ebook.getTamanhoArquivoMb()));
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao selecionar livro: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void novo() {
        campoCodigo.setText("");
        campoTitulo.setText("");
        campoIsbn.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
        campoEspecifico1.setText("");
        campoEspecifico2.setText("");
        comboTipo.setSelectedIndex(0);
        livroSelecionado = null;
        tabelaLivros.clearSelection();
        atualizarCamposEspecificos();
    }

    private void salvar() {
        try {
            if (campoTitulo.getText().isEmpty()
                    || campoIsbn.getText().isEmpty()
                    || campoValor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos obrigatórios!",
                        "Validação",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipo = (String) comboTipo.getSelectedItem();
            String titulo = campoTitulo.getText();
            String isbn = campoIsbn.getText();
            String descricao = campoDescricao.getText();
            double valor = Double.parseDouble(campoValor.getText());

            if (livroSelecionado == null) {
                // Novo registro
                if ("LIVRO FÍSICO".equals(tipo)) {
                    int paginas = Integer.parseInt(campoEspecifico1.getText());
                    String editora = campoEspecifico2.getText();
                    LivroFisico livro =
                            new LivroFisico(0, titulo, descricao, isbn, valor, paginas, editora);
                    dao.inserirLivroFisico(livro);
                } else {
                    double tamanho = Double.parseDouble(campoEspecifico1.getText());
                    Ebook ebook = new Ebook(0, titulo, descricao, isbn, valor, tamanho);
                    dao.inserirEbook(ebook);
                }
                JOptionPane.showMessageDialog(
                        this,
                        "Livro inserido com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Atualizar registro
                int id = livroSelecionado.getId();
                if ("LIVRO FÍSICO".equals(tipo)) {
                    int paginas = Integer.parseInt(campoEspecifico1.getText());
                    String editora = campoEspecifico2.getText();
                    dao.atualizarLivroFisico(id, titulo, descricao, isbn, valor, paginas, editora);
                } else {
                    double tamanho = Double.parseDouble(campoEspecifico1.getText());
                    dao.atualizarEbook(id, titulo, descricao, isbn, valor, tamanho);
                }
                JOptionPane.showMessageDialog(
                        this,
                        "Livro atualizado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            carregarLivros();
            novo();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Verifique os campos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        if (livroSelecionado == null) {
            JOptionPane.showMessageDialog(
                    this, "Selecione um livro para deletar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta =
                JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja deletar este livro?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(livroSelecionado.getId(), livroSelecionado.getTipo());
                JOptionPane.showMessageDialog(
                        this,
                        "Livro deletado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarLivros();
                novo();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao deletar: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
