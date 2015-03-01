package friend;

import java.util.NoSuchElementException;

public class Queue<T> {
        
        LinkedList<T> list;
        
        int cursor;
        
        public Queue() {
                list = new LinkedList<T>();
                cursor = -1;
        }
        
        public void enqueue(T item) {
                list.add(item);
        }
        
        public T dequeue() {
                if (list.isEmpty()) {
                        throw new NoSuchElementException();
                }
                return list.removeAt(0);
        }
        
        public int size() {
                return list.size();
        }
        
        public boolean isEmpty() {
                return list.isEmpty();
        }
        
        public int positionOf(T item) {
                return list.indexOf(item);
        }
        
        public void clear() {
                list.clear();
        }
        
        public void remove(T item) { 
                list.remove(item);
        }
        
        public void removeAll(T item) {
                list.removeAll(item);
        }
        
        public T first() {
                if (list.size() == 0) {
                        return null;
                }
                cursor = 0;
                return list.getAt(cursor);
        }
        
        public T next() {
                if (cursor < 0 || cursor == (list.size()-1)) {
                        return null;
                }
                cursor++;
                return list.getAt(cursor);
        }
}