package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {
    @Test
    void isWorkingPush(){
        Stack<Integer> gg=new Stack<Integer>();
        gg.push(3);
        gg.push(4);
        gg.push(5);

        assertEquals("5 4 3 ",gg.toString());
    }

    @Test
    void isWorkingRemove(){
        Stack<Integer> gg=new Stack<Integer>();
        gg.push(3);
        gg.push(4);
        gg.push(5);

        assertEquals(5,gg.remove());
        assertEquals("4 3 ",gg.toString());
    }

    @Test
    void isWorkingOverStack(){
        Stack<Integer> gg=new Stack<Integer>();
        gg.push(3);
        gg.push(4);
        gg.push(5);
        gg.overStack();
        assertEquals("3 4 5 ",gg.toString());
    }

    @Test
    void isWorkingSize(){
        Stack<Integer> gg=new Stack<Integer>();
        gg.push(3);
        gg.push(4);
        gg.push(5);
        assertEquals(3,gg.size());
    }
}