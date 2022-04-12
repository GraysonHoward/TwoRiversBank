package dev.gray.ListTests;
/* Author: Grayson Howard
 * Modified: 04/07/2022
 * A set of tests verify correct functionality of
 * the arrayList implementation
 */

import dev.gray.utility.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListTests {

    @Test
    // Check that items can be added and size is correct
    void add_items_size(){
        ArrayList<String> names = new ArrayList<>();
        names.addLast("A");
        names.addLast("B");
        names.addLast("C");
        Assertions.assertEquals(3, names.size());
    }

    @Test
    // Check that getting an element by index returns
    // the correct element
    void get_by_index(){
        ArrayList<String> names = new ArrayList<>();
        names.addLast("A");
        names.addLast("B");
        names.addLast("C");
        String result = names.get(1);
        Assertions.assertEquals("B", result);
    }

    @Test
    // Tests if an element can be removed correctly
    void remove_element(){
        ArrayList<String> names = new ArrayList<>();
        names.addLast("A");
        names.addLast("B");
        names.addLast("C");
        String str = names.remove(2);
        Assertions.assertEquals("C", str);
        Assertions.assertEquals(2, names.size());
    }

    @Test
    // Tests that an element can be reset correctly
    void set_element(){
        ArrayList<String> names = new ArrayList<>();
        names.addLast("A");
        names.addLast("B");
        names.addLast("C");
        String str = names.set(2,"Q");
        Assertions.assertEquals("C", str);
        Assertions.assertEquals("Q", names.get(2));
    }

    @Test
    // Tests the dynamic resizing function of the arrayList
    void many_adds(){
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i <17; i++){
            names.addLast("hello");
        }
        System.out.println(names.toString());
        Assertions.assertEquals(17,names.size());
    }

}