package controller;

import javax.swing.JOptionPane;
import model.Stack;
import java.util.StringTokenizer;

public class NotationController {

    // ===================== MÉTODOS PRINCIPALES =====================

    // Convierte infija a postfija
    public String convertirAPostfija(String infija) {
        if (!validarExpresion(infija)) return "";
        try {
            String postfija = infixToPostfix(infija);
            return "Expresión Postfija:\n" + postfija;
        } catch (Exception e) {
            mostrarError("Error al convertir a notación postfija. Verifique la sintaxis.");
            return "";
        }
    }

    // Convierte infija a prefija
    public String convertirAPrefija(String infija) {
        if (!validarExpresion(infija)) return "";
        try {
            String prefija = infixToPrefix(infija);
            return "Expresión Prefija:\n" + prefija;
        } catch (Exception e) {
            mostrarError("Error al convertir a notación prefija. Verifique la expresión.");
            return "";
        }
    }

    // Evalúa una expresión postfija paso a paso
    public String evaluarPostfija(String infija) {
        if (!validarExpresion(infija)) return "";
        if (infija.matches(".*[a-zA-Z].*")) {
            mostrarError("No se puede evaluar una expresión con variables.");
            return "";
        }

        try {
            String postfija = infixToPostfix(infija);
            StringBuilder pasos = new StringBuilder("Expresión Postfija: " + postfija + "\n\nEvaluación paso a paso:\n");
            double resultado = evaluatePostfixStepByStep(postfija, pasos);
            pasos.append("\nResultado final: ").append(resultado);
            return pasos.toString();
        } catch (Exception e) {
            mostrarError("Error al evaluar la expresión. Verifique la sintaxis.");
            return "";
        }
    }

    // ===================== VALIDACIONES =====================

    private boolean validarExpresion(String expresion) {
        if (expresion == null || expresion.isEmpty()) {
            mostrarError("Por favor, ingrese una expresión antes de continuar.");
            return false;
        }

        // Solo se permiten letras, números, operadores, paréntesis, puntos y espacios
        if (!expresion.matches("[a-zA-Z0-9+\\-*/^()\\s\\.]+")) {
            mostrarError("La expresión contiene caracteres inválidos.");
            return false;
        }

        // Verificar balance de paréntesis
        int balance = 0;
        for (char c : expresion.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) {
                mostrarError("Paréntesis mal colocados.");
                return false;
            }
        }
        if (balance != 0) {
            mostrarError("Paréntesis desbalanceados.");
            return false;
        }

        // Validar operadores consecutivos
        if (expresion.matches(".*[+\\-*/^]{2,}.*")) {
            mostrarError("Hay operadores consecutivos o mal ubicados.");
            return false;
        }

        return true;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ===================== LÓGICA DE NOTACIÓN =====================

    public boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    public int prioridadInfija(char op) {
        switch (op) {
            case '^': return 4;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(' : return 5;
            default: return 0;
        }
    }

    public int prioridadPila(char op) {
        switch (op) {
            case '^': return 3;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(' : return 0;
            default: return 0;
        }
    }

    public double operar(double a, char op, double b) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) {
                    mostrarError("Error: división por cero.");
                    return 0;
                }
                return a / b;
            case '^': return Math.pow(a, b);
            default: return 0;
        }
    }

    // ===================== CONVERSIÓN A POSTFIJA =====================
    public String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        char[] chars = infix.replaceAll("\\s+", "").toCharArray();

        String number = "";

        for (char c : chars) {
            if (Character.isDigit(c) || c == '.') {
                number += c;
            } else {
                if (!number.isEmpty()) {
                    output.append(number).append(' ');
                    number = "";
                }

                if (Character.isLetter(c)) {
                    output.append(c).append(' ');
                } else if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        output.append(stack.pop()).append(' ');
                    }
                    if (!stack.isEmpty()) stack.pop();
                } else if (esOperador(c)) {
                    while (!stack.isEmpty() && prioridadPila(stack.peek()) >= prioridadInfija(c)) {
                        output.append(stack.pop()).append(' ');
                    }
                    stack.push(c);
                }
            }
        }

        if (!number.isEmpty()) {
            output.append(number).append(' ');
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
        }

        return output.toString().trim();
    }

    // ===================== CONVERSIÓN A PREFIJA =====================
    public String infixToPrefix(String infix) {
        StringBuilder reversed = new StringBuilder(infix).reverse();
        String swapped = reversed.toString()
                .replace('(', '☼').replace(')', '(').replace('☼', ')');
        String postfix = infixToPostfix(swapped);
        return new StringBuilder(postfix).reverse().toString();
    }

 // ===================== EVALUAR POSTFIJA PASO A PASO =====================
    public double evaluatePostfixStepByStep(String postfix, StringBuilder pasos) {
        Stack<Double> stack = new Stack<>();
        StringTokenizer tokens = new StringTokenizer(postfix, " ");

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if (token.matches("[0-9\\.]+")) {
                stack.push(Double.parseDouble(token));
            } else if (token.length() == 1 && esOperador(token.charAt(0))) {
                if (stack.isEmpty()) {
                    mostrarError("Error: faltan operandos antes del operador " + token);
                    return 0;
                }
                double b = stack.pop();

                if (stack.isEmpty()) {
                    mostrarError("Error: faltan operandos antes del operador " + token);
                    return 0;
                }
                double a = stack.pop();

                double result = operar(a, token.charAt(0), b);
                stack.push(result);
                pasos.append(String.format("%f %c %f = %f\n", a, token.charAt(0), b, result));
            }
        }

        // Verificación final
        double resultadoFinal = 0;

        if (!stack.isEmpty()) {
            resultadoFinal = stack.pop();
            if (!stack.isEmpty()) {
                mostrarError("Expresión postfija inválida: sobran operandos.");
                return 0;
            }
        } else {
            mostrarError("Expresión postfija inválida: faltan operandos.");
            return 0;
        }

        return resultadoFinal;
    }


    }

