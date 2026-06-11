/**
 * File: Runner.java
 * Description: This class has the main function. It takes user input for file path and number of concurrent subjects user can pick.
 * Author: Shwetha Srinivas
 * Student ID: A3150322
 * Email ID: a3150322@adelaide.edu.au
 * AI Tool Used: N
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
         */

        for (String line : prereqLines) {
            String[] coursesInLine = line.split(",\\s*");
            Integer childIndex = courseIndex.get(coursesInLine[0]);
            for (int i = 1; i < coursesInLine.length; i++) {
                Integer prereqIndex = courseIndex.get(coursesInLine[i].trim());
                courseGraph.insert(new Edge(prereqIndex, childIndex));
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

        System.out.println("Items in courseList: " + Arrays.toString(courses));
        System.out.println("Concurrent study value: " + concurrentStudy);
        System.out.println(courseIndex);
        System.out.println(courseGraph);

    }

}