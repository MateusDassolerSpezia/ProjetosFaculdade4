package view;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class LineNumberView extends JComponent implements CaretListener, DocumentListener {
    private static final long serialVersionUID = 1L;
    private static final int MARGIN = 5;
    private final JTextComponent textComponent;
    private final FontMetrics fontMetrics;
    private int lastDigits;

    public LineNumberView(JTextComponent component) {
        this.textComponent = component;
        setFont(component.getFont());
        fontMetrics = getFontMetrics(getFont());
        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        setForeground(Color.GRAY);
        setBackground(new Color(245, 245, 245));
        // força exibir o "1" logo no início
        update();
    }

    private int getLineCount() {
        Element root = textComponent.getDocument().getDefaultRootElement();
        return Math.max(1, root.getElementCount()); // garante pelo menos 1
    }

    private int getDigits() {
        int lines = Math.max(1, getLineCount());
        int digits = String.valueOf(lines).length();
        if (digits != lastDigits) {
            lastDigits = digits;
            int width = MARGIN * 5 + fontMetrics.charWidth('0') /* digits*/;
            setPreferredSize(new Dimension(width, Integer.MAX_VALUE));
            revalidate();
        }
        return digits;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle clip = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(clip.x, clip.y, clip.width, clip.height);

        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();
        int lineCount = getLineCount();
        
        // Calcula quais linhas estão visíveis na área de clipping
        int firstVisibleLine = Math.max(0, clip.y / lineHeight);
        
        // Calcula quantas linhas cabem na área visível
        int visibleLineCount = (clip.height + lineHeight - 1) / lineHeight;
        int lastVisibleLine = firstVisibleLine + visibleLineCount;
        
        // Garante que sempre mostra pelo menos as linhas visíveis na tela,
        // mesmo que o documento tenha menos linhas
        int linesToShow = Math.max(lastVisibleLine, lineCount - 1);

        // Desenha os números das linhas
        for (int lineIndex = firstVisibleLine; lineIndex <= linesToShow; lineIndex++) {
            int lineNumber = lineIndex + 1;
            
            String text = String.valueOf(lineNumber);
            int x = getWidth() - MARGIN - fm.stringWidth(text);
            int y = (lineIndex * lineHeight) + lineHeight - fm.getDescent();
            
            // Verifica se a posição Y está dentro da área visível
            if (y >= clip.y && y <= clip.y + clip.height) {
                g.setColor(getForeground());
                g.drawString(text, x, y);
            }
        }
    }

    private int getLineOfOffset(int offset) throws BadLocationException {
        Document doc = textComponent.getDocument();
        Element root = doc.getDefaultRootElement();
        return root.getElementIndex(offset);
    }

    // Listeners
    @Override
    public void caretUpdate(CaretEvent e) {
        repaint();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update();
    }

    private void update() {
        getDigits();
        repaint();
    }
}