/**
 * File: Edge.java
 * Description: This is the edge class build based of the expected behavior mentioned in 10.1 table of the text book.
 * Author: Shwetha Srinivas
 * Student ID: A3150322
 * Email ID: a3150322@adelaide.edu.au
 * AI Tool Used: Y (To write the equals function)
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

class Edge {

    /* Declaring variable mentioned in the table 10.1 for Edge class */
    private int dest;
    private int source;
    private double weight;

    /* Constructor for default weight */
    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
        this.weight = 1.0;
    }

    /* Constructor overload for weighted edge or leaf vertex */
    public Edge(int source, int dest, double weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }

    /* Creating equal function to compare edges */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {
            Edge edge = (Edge) o;
            return (source == edge.source && dest == edge.dest);
        } else {
            return false;
        }
    }

    public int getDest() { return dest; }

    public int getSource() { return source; }

    public double getWeight() { return weight; }

    @Override
    public int hashCode() {
        return Integer.valueOf(source).hashCode() * Integer.valueOf(dest).hashCode();
    }

    @Override
    public String toString() {
        return "(" + source + ", " + dest + "): " + weight;
    }
}