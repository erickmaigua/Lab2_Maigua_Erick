package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.NotationController;

public class View extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfExpresion;
    private JTextArea taResultados;
    private JButton btnPostfija, btnPrefija, btnEvaluar;

    private NotationController controller = new NotationController();

    public View() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 806, 420);
        setTitle("Conversor de Notación (Infija - Postfija - Prefija)");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Panel superior
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
        taResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        contentPane.add(new JScrollPane(taResultados), BorderLayout.CENTER);

        // Eventos
        btnPostfija.addActionListener(this::accionConvertirPostfija);
        btnPrefija.addActionListener(this::accionConvertirPrefija);
        btnEvaluar.addActionListener(this::accionEvaluarPostfija);
    }

    // Evento: convertir a postfija
    private void accionConvertirPostfija(ActionEvent e) {
        String infija = tfExpresion.getText().trim();
        String resultado = controller.convertirAPostfija(infija);
        taResultados.setText(resultado);
    }

    // Evento: convertir a prefija
    private void accionConvertirPrefija(ActionEvent e) {
        String infija = tfExpresion.getText().trim();
        String resultado = controller.convertirAPrefija(infija);
        taResultados.setText(resultado);
    }

    // Evento: evaluar postfija
    private void accionEvaluarPostfija(ActionEvent e) {
        String infija = tfExpresion.getText().trim();
        String resultado = controller.evaluarPostfija(infija);
        taResultados.setText(resultado);
    }
}
