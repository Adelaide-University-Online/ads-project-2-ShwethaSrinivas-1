/**
 * File: AbstractGraph.java
 * Description: Abstract base class for graphs. A graph is a set of vertices and
 *              a set of edges. Vertices are represented by integers
 *              from 0 to n ‐ 1. Edges are ordered pairs of vertices.
 * Author: Shwetha Srinivas
 * Student ID: A3150322
 * Email ID: a3150322@adelaide.edu.au
 * AI Tool Used: N
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

import java.util.*;
import java.io.*;


public abstract class AbstractGraph implements Graph {
// Data Fields
    /** The number of vertices */
    private int numV;
    /** Flag to indicate whether this is a directed graph */
    private boolean directed;
// Constructor
    /** Construct a graph with the specified number of vertices and the directed
     flag. If the directed flag is true, this is a directed graph.
     @param numV The number of vertices
     @param directed The directed flag
     */
    public AbstractGraph(int numV, boolean directed) {
        this.numV = numV;
        this.directed = directed;
    }
// Accessor Methods
    /** Return the number of vertices.
     @return The number of vertices
     */
    public int getNumV() {
        return numV;
    }
    /** Return whether this is a directed graph.
     @return true if this is a directed graph
     */
    public boolean isDirected() {
        return directed;
    }
}