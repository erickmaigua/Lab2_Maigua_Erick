package model;

// Pila usando nodos
public class StackCustom<T> {
    private Node<T> top;

    public StackCustom() {
        top = null;
    }

    // Verifica si la pila está vacía
    public boolean isEmpty() {
        return top == null;
    }

    // Inserta un elemento en la pila
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(top);
        top = newNode;
    }

    // Saca el elemento superior de la pila
    public T pop() {
        if (isEmpty()) throw new RuntimeException("La pila está vacía");
        T data = top.getData();
        top = top.getNext();
        return data;
    }

    // Devuelve el elemento superior sin sacarlo
    public T peek() {
        if (isEmpty()) throw new RuntimeException("La pila está vacía");
        return top.getData();
    }
}
