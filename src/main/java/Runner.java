/**
 * File: Runner.java
 * Description: This class has the main function. It takes user input for file path and number of concurrent subjects user can pick.
 * Author: Shwetha Srinivas
 * Student ID: A3150322
 * Email ID: a3150322@adelaide.edu.au
 * AI Tool Used: Y (Used chatGPT to get the list sorting command based on dependantLevels and printing format for output)
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class Runner {

    public static void main(String[] args) throws IOException {

        /*
          Printing the welcome screen and getting user inputs.
          First asking for file path and reading it.
         */
        System.out.println("==============================");
        System.out.println("           OptiTime");
        System.out.println("==============================");

        /*
          Variables declaration
         */
        int concurrentStudy = 0;
        String verticesLine;
        String[] courses = null;
        ListGraph courseGraph;
        int numV = 0;
        LinkedList<String> prereqLines = new LinkedList<>();


        // Getting path from user and reading the file data
        boolean fileRead = false;

        while (!fileRead) {

            String filePath = JOptionPane.showInputDialog("Enter the file path with file name:");
            // Adding null check to allow cancelling
            if (filePath == null) return;

            filePath = filePath.replace("\"", "");

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                // Read first line and create array
                verticesLine = br.readLine();
                courses = verticesLine.split(",\\s*");

                numV = courses.length;

                // Reading the prerequisite lines
                String prereqLine;
                while ((prereqLine = br.readLine()) != null) {
                    prereqLines.add(prereqLine);
                }
                fileRead = true;
            } catch (FileNotFoundException e) {
                System.out.println("Invalid file path. Provide a valid path" + "\n" + e.getMessage()); // Updated later to filenotfound to show the right error to user
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());  // Updated later as file reading logic is with in the try block
            }
        }

        courseGraph = new ListGraph(numV, true);

        /*
          Creating hashmap for the vertices stored in courses array
         */

        HashMap<String, Integer> courseIndex = new HashMap<>();
        for (int i = 0; i < numV; i++) {
            courseIndex.put(courses[i], i);
        }

        /*
          Processing Prerequisite lines and adding Edges to Graph
          Also storing the prereqCount to use as countdown and flag when course is available to add(Updated later)
         */
        int[] prereqCount = new int[numV];
        for (String line : prereqLines) {
            String[] coursesInLine = line.split(",\\s*");
            Integer childIndex = courseIndex.get(coursesInLine[0]);
            for (int i = 1; i < coursesInLine.length; i++) {
                Integer prereqIndex = courseIndex.get(coursesInLine[i].trim());
                courseGraph.insert(new Edge(prereqIndex, childIndex));
                prereqCount[childIndex]++;
            }
        }


        boolean validInput = false;
        while (!validInput) {
            String input = JOptionPane.showInputDialog("Enter number of courses student can pick concurrently:");
            // Adding null check to allow cancelling
            if (input == null) return;

            try {
                concurrentStudy = Integer.parseInt(input);
                if (concurrentStudy > 0) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number (e.g., 4).");
            }
        }

        /*
           After trying to fetch courses based of Level segregation and using DFS style queuing arrived at going based
           of DependentLevels and Prereq count down algorithm. Capture more details in Coding Journal.
         */

        // Copying Prereq counts to countdown array
        int[] prereqCountLeft = prereqCount.clone();

        /*
         * Using recursive call method to fetch the levels of dependent on each vertex.
         */
        int[] dependantLevels = new int[numV];
        for (int v = 0; v < numV; v++) {
            calcDependantLevels(courseGraph, v, dependantLevels);
        }

        /*
         * Code from chatGpt to order the course with higher dependent levels first
         */
        final String[] code = courses;
        Comparator<Integer> byDependantLevel = (a, b) -> {
            if (dependantLevels[a] != dependantLevels[b]) {
                return dependantLevels[b] - dependantLevels[a];
            } else {
                return code[a].compareTo(code[b]);
            }
        };

        /*
         * Declaring readylist and moving all the course without prereqs
         */
        LinkedList<Integer> readyList = new LinkedList<>();
        for (int v = 0; v < numV; v++) {
            if (prereqCountLeft[v] == 0) {
                readyList.add(v);
            }
        }

        /*
         * Declaring study periods as list of courses array
         */
        LinkedList<String[]> studyPeriods = new LinkedList<>();

        /*
         * Allocating to study period and updating the prereq counts in loop
         */
        while (!readyList.isEmpty()) {

            // Sorting the readylist to prioritise course with higher dependent levels
            readyList.sort(byDependantLevel);

            /*
             * Moving eligible courses to assignable courses list
             */
            LinkedList<Integer> assignableCourses = new LinkedList<>();
            while (assignableCourses.size() < concurrentStudy && !readyList.isEmpty()) {
                assignableCourses.add(readyList.removeFirst());
            }

            /*
             * Updating the prereq counts of dependent course using above list
             */
            for (int v : assignableCourses) {
                Iterator<Edge> edges = courseGraph.edgeIterator(v);
                while (edges.hasNext()) {
                    int dependent = edges.next().getDest();
                    prereqCountLeft[dependent]--;
                    if (prereqCountLeft[dependent] == 0) {
                        readyList.add(dependent);
                    }
                }
            }

            /*
             * Fetching course codes and adding to study period array
             */
            String[] period = new String[assignableCourses.size()];
            int slot = 0;
            for (int v : assignableCourses) {
                period[slot++] = courses[v];
            }
            studyPeriods.add(period);
        }

        /*
         * Printing the result, both to the console and in a dialog box.
         */
        StringBuilder output = new StringBuilder();
        output.append(numV).append(" courses, up to ").append(concurrentStudy)
                .append(" per study period\n");
        output.append("Minimum study periods required: ").append(studyPeriods.size()).append("\n\n");

        int periodNumber = 1;
        for (String[] period : studyPeriods) {
            output.append("Study Period ").append(periodNumber).append(": ")
                    .append(String.join(", ", period)).append("\n");
            periodNumber++;
        }

        System.out.println();
        System.out.print(output);
        JOptionPane.showMessageDialog(null, output.toString());

    }

    /*
      Using recursive function call to count the levels of dependency for each vertex.
    */
    private static int calcDependantLevels(Graph graph, int v, int[] tailSize) {
        if (tailSize[v] != 0) {
            return tailSize[v];
        }
        int longest = 1;
        Iterator<Edge> edges = graph.edgeIterator(v);
        while (edges.hasNext()) {
            int next = edges.next().getDest();
            longest = Math.max(longest, 1 + calcDependantLevels(graph, next, tailSize));
        }
        tailSize[v] = longest;
        return longest;
    }

}