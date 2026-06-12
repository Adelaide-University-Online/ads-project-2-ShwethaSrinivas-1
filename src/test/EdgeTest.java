/**
 * File: EdgeTest.java
 * Description: This is the test class for Edge and tests Equals, constructors and getters.
 * Author: Shwetha Srinivas
 * Student ID: A3150322
 * Email ID: a3150322@adelaide.edu.au
 * AI Tool Used: Y (To write the equals function)
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void testConstructorAndGetter() {
        Edge edge = new Edge(1, 2);
        Edge edge2 = new Edge(1, 2, 5.5);
        assertEquals(1, edge.getSource(),"getSource not working");
        assertEquals(2, edge.getDest(),"getDest not working");
        assertEquals(1.0, edge.getWeight(), "getWeight not working for default constructor");
        assertEquals(5.5, edge2.getWeight(),  "getWeight not working for constructor with weight input");
    }

    @Test
    void testEquals() {
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 2);
        Edge edge3 = new Edge(2, 3);

        // Test equality
        assertEquals(edge1, edge2, "Equals method not working for positive test case");
        // Test inequality
        assertNotEquals(edge1, edge3, "Equals method not working for Negative test case");
        // Test with different object type
        assertNotEquals(edge1, "Some String", "Edge should not be equal to a String");
    }
}