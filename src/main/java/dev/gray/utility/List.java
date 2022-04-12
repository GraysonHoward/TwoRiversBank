package dev.gray.utility;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * List interface detailing methods that should be
 * implemented and necessary details as to how where
 * needed.
 */

public interface List<T> {

    // Returns the number of elements in the list.
    int size();

    // Returns if the list is empty
    boolean isEmpty();

    // Adds an element at the end of the list
    void add(int i, T element);

    // Sets an element at a given index returns old element
    T set(int i, T element);

    // Returns element at a given index
    T get(int index);

    // Removes element at a given index and returns it
    T remove(int i);



}