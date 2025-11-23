import java.util.Arrays;
import java.util.EmptyStackException;

// 1. Интерфейс стека
interface Stack<T> {
    /**
     * Добавляет элемент на вершину стека
     * @param element элемент для добавления
     */
    void push(T element);
    
    /**
     * Удаляет и возвращает элемент с вершины стека
     * @return элемент с вершины стека
     * @throws EmptyStackException если стек пуст
     */
    T pop();
    
    /**
     * Возвращает элемент с вершины стека без удаления
     * @return элемент с вершины стека
     * @throws EmptyStackException если стек пуст
     */
    T peek();
    
    /**
     * Проверяет, пуст ли стек
     * @return true если стек пуст, false в противном случае
     */
    boolean isEmpty();
    
    /**
     * Возвращает количество элементов в стеке
     * @return количество элементов
     */
    int size();
    
    /**
     * Очищает стек
     */
    void clear();
}

// 2. Реализация на основе массива
class ArrayStack<T> implements Stack<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROW_FACTOR = 1.5;
    
    private T[] elements;
    private int size;
    
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        this.elements = (T[]) new Object[initialCapacity];
        this.size = 0;
    }
    
    @Override
    public void push(T element) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
    }
    
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T element = elements[--size];
        elements[size] = null; // Помощь сборщику мусора
        return element;
    }
    
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[size - 1];
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = (int) (elements.length * GROW_FACTOR);
        T[] newElements = (T[]) new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Stack: []";
        }
        
        StringBuilder sb = new StringBuilder("Stack: [");
        for (int i = size - 1; i >= 0; i--) {
            sb.append(elements[i]);
            if (i > 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

// 3. Реализация на основе связного списка
class LinkedStack<T> implements Stack<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node<T> top;
    private int size;
    
    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }
    
    @Override
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        
        T element = top.data;
        top = top.next;
        size--;
        return element;
    }
    
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }
    
    @Override
    public boolean isEmpty() {
        return top == null;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        top = null;
        size = 0;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Stack: []";
        }
        
        StringBuilder sb = new StringBuilder("Stack: [");
        Node<T> current = top;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}

// 4. Демонстрация работы
public class StackDemo {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация ArrayStack ===");
        demoArrayStack();
        
        System.out.println("\n=== Демонстрация LinkedStack ===");
        demoLinkedStack();
        
        System.out.println("\n=== Демонстрация исключений ===");
        demoExceptions();
    }
    
    private static void demoArrayStack() {
        Stack<Integer> stack = new ArrayStack<>();
        
        // Добавление элементов
        System.out.println("Добавляем элементы: 10, 20, 30");
        stack.push(10);
        stack.push(20);
        stack.push(30);
        
        System.out.println("Стек: " + stack);
        System.out.println("Размер: " + stack.size());
        System.out.println("Вершина: " + stack.peek());
        
        // Удаление элементов
        System.out.println("\nУдаляем элементы:");
        while (!stack.isEmpty()) {
            System.out.println("Извлечено: " + stack.pop());
            System.out.println("Текущий стек: " + stack);
        }
    }
    
    private static void demoLinkedStack() {
        Stack<String> stack = new LinkedStack<>();
        
        // Добавление элементов
        System.out.println("Добавляем строки");
        stack.push("Java");
        stack.push("Stack");
        stack.push("Implementation");
        
        System.out.println("Стек: " + stack);
        System.out.println("Размер: " + stack.size());
        System.out.println("Вершина: " + stack.peek());
        
        // Очистка и проверка
        stack.clear();
        System.out.println("\nПосле очистки:");
        System.out.println("Пуст ли стек: " + stack.isEmpty());
        System.out.println("Размер: " + stack.size());
    }
    
    private static void demoExceptions() {
        Stack<Integer> stack = new ArrayStack<>();
        
        try {
            stack.pop(); // Попытка извлечь из пустого стека
        } catch (EmptyStackException e) {
            System.out.println("Поймано исключение: " + e.getClass().getSimpleName());
        }
        
        try {
            stack.peek(); // Попытка посмотреть вершину пустого стека
        } catch (EmptyStackException e) {
            System.out.println("Поймано исключение: " + e.getClass().getSimpleName());
        }
    }
}