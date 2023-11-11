package org.example;

/**
 класс Stack представляет собой стек
 @param <T> тип данных элементов стека
 */
public class Stack<T> {
    /**
     класс Node представляет собой узел стека
     @param <T> тип данных элемента узла
     */
    private class Node<T>{
        /**
         информационное поле
         */
        T info;

        /**
         ссылка на следующий элемент
         */
        Node<T> next;

        /**
         конструктор узла по умолчанию
         */
        Node(){
            info=null;
            next=null;
        }

        /**
         конструктор по передаваемому значению
         @param x - значение, которое примет информационное поле info узла
         */
        Node(T x){
            info=x;
            next=null;
        }

        /**
         перегруженный метод toString класса Object
         @return символьное представление информационного поля узла
         */
        @Override
        public String toString(){

            return info.toString();
        }
    }

    /**
     узел, содержащий в себе верхний элемент стека ("голову стека")
     */
    private Node<T> top;

    /**
     количество элементов в стеке
     */
    private int size;

    /**
     конструктор стека по умолчанию
     */
    Stack(){
        top=null;
        size=0;
    }

    /**
     метод добавления элемента в стек
     @param x - добавляемое значение
     */
    public void push(T x){
        Node<T> p=new Node<T>(x);
        p.next=top;
        top=p;
        size++;
    }

    /**
     метод удаления головы стека
     @return - удаленное значение
     @throws IndexOutOfBoundsException() - список пуст
     */
    public T remove(){
        if(top!=null) {
            T x = top.info;
            top = top.next;
            size--;
            return x;
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     метод поворачивания стека в обратном направлении
     */
    public void overStack(){
        Stack<T> q=new Stack<T>();
        if(top==null||this==null){
            return;
        }
        else{
            while(top!=null){
                q.push(this.remove());
            }
            top=q.top;
        }
    }

    /**
     метод возвращающий количество элементов стека
     @return - количество элементов списка
     */
    public int size(){
        return size;
    }

    /**
     метод нахождения первого элемента стека
     @return - указатель на голову стека
     */
    public Node<T> getTop(){
        return top;
    }

    /**
     метод очищения стека (удаление всех элементов)
     */
    public void clean(){
        top=null;
        size=0;
    }

    /**
     перегруженный метод toString класса Object
     @return символьное представление элементов стека
     */
    @Override
    public String toString(){
        if (top!=null){
            T x= remove();
            String str;
            str=x+" "+ toString();
            push(x);
            return str;
        }
        else{
            return "";
        }
    }

}
