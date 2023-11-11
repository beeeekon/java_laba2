package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void evalPlus(){
        Calculator s=new Calculator("15+3");

        assertEquals(0,18-s.evaluateInfix());
    }

    @Test
    void evalMinus(){
        Calculator s=new Calculator("15-3");

        assertEquals(0,12-s.evaluateInfix());
    }

    @Test
    void evalProd(){
        Calculator s=new Calculator("15*3");

        assertEquals(0,45-s.evaluateInfix());
    }

    @Test
    void evalPart(){
        Calculator s=new Calculator("15/3");

        assertEquals(0,5-s.evaluateInfix());
    }

    @Test
    void evalPlusPlus(){
        Calculator s=new Calculator("15+(30+15)");

        assertEquals(0,60-s.evaluateInfix());
    }

    @Test
    void evalPlusMinus(){
        Calculator s=new Calculator("15+(30-15)");

        assertEquals(0,30-s.evaluateInfix());
    }

    @Test
    void evalPlusProd(){
        Calculator s=new Calculator("15+(30*15)");

        assertEquals(0,465-s.evaluateInfix());
    }

    @Test
    void evalPlusPart(){
        Calculator s=new Calculator("15+(30/15)");

        assertEquals(0,17-s.evaluateInfix());
    }

}