package ru.kuzmina.paretoOpt;

import ru.kuzmina.paretoOpt.form.InputFunctionalPanel;
import ru.kuzmina.paretoOpt.form.NMPanel;
import ru.kuzmina.paretoOpt.form.SwingConsole;
import ru.kuzmina.paretoOpt.optimize.Optimizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Application extends JFrame {
    private static Logger log = Logger.getLogger(Application.class.getName());

    private JLabel label = new JLabel();
    private JButton optimize;
    private JButton refresh;
    private NMPanel nmPanel;
    private JRadioButton withRandom;
    private JRadioButton withoutRandom;
    private InputFunctionalPanel x;
    private InputFunctionalPanel y;
    private InputFunctionalPanel g1;
    private InputFunctionalPanel g2;

    public Application() {
        super("Парето оптимизация");

        nmPanel = new NMPanel();

        JPanel selectPanel = new JPanel();
        withRandom = new JRadioButton("Сгенерировать");
        withoutRandom = new JRadioButton("Ввести");
        selectPanel.add(withRandom);
        selectPanel.add(withoutRandom);

        x = new InputFunctionalPanel("Введите x");
        x.setVisible(false);

        y = new InputFunctionalPanel("Введите y");
        y.setVisible(false);

        g1 = new InputFunctionalPanel("Введите g1");
        g1.setVisible(false);

        g2 = new InputFunctionalPanel("Введите g2");
        g2.setVisible(false);

        optimize = new JButton("Найти решение");
        refresh = new JButton("Сбросить");

        optimize.addActionListener(event -> {
            SwingUtilities.invokeLater(() -> {
                Optimizer optimizer = new Optimizer(nmPanel.getN(), nmPanel.getM());
                if (withRandom.isSelected()) {
                    optimizer.generate();
                } else {
                    optimizer.setG1(g1.getListValues());
                    optimizer.setG2(g2.getListValues());
                    optimizer.setX(x.getValues());
                    optimizer.setY(y.getValues());
                }
                List<Set<Integer>> paretoSet = optimizer.findParetoSet();
                log.info("Founded Pareto optimal: " + paretoSet);
                label.setText("Ответ: " + optimizer.toString());
            });
        });

        refresh.addActionListener(event -> {
            SwingUtilities.invokeLater(() -> {
                x.remove();
                y.remove();
                g1.remove();
                g2.remove();
                nmPanel.clear();
                withoutRandom.setSelected(false);
                withRandom.setSelected(false);
                label.setText("");
            });
        });

        setLayout(new FlowLayout());
        add(nmPanel, BorderLayout.CENTER);
        add(selectPanel);
        add(x);
        add(y);
        add(g1);
        add(g2);
        add(optimize);
        add(refresh);
        add(label);

        withoutRandom.addActionListener(event -> {
            SwingUtilities.invokeLater(() -> {
                if (withoutRandom.isSelected()) {
                    withRandom.setSelected(false);
                    x.create(nmPanel.getN());
                    y.create(nmPanel.getM());
                    g1.create(nmPanel.getN(), nmPanel.getM());
                    g2.create(nmPanel.getN(), nmPanel.getM());
                } else {
                    x.remove();
                    y.remove();
                    g1.remove();
                    g2.remove();
                }
            });
        });

        withRandom.addActionListener(event -> {
            SwingUtilities.invokeLater(() -> {
                if (withoutRandom.isSelected()) {
                    withoutRandom.setSelected(false);
                    x.remove();
                    y.remove();
                    g1.remove();
                    g2.remove();
                }
            });
        });
    }

    private static Application application = new Application();

    public static void main(String[] args) {
        SwingConsole.run(application, 300, 300);
    }

}
