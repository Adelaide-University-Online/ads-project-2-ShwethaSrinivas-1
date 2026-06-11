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

        int concurrentStudy = 0;
        String verticesLine;
        String[] courses;
        LinkedList<String> courseList = new LinkedList<>();

        // Getting path from user and reading the file data
        boolean fileRead = false;

        while(!fileRead) {

            String filePath = JOptionPane.showInputDialog("Enter the file path with file name:");
            // Adding null check to allow cancelling
            if (filePath == null) return;

            filePath = filePath.replace("\"", "");

            try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                // Read first line and create array
                verticesLine = br.readLine();
                courses = verticesLine.split(",\\s*");

                //Insert into LinkedList
                Collections.addAll(courseList, courses);

                fileRead = true;
            } catch (Exception e) {
                System.out.println("Invalid file path. Provide a valid path" + "\n" + e.getMessage());
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

        System.out.println("Items in courseList: " + courseList);
        System.out.println("Concurrent study value: " + concurrentStudy);
        
    }
    
}