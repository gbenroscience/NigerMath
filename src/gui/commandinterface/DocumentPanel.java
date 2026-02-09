package gui.commandinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author GBEMIRO
 */
public class DocumentPanel extends JPanel {

    private Document document;
    JScrollPane pane = new JScrollPane();

    public DocumentPanel() {
        super(true);

    }

    private void initDocument() {
        document = new Document();

        Sentence s = new Sentence(document);
        s.setText("");
        document.appendSentence(s);
        document.setLocation(new Point(100, 200));
        document.setBgColor(Color.white);
        document.setFgColor(Color.black);
        document.setFont(new Font("Calibri", Font.BOLD, 22));
        document.setSize(new Dimension(1000, 900));
        document.setMargin(new Dimension(40, 40));
        pane.setViewportView(this);
    }

}
