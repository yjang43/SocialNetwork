package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileControl {
  private static String log = "";

  public static void constructGraph(GraphADT<Profile> graph, String... filePaths) {
    for (int i = 0; i < filePaths.length; i++) {
      try {
        Scanner scn = new Scanner(new File(filePaths[i]));
        // read each instruction and update the graph
        while (scn.hasNext()) {
          String instruction = scn.nextLine();
          System.out.println(instruction);
          log = log.concat(instruction + '\r');
          translateInstruction(graph, instruction);
        }
        scn.close();
      } catch (FileNotFoundException e) {
        System.out.println(e + "\nfile is not found");
      }
    }
  }

  private static void translateInstruction(GraphADT<Profile> graph, String instruction) {
    Scanner separator = new Scanner(instruction);
    String[] instructionSet = new String[3];
    int instructionIndex = 0;
    while (separator.hasNext()) {
      instructionSet[instructionIndex] = separator.next();
      instructionIndex++;
    }
    separator.close();


    final int operator = 0;
    final int operand1 = 1;
    final int operand2 = 2;

    // add user
    if (instructionSet[operator].compareTo("a") == 0 && instructionSet[operand2] == null) {
      graph.addUser(new Profile(instructionSet[operand1]));
      /////////////// Profile should be done!!!
      return;
    }

    // add friend
    if (instructionSet[operator].compareTo("a") == 0 && instructionSet[operand2] != null) {

      return;
    }

    // remove user
    if (instructionSet[operator].compareTo("r") == 0 && instructionSet[operand2] == null) {

      return;
    }

    // remove friend
    if (instructionSet[operator].compareTo("r") == 0 && instructionSet[operand2] != null) {

      return;
    }

    // set center user
    if (instructionSet[operator].compareTo("s") == 0 && instructionSet[operand2] == null) {

      return;
    }

    throw new InvalidInstructionException();

  }


  public static void writeLog(String filePath) {
    try {
      
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
      writer.write(log);
      writer.close();
    } catch(IOException e) {
      
    }
  }

  public static void main(String args[]) {
    String userDir = System.getProperty("user.dir");
    FileControl.constructGraph(new Graph(), userDir + "/application/testInput.txt");
    FileControl.writeLog(userDir + "/log.txt");
  }
}
