package view;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Implementa a interface solicitada no enunciado.
 * - Janela 1500x800, não redimensionável.
 * - Barra de ferramentas com ícones, nomes e atalhos.
 * - Editor com numeração de linhas e barras de rolagem sempre visíveis.
 * - Área de mensagens somente leitura e barras de rolagem sempre visíveis.
 * - Barra de status com pasta e nome do arquivo aberto.
 * - Ações: novo, abrir, salvar, copiar, colar, recortar, compilar(F7), equipe(F1).
 * - Botão salvar com ícones dinâmicos baseados no estado do arquivo.
 */
public class MainFrame extends JFrame {

    private final JTextArea editor = new JTextArea();
    private final JTextArea areaMensagens = new JTextArea();
    private final JLabel barraStatus = new JLabel(" ");
    private File arquivoAtual = null;
    
    // Referência ao botão salvar para poder atualizá-lo dinamicamente
    private JButton btnSalvar;

    // equipe (edite os nomes aqui)
    private static final String EQUIPE = "Equipe do Compilador:\n- Mateus Spezia\n- Pedro Alegria \n- Vinícius Oneda";

    public MainFrame() {
        super("Compilador - Trabalho Final");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra de Ferramentas
        JToolBar toolBar = criarToolBar();
        add(toolBar, BorderLayout.NORTH);

        // Editor
        editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        editor.setTabSize(4);
        JScrollPane spEditor = new JScrollPane(editor);
        spEditor.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spEditor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Numeração de linhas
        LineNumberView linhas = new LineNumberView(editor);
        spEditor.setRowHeaderView(linhas);

        // Área de mensagens
        areaMensagens.setEditable(false);
        areaMensagens.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane spMsgs = new JScrollPane(areaMensagens);
        spMsgs.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spMsgs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Split
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spEditor, spMsgs);
        split.setContinuousLayout(true);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(500);
        add(split, BorderLayout.CENTER);

        // Barra de status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        statusPanel.add(barraStatus, BorderLayout.WEST);
        add(statusPanel, BorderLayout.SOUTH);

        // Atalhos de teclado globais
        configurarAtalhos();

        atualizarStatus();
    }

    private void configurarAtalhos() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "novo");
        am.put("novo", new AbstractAction() { public void actionPerformed(ActionEvent e) { acaoNovo(); } });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "abrir");
        am.put("abrir", new AbstractAction() { public void actionPerformed(ActionEvent e) { acaoAbrir(); } });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "salvar");
        am.put("salvar", new AbstractAction() { public void actionPerformed(ActionEvent e) { acaoSalvar(); } });

        // Copiar/Colar/Recortar: também já existem no JTextArea, mas deixamos globais
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), DefaultEditorKit.copyAction);
        am.put(DefaultEditorKit.copyAction, new DefaultEditorKit.CopyAction());

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), DefaultEditorKit.pasteAction);
        am.put(DefaultEditorKit.pasteAction, new DefaultEditorKit.PasteAction());

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), DefaultEditorKit.cutAction);
        am.put(DefaultEditorKit.cutAction, new DefaultEditorKit.CutAction());

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "compilar");
        am.put("compilar", new AbstractAction() { public void actionPerformed(ActionEvent e) { acaoCompilar(); } });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "equipe");
        am.put("equipe", new AbstractAction() { public void actionPerformed(ActionEvent e) { acaoEquipe(); } });
    }

    private JToolBar criarToolBar() {
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);

        JButton btnNovo = criarBotao("novo", "Novo [Ctrl+N]", "novo.png", e -> acaoNovo());
        JButton btnAbrir = criarBotao("abrir", "Abrir [Ctrl+O]", "abrir.png", e -> acaoAbrir());
        
        // Cria o botão salvar e mantém referência para atualizações dinâmicas
        btnSalvar = criarBotao("salvar", "Salvar [Ctrl+S]", "salvarComo.png", e -> acaoSalvar());
        
        JButton btnCopiar = criarBotao("copiar", "Copiar [Ctrl+C]", "copiar.png", e -> editor.copy());
        JButton btnColar = criarBotao("colar", "Colar [Ctrl+V]", "colar.png", e -> editor.paste());
        JButton btnRecortar = criarBotao("recortar", "Recortar [Ctrl+X]", "recortar.png", e -> editor.cut());
        JButton btnCompilar = criarBotao("compilar", "Compilar [F7]", "compilar.png", e -> acaoCompilar());
        JButton btnEquipe = criarBotao("equipe", "Equipe [F1]", "equipe.png", e -> acaoEquipe());

        Dimension tam = new Dimension(140, 48);
        for (JButton b : new JButton[]{btnNovo,btnAbrir,btnSalvar,btnCopiar,btnColar,btnRecortar,btnCompilar,btnEquipe}) {
            b.setFocusable(false);
            b.setPreferredSize(tam);
            bar.add(b);
        }
        return bar;
    }

    private JButton criarBotao(String name, String text, String iconName, ActionListener al) {
        JButton b = new JButton(text, carregarIcone(iconName));
        b.setName(name);
        b.addActionListener(al);
        return b;
    }

    private Icon carregarIcone(String resourceName) {
        java.net.URL url = getClass().getResource("/icons/" + resourceName);
        if (url == null) return UIManager.getIcon("FileView.fileIcon");
        ImageIcon icon = new ImageIcon(url);
        return icon;
    }
    
    /**
     * Atualiza o ícone e texto do botão salvar baseado no estado atual do arquivo
     */
    private void atualizarBotaoSalvar() {
        if (btnSalvar != null) {
            if (arquivoAtual == null) {
                // Sem arquivo - modo "Salvar Como"
                btnSalvar.setText("Salvar [Ctrl+S]");
                btnSalvar.setIcon(carregarIcone("salvarComo.png"));
                btnSalvar.setToolTipText("Salvar Como [Ctrl+S]");
            } else {
                // Com arquivo - modo "Salvar"
                btnSalvar.setText("Salvar [Ctrl+S]");
                btnSalvar.setIcon(carregarIcone("salvar.png"));
                btnSalvar.setToolTipText("Salvar [Ctrl+S]");
            }
        }
    }

    // ====== Ações ======
    private void acaoNovo() {
        editor.setText("");
        areaMensagens.setText("");
        arquivoAtual = null;
        atualizarStatus();
        atualizarBotaoSalvar(); // Atualiza o botão para "Salvar Como"
    }

    private void acaoAbrir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        // Configura filtro para aceitar apenas arquivos .txt
        javax.swing.filechooser.FileNameExtensionFilter filtroTxt = 
            new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt");
        chooser.setFileFilter(filtroTxt);
        chooser.setAcceptAllFileFilterUsed(false); // Remove a opção "All Files"
        
        int op = chooser.showOpenDialog(this);
        
        if (op == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            try {
                String conteudo = FileUtils.readFile(f);
                editor.setText(conteudo);
                editor.setCaretPosition(0);
                arquivoAtual = f;
                areaMensagens.setText(""); // Só limpa quando arquivo é aberto com sucesso
                atualizarStatus();
                atualizarBotaoSalvar(); // Atualiza o botão para "Salvar"
            } catch (Exception ex) {
                areaMensagens.setText("Erro ao abrir arquivo: " + ex.getMessage());
                atualizarStatus();
            }
        }
        // Quando op == CANCEL_OPTION ou ERROR_OPTION, não faz nada
        // Isso preserva as mensagens existentes na areaMensagens
    }

    private void acaoSalvar() {
        try {
            if (arquivoAtual == null) {
                // Modo "Salvar Como"
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                // Configura filtro para salvar apenas arquivos .txt
                javax.swing.filechooser.FileNameExtensionFilter filtroTxt = 
                    new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt");
                chooser.setFileFilter(filtroTxt);
                chooser.setAcceptAllFileFilterUsed(false); // Remove a opção "All Files"
                
                int op = chooser.showSaveDialog(this);
                if (op != JFileChooser.APPROVE_OPTION) {
                    // Se cancelou, não faz nada - mantém mensagens existentes
                    return;
                }
                arquivoAtual = chooser.getSelectedFile();
                if (!arquivoAtual.getName().toLowerCase().endsWith(".txt")) {
                    arquivoAtual = new File(arquivoAtual.getAbsolutePath() + ".txt");
                }
            }
            // Salva o arquivo (tanto no modo "Salvar" quanto "Salvar Como")
            FileUtils.writeFile(arquivoAtual, editor.getText());
            areaMensagens.setText(""); // Só limpa quando arquivo é salvo com sucesso
            atualizarStatus();
            atualizarBotaoSalvar(); // Atualiza o botão para "Salvar" após salvar pela primeira vez
        } catch (Exception ex) {
            areaMensagens.setText("Erro ao salvar arquivo: " + ex.getMessage());
        }
    }

    private void acaoCompilar() {
        areaMensagens.setText("compilação de programas ainda não implementada");
    }

    private void acaoEquipe() {
        areaMensagens.setText(EQUIPE);
    }

    private void atualizarStatus() {
        if (arquivoAtual == null) {
            barraStatus.setText(" ");
        } else {
            barraStatus.setText("  " + arquivoAtual.getParent() + "\\" + arquivoAtual.getName());
        }
    }
}