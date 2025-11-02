package model;

public class Stack<T> {
    private Node<T> top;

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
    }

    public T pop() {
        if (isEmpty()) throw new RuntimeException("Pila vacía");
        T data = top.data;
        top = top.next;
        return data;
    }

    public T peek() {
        if (isEmpty()) throw new RuntimeException("Pila vacía");
        return top.data;
    }
}
