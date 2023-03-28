/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package collections.list;

/**
 * An implementation of the List interface using unidirectional links, forming a "chain".
 *
 * @author Ian Clement
 */
public class LinkedList<T> implements List<T> {

    /* private inner class for link "chains" */
    private static class Link<T> {
        T element;
        Link<T> next;

        public Link() {}

        public Link(T element) {
            this.element = element;
        }
    }

    private Link<T> head;
    private Link<T> last;  // a last reference is used to make list append operations `add(x)`, `add(size(), x)` more efficient
    private int size;

    public LinkedList() {
        head = null;
        last = null;
        size = 0;
    }

    @Override
    public void add(T element) {
        // TODO

        Link<T> tmp = new Link<>(element);
       if (size > 0) {
            last.next = tmp;
            last = last.next;
        }else{
            head = last =tmp;
        }
        size++;
    }

    @Override
    public void add(int position, T element) {
        if (position < 0 || position > size) {
            throw new ListBoundsException();
        }

        // case 2: pos is size
        if(position == size){
            add(element);
            return;
        }

        // case 1 : pos is 0
        // case 3: pos is between 0 and size (pos >= 1 && pos < size)
        if(position == 0){
            Link<T> tmp = new Link<>(element);
            tmp.next = head;
            head = tmp;
        } else{

            // V1
            // current is the index we want to put it in (current = pos)
//            Link<T> current = head;
//            Link<T> previous = null;
//
//            for (int i = 0; i < position; i++) {
//                previous = current;
//                current = current.next;
//            }
//
//            Link<T> tmp = new Link<>(element);
//            previous.next = tmp;
//            tmp.next = current;
//
//            size++;

            // How ian does it:
            // current is the index before the spot we want to put it in (pos - 1)
            Link<T> current = head;
            for (int i = 0; i < position - 1; i++)
                current = current.next;

            Link<T> tmp = new Link<>(element);
            tmp.next = current.next;
            current.next = tmp;
        }

        size++;
    }

    @Override
    public T remove(int position) {
        if (position < 0 || position >= size) {
            throw new ListBoundsException();
        }

        T tmp;

        // case 1: size is 1, pos is 0 (by default due to implementation)
        if(size == 1){
            tmp = head.element;
            head = last = null;
        }
            // case 2: size is >1
        else{

            // subcase: pos is 0
            if(position == 0){
                tmp = head.element;
                head = head.next;
            }
            // subcase: pos is size-1
            else if(position == size - 1){
                tmp = last.element; // save last element to return
                Link<T> current = move(size - 2); // get before last link since remove is for last one

                current.next = null; // remove link
                last = current; // update last
            }
            else{ // position in the middle (pos is > 1 but < size-1)
                Link<T> current = move(position - 1); // get link before the one we want to remove
                tmp = current.next.element; // save element to return
                current.next = current.next.next; // remove link (delete)
            }

        }

        size--;

        return tmp;
    }

    // helper method to find linked list after a certain number of steps
    private Link<T> move(int position){

        Link<T> current = head;
        for (int i = 0; i < position; i++)
            current = current.next;

        return current;
    }

    @Override
    public void clear() {
        head = last = null;
        size = 0;
    }

    @Override
    public T get(int position) {
        if (position < 0 || position >= size) {
            throw new ListBoundsException();
        }

        // TODO
        if (position == size - 1)
            return last.element;

        Link<T> tmp = move(position);

        return tmp.element;
    }

    @Override
    public T set(int position, T element) {
        if (position < 0 || position >= size) {
            throw new ListBoundsException();
        }
        Link<T> current = move(position);
        T tmp = current.element;
        current.element = element;
        return tmp;
        // TODO
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('[');

        for (Link<T> current = head; current != null; current = current.next) {
            sb.append(current.element);

            if (current.next != null) {
                sb.append(", ");
            }
        }

        sb.append(']');

        return sb.toString();
    }

    @Override
    public void reset() {

    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }


}


     
      
