package controller;

import javax.swing.JTextArea;
import model.StackCustom;

public class NotationConverter {

    // Verifica si un caracter es operador
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Prioridad en la expresión infija
    public static int infixPriority(char op) {
        switch (op) {
            case '^': return 4;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(': return 5;
            default: return 0;
        }
    }

    // Prioridad de operadores en la pila
    public static int stackPriority(char op) {
        switch (op) {
            case '^': return 3;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(': return 0;
            default: return 0;
        }
    }

    // Operación entre dos números
    public static double operate(double a, char op, double b) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '^': return Math.pow(a, b);
            default: return 0;
        }
    }

    // Convierte expresión infija a postfija usando StackCustom
    public static String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        StackCustom<Character> stack = new StackCustom<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                stack.pop(); // elimina '('
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && stackPriority(stack.peek()) >= infixPriority(c)) {
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }

        return output.toString();
    }

    // Convierte expresión infija a prefija usando StackCustom
    public static String infixToPrefix(String expression) {
        StringBuilder output = new StringBuilder();
        StackCustom<Character> stack = new StackCustom<>();
        expression = new StringBuilder(expression).reverse().toString();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } else if (c == ')') {
                stack.push(c);
            } else if (c == '(') {
                while (!stack.isEmpty() && stack.peek() != ')') {
                    output.append(stack.pop());
                }
                stack.pop();
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && stackPriority(stack.peek()) > infixPriority(c)) {
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }

        return output.reverse().toString();
    }

    // Evalúa la expresión postfija paso a paso y la muestra en JTextArea
    public static double evaluatePostfixStepByStep(String expression, JTextArea textArea) {
        StackCustom<Double> stack = new StackCustom<>();
        textArea.append("Evaluación paso a paso:\n");

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                stack.push((double)(c - '0'));
            } else if (isOperator(c)) {
                double b = stack.pop();
                double a = stack.pop();
                double result = operate(a, c, b);
                stack.push(result);
                textArea.append(a + " " + c + " " + b + " = " + result + "\n");
            }
        }

        return stack.pop();
    }
}
