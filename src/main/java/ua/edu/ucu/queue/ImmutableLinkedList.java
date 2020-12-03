package ua.edu.ucu.queue;


public class ImmutableLinkedList implements ImmutableList {
    private final Node head;
    private int length;

    public ImmutableLinkedList() {
        head = null;
        length = 0;
    }

    public ImmutableLinkedList(Object[] lst) {
        length = lst.length;
        if (length < 1) {
            head = null;
            length = 0;
        } else {
            Node start = new Node();
            Node current = start;
            for (Object item : lst) {
                current.setNext(new Node(item));
                current = current.getNext();
            }
            head = start.getNext();
        }
    }

    private ImmutableLinkedList(Node nodeHead, int len) {
        head = nodeHead;
        length = len;
    }

    public void checkIndex(int index) {
        if (index >= length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Object checkNode(Node node) {
        if (node != null) {
            return node.getData();
        } else {
            return null;
        }
    }

    public static boolean checkCopyingIndex(int length, int start, int end) {
        return start >= 0 && end > start && end <= length;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return addAll(length, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(length, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (c.length < 1) {
            return copy(0, length);
        }
        ImmutableLinkedList newList = copy(0, index);
        ImmutableLinkedList addedList = new ImmutableLinkedList(c);
        ImmutableLinkedList remainingList = copy(index, length);
        Node last;
        int len = newList.length + addedList.length + remainingList.length;
        Node bridge = addedList.getNode(c.length - 1);
        if (index < 1) {
            newList = addedList;
        } else {
            last = newList.getNode(index - 1);
            last.setNext(addedList.head);
        }
        bridge.setNext(remainingList.head);
        newList.length = len;
        return newList;
    }

    public Node getNode(int index) {
        checkIndex(index);
        Node currentNode = head;
        int i = 0;
        while (i < index && currentNode != null) {
            currentNode = currentNode.getNext();
            i += 1;
        }
        return currentNode;
    }

    @Override
    public Object get(int index) {
        return getNode(index).getData();
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        checkIndex(index);
        if (index == 0) {
            return removeFirst();
        }
        if (index == length - 1) {
            return removeLast();
        }
        ImmutableLinkedList newList = copy(0, length);
        Node prev = newList.getNode(index - 1);
        Node node = newList.getNode(index);
        Node next = node.getNext();
        prev.setNext(next);
        return new ImmutableLinkedList(newList.head, length - 1);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        ImmutableLinkedList newList = copy(0, length);
        Node node = newList.getNode(index);
        node.setData(e);
        return newList;
    }

    @Override
    public int indexOf(Object e) {
        Node current = head;
        int index = 0;
        while (index < length && current != null) {
            if (!current.equals(e)) {
                current = current.getNext();
                index += 1;
            } else {
                break;
            }
        }
        if (current == null) {
            return -1;
        }
        return index;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return length < 1;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[length];
        Node current = head;
        int i = 0;
        while (current != null) {
            array[i] = current.getData();
            current = current.getNext();
            i += 1;
        }
        return array;
    }

    public ImmutableLinkedList copy(int startIndex, int endIndex) {
        int listLength = this.length;
        if (!checkCopyingIndex(listLength, startIndex, endIndex)) {
            return new ImmutableLinkedList();
        }
        int currentIndex = 0;
        Node current = this.head;
        while (currentIndex < startIndex) {
            current = current.getNext();
            currentIndex += 1;
        }

        Node newHead = new Node(current.getData());
        Node resultCurrent = newHead;
        while (current.hasNext() && currentIndex < endIndex) {
            current = current.getNext();
            resultCurrent.setNext(new Node(current.getData()));
            resultCurrent = resultCurrent.getNext();
            currentIndex += 1;
        }
        return new ImmutableLinkedList(newHead, endIndex - startIndex);
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return checkNode(head);
    }

    public Object getLast() {
        return get(length - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return new ImmutableLinkedList(head.getNext(), length - 1);

    }

    public ImmutableLinkedList removeLast() {
        ImmutableLinkedList newList = copy(0, length);
        Node tail = newList.getNode(length - 2);
        tail.setNext(null);
        return new ImmutableLinkedList(newList.head, length - 1);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Node current = head;
        if (current == null) {
            return "";
        }
        result.append(current.getData());
        while (current.hasNext()) {
            result.append(", ");
            current = current.getNext();
            result.append(current.getData());
        }
        return result.toString();
    }


}
