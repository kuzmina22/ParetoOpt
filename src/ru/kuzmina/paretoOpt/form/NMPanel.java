package ru.kuzmina.paretoOpt.form;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import java.awt.FlowLayout;

public class NMPanel extends JPanel {

    private JTextField forN;
    private JTextField forM;

    public NMPanel() {
        JLabel labelN = new JLabel("Введите n:");
        forN = new JTextField(5);
        ((AbstractDocument) forN.getDocument()).setDocumentFilter(new InputFilter(InputFilter.INT_REGEX));

        JLabel labelM = new JLabel("Введите m:");
        forM = new JTextField(5);
        ((AbstractDocument) forM.getDocument()).setDocumentFilter(new InputFilter(InputFilter.INT_REGEX));

        setLayout(new FlowLayout());
        add(labelN);
        add(forN);
        add(labelM);
        add(forM);
    }

    public Integer getN() {
        return Integer.parseUnsignedInt(forN.getText());
    }

    public Integer getM() {
        return Integer.parseUnsignedInt(forM.getText());
    }

    public void clear() {
        forM.setText("");
        forN.setText("");
    }

}
