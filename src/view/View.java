package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.NotationConverter; // Asegúrate de tener esta clase en tu proyecto

public class View extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfExpresion;
    private JTextArea taResultados;
    private JButton btnPostfija, btnPrefija, btnEvaluar;

    /**
     * Inicia la aplicación
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                View frame = new View();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Crea la ventana
     */
    public View() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Panel superior con JTextField y botones
        JPanel panelSuperior = new JPanel();
        tfExpresion = new JTextField(25);
        btnPostfija = new JButton("Convertir a Postfija");
        btnPrefija = new JButton("Convertir a Prefija");
        btnEvaluar = new JButton("Evaluar Postfija");

        panelSuperior.add(new JLabel("Expresión:"));
        panelSuperior.add(tfExpresion);
        panelSuperior.add(btnPostfija);
        panelSuperior.add(btnPrefija);
        panelSuperior.add(btnEvaluar);

        contentPane.add(panelSuperior, BorderLayout.NORTH);

        // Área de resultados
        taResultados = new JTextArea();
        taResultados.setEditable(false);
        contentPane.add(new JScrollPane(taResultados), BorderLayout.CENTER);

        // Eventos de los botones
        btnPostfija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String infija = tfExpresion.getText();
                String postfija = NotationConverter.infixToPostfix(infija);
                taResultados.setText("Expresión Postfija: " + postfija + "\n");
            }
        });

        btnPrefija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String infija = tfExpresion.getText();
                String prefija = NotationConverter.infixToPrefix(infija);
                taResultados.setText("Expresión Prefija: " + prefija + "\n");
            }
        });

        btnEvaluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String infija = tfExpresion.getText();
                String postfija = NotationConverter.infixToPostfix(infija);
                taResultados.setText("Expresión Postfija: " + postfija + "\n\nEvaluación paso a paso:\n");
                double resultado = NotationConverter.evaluatePostfixStepByStep(postfija, taResultados);
                taResultados.append("\nResultado final: " + resultado);
            }
        });
    }
}
