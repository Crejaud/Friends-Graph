 package friend;

import java.util.NoSuchElementException;

class Node<T> {
        
        T data;
        
        Node<T> next;
        
        Node(T dat) {
                data = dat;
                next = null;
        }
}

public class LinkedList<T> {
        
        
        Node<T> tail;   
        
        int count;   
        
        public LinkedList() {
                tail = null;
                count = 0;
        }
        
        public void add(T item) {
                Node<T> itemnode = new Node<T>(item);
                
                if (count == 0) { // empty list
                        itemnode.next = itemnode;
                } else {
                        itemnode.next = tail.next;
                        tail.next = itemnode;
                }
                tail = itemnode;
                count++;
        }
        
        public void insertAt(T item, int index) {
                if (index < 0 || index >= count) {
                        throw new IndexOutOfBoundsException(index + " < 0 or >= " + count);
                }
                
                // find predecessor
                Node<T> pred = tail;
                for (int i=0; i < index; i++) {
                        pred = pred.next;
                }
                
                // insert after pred        
                Node<T> itemnode = new Node<T>(item);
                itemnode.next = pred.next;
                pred.next = itemnode;
                count++;
        }
        
        public void remove(T item) {
                int i = indexOf(item);
                if (i == -1) {
                        throw new NoSuchElementException();
                }
                removeAt(i);
        }
        
        public T removeAt(int index) {
                if (index < 0 || index >= count) {
                        throw new IndexOutOfBoundsException(index + " < 0 or >= " + count);
                }
                T ret=null;
                if (index == 0) {
                        ret = tail.next.data;
                        if (count == 1) { // single element, special case
                                tail = null;
                        } else {  // at least two entries
                                tail.next = tail.next.next;
                        }
                        count--;
                } else { // at least two entries, and index > 0   
                        // find the entry just prior to index
                        Node<T> prev=tail.next;
                        for (int i=0; i < index-1; i++) {
                                prev = prev.next;
                        }
                        // remove after prev
                        Node<T> curr = prev.next;
                        ret = curr.data;  // save for return 
                        prev.next = curr.next;
                        curr.next = null;
                        count--;
                        if (curr == tail) {
                                tail = prev;
                        }
                }
                return ret;
        }
        
        public void removeAll(T item) {
                if (count == 0) {
                        throw new NoSuchElementException();
                }
                
                // step through all entries
                Node<T> prev=tail, curr=tail.next;
                int oldcount = count;
                for (int i=0; i < oldcount; i++) {
                        if (item.equals(curr.data)) {
                                prev.next = curr.next;
                                curr.data = null;
                                curr.next = null;
                                count--;
                        } else {
                                prev = curr;
                        }
                        curr = prev.next;
                }
                if (count == oldcount) { // no match
                        throw new NoSuchElementException();
                } 
                if (count == 0) {
                        tail = null;
                } else {
                        tail = prev;
                }
        }
        
        public void clear() {
                tail = null;
                count = 0;
        }
        
        public void setAt(T item, int index) {
                if (index < 0 || index >= count) {
                        throw new IndexOutOfBoundsException(index + " < 0 or >= " + count);
                }
                Node<T> curr=tail.next;
                for (int i=0; i < index; i++) {
                        curr = curr.next;
                }
                curr.data = item; // update
        }
        
        public T getAt(int index) { 
                if (index < 0 || index >= count) { 
                        throw new IndexOutOfBoundsException(index + " < 0 or >= " + count); 
                }
                // skip over intervening nodes 
                Node<T> curr=tail.next; 
                for (int i=0; i < index; i++) { 
                        curr = curr.next; 
                } 
                return curr.data; 
        }
        
        public int indexOf(T item) {
                if (count == 0) {
                        return -1;
                }
                
                // step through list nodes
                Node<T> curr=tail.next;
                for (int i=0; i < count; i++) {
                        if (item.equals(curr.data)) {
                                return i;
                        }
                        curr = curr.next;
                }
                return -1;
        }
        
        public int size() {
                return count;
        }
        
        public boolean isEmpty() {
                return count == 0;
        }
}