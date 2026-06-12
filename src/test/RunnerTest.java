/**
 * File: RunnerTest.java
 * Description: This class tests the recursive function from the Runner class and also uses the getNumV.
 * Author: Shwetha Srinivas
 * Student ID: A3150322
 * Email ID: a3150322@adelaide.edu.au
 * AI Tool Used: N
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunnerTest {

    @Test
    void testCalcDependantLevels() {

        ListGraph graph = new ListGraph(4, true);
        graph.insert(new Edge(0, 1));
        graph.insert(new Edge(1, 2));
        graph.insert(new Edge(1, 3));

        int[] tailSize = new int[graph.getNumV()];

        // 2. Execute: Calculate levels
        // Based on your logic: 0 depends on 1 and 2, so its level should be 3.
        Runner.calcDependantLevels(graph, 0, tailSize);

        // 3. Verify
        assertEquals(3, tailSize[0], "Vertex 0 should have a dependency depth of 3");
        assertEquals(2, tailSize[1], "Vertex 1 should have a dependency depth of 2");
        assertEquals(1, tailSize[2], "Vertex 2 should have a dependency depth of 1");
        assertEquals(1, tailSize[3], "Vertex 3 should have a dependency depth of 1");
    }

    @Test
    void testCalcDependantLevelsBranching() {
        // Setup: 0 -> 1, 0 -> 2
        ListGraph graph = new ListGraph(3, true);
        graph.insert(new Edge(0, 1));
        graph.insert(new Edge(0, 2));

        int[] tailSize = new int[3];
        Runner.calcDependantLevels(graph, 0, tailSize);

        // 0 should see 1 and 2 as depth 1, so 0 is depth 2
        assertEquals(2, tailSize[0]);
        assertEquals(1, tailSize[1]);
        assertEquals(1, tailSize[2]);
    }
}