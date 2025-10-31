package controller;

import javax.swing.JTextArea;
import java.util.Stack;

public class NotationConverter {

    // Verifica si un caracter es un operador
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Prioridad de operadores en la expresión infija
    public static int infixPriority(char op) {
        switch (op) {
            case '^': return 4;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(': return 5;
            default: return 0;
        }
    }

    // Prioridad de operadores cuando están en la pila
    public static int stackPriority(char op) {
        switch (op) {
            case '^': return 3;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(': return 0;
            default: return 0;
        }
    }

    // Realiza la operación entre dos números
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

    // Convierte expresión infija a postfija
    public static String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

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

    // Convierte expresión infija a prefija
    public static String infixToPrefix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
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
                stack.pop(); // elimina ')'
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
        Stack<Double> stack = new Stack<>();
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
