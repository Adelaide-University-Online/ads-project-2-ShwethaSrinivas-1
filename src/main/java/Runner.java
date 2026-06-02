/**
* File: filename.java
* Description: A brief description of this Java module.
* Author: Steve Jobs
* Student ID: 12345678
* Email ID: jobst007
* AI Tool Used: Y/N (This includes all AI Tools e.g. ChatGPT, Microsoft or Github Copiliot etc... Please leave blank if you do not wish to share this information)
* This is my own work as defined by
*    the University's Academic Integrity Policy.
**/

import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;

public class Runner {
    
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to ADS Assignment Starter!");
        //Taking the file location and Reading the file
        String filePath = JOptionPane.showInputDialog("Enter the file path with file name:");
        System.out.println(filePath);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String verticesLine = br.readLine();


        System.out.println(verticesLine);
        System.out.println("You can modify this file to implement your assignment requirements.");
        
    }
    
}