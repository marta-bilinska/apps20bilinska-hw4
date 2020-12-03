package ua.edu.ucu.queue;

public class Node {
    private Node next = null;
    private Object data = null;

    Node(Object value) {
        data = value;
    }

    Node() {

    }

    public void setNext(Node n) {
        next = n;
    }

    public Node getNext() {
        return next;
    }

    public void setData(Object value) {
        data = value;
    }

    public Object getData() {
        return data;
    }

    public boolean hasNext() {
        return next != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            return this.data.equals(((Node) obj).data);
        }
        return this.data.equals(obj);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

}