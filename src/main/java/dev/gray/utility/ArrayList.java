package dev.gray.utility;
/* Author: Grayson Howard
 * Modified: 04/06/2022
 * This is a fairly standard arrayList class with a few extra helper methods
 * these methods are commented to indicate their use.
 */

import java.util.Arrays;

public class ArrayList<T> implements List<T>{
    private int size = 0;
    private T[] data;

    // Initialises ArrayList of default capacity
    public ArrayList(){
        data = (T[]) new Object[16];
    }
    // Initialises ArrayList of custom capacity
    public ArrayList(int i){
        data = (T[]) new Object[i];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(int i, T element) {
        // Make sure the array is big enough
        if(size == this.data.length){
            this.data = Arrays.copyOf(this.data, this.data.length*2);
        }
        // Move data so there's room for the new element
        if (size - i >= 0) System.arraycopy(data, i, data, i + 1, size - i);
        data[i] = element;
        size++;
    }

    // Add new data at the last position
    public void addLast(T element){
        add(size,element);
    }

    @Override
    public T set(int i, T element) {
        checkIndex(i, size);
        T old = data[i];
        data[i] = element;
        return old;
    }

    @Override
    public T get(int i) {
        checkIndex(i, size);
        return data[i];
    }

    @Override
    public T remove(int i) {
        checkIndex(i,size);
        T old = data[i];
        if (size - 1 - i >= 0) System.arraycopy(data, i + 1, data, i, size - 1 - i);
        data[size-1] =null;
        size--;
        return old;
    }

    // Makes sure the index provided is in use
    private void checkIndex(int i, int s) throws IndexOutOfBoundsException {
        if (i < 0 || i >= s)
            throw new IndexOutOfBoundsException("Illegal index" + i);
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
