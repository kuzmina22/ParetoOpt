package ru.kuzmina.paretoOpt.form;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.List;

public class InputFunctionalPanel extends JPanel {

    private List<JPanel> inputPanels = new ArrayList<>();
    private List<JTextField> fields = new ArrayList<>();
    private int n = 0;
    private int m = 0;

    public InputFunctionalPanel(String title) {
        setBorder(BorderFactory.createTitledBorder(title));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void remove() {
        for (JPanel panel : inputPanels) {
            remove(panel);
        }
        //revalidate();
        //repaint();
        setVisible(false);
    }

    public void create(int n) {
        this.n = n;
        JPanel iPanel = new JPanel();
        iPanel.setLayout(new BoxLayout(iPanel, BoxLayout.X_AXIS));
        for (int i = 0; i < n; i++) {
            JTextField jField = new JTextField();
            ((AbstractDocument) jField.getDocument()).setDocumentFilter(new InputFilter(InputFilter.DOUBLE_REGEX));
            jField.setColumns(5);
            iPanel.add(jField);
            fields.add(jField);
        }
        inputPanels.add(iPanel);
        add(iPanel);
        setVisible(true);
    }

    public void create(int n, int m) {
        this.n = n;
        this.m = m;
        for (int i = 0; i < n; i++) {
            JPanel iPanel = new JPanel();
            iPanel.setLayout(new BoxLayout(iPanel, BoxLayout.X_AXIS));
            for (int j = 0; j < m; j++) {
                JTextField jField = new JTextField();
                ((AbstractDocument) jField.getDocument()).setDocumentFilter(new InputFilter(InputFilter.DOUBLE_REGEX));
                jField.setColumns(5);
                iPanel.add(jField);
                fields.add(jField);
            }
            inputPanels.add(iPanel);
            add(iPanel);
        }
        setVisible(true);
    }

    public List<Double> getValues() {
        List<Double> values = new ArrayList<>();
        for (JTextField f : fields) {
            values.add(Double.parseDouble(f.getText()));
        }
        return values;
    }

    public List<List<Double>> getListValues() {
        List<List<Double>> values = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Double> val = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                val.add(Double.parseDouble(fields.get(m * i + j).getText()));
            }
            values.add(val);
        }
        return values;
    }
}
