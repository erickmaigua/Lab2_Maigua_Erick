package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.NotationConverter;



public class MainWindow extends JFrame {

    private JTextField tfExpression;
    private JTextArea taResults;
    private JButton btnPostfix, btnPrefix, btnEvaluate;

    public MainWindow() {
        setTitle("Infix → Postfix → Prefix Converter");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con input y botones
        JPanel topPanel = new JPanel();
        tfExpression = new JTextField(25);
        btnPostfix = new JButton("Convert to Postfix");
        btnPrefix = new JButton("Convert to Prefix");
        btnEvaluate = new JButton("Evaluate Postfix");

        topPanel.add(new JLabel("Expression:"));
        topPanel.add(tfExpression);
        topPanel.add(btnPostfix);
        topPanel.add(btnPrefix);
        topPanel.add(btnEvaluate);

        // Área de resultados
        taResults = new JTextArea();
        taResults.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(taResults), BorderLayout.CENTER);

        // Evento para convertir a postfijo
        btnPostfix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String infix = tfExpression.getText();
                String postfix = NotationConverter.infixToPostfix(infix);
                taResults.setText("Postfix: " + postfix + "\n");
            }
        });

        // Evento para convertir a prefijo
        btnPrefix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String infix = tfExpression.getText();
                String prefix = NotationConverter.infixToPrefix(infix);
                taResults.setText("Prefix: " + prefix + "\n");
            }
        });

        // Evento para evaluar postfijo paso a paso
        btnEvaluate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String infix = tfExpression.getText();
                String postfix = NotationConverter.infixToPostfix(infix);
                taResults.setText("Postfix: " + postfix + "\n");
                double result = NotationConverter.evaluatePostfixStepByStep(postfix, taResults);
                taResults.append("\nFinal Result: " + result);
            }
        });
    }

    // Main del proyecto
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
