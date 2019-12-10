//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           This program reads a text file and creates a user relationship among freinds
// Files:           Main.java, FileControl.java, Graph.java, PackageManager.java
// Course:          (400, Fall, 2019)
//
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileControl {
	 private static String log = "";
	 private static ProfileManager profile_manager = new ProfileManager();

	 /**
	 * Constructs a graph object based on input text file
	 * @param filePaths text file to read relationships
	 */
	public static void constructGraph(String... filePaths) {
	    for (int i = 0; i < filePaths.length; i++) {
	      try {
	        Scanner scn = new Scanner(new File(filePaths[i]));
	        // read each instruction and update the graph
	        while (scn.hasNext()) {
	          String instruction = scn.nextLine();
	          System.out.println(instruction);
	          log = log.concat(instruction + '\r');
	          translateInstruction(FileControl.profile_manager.getGraph(), instruction);
	        }
	        scn.close();
	      } catch (FileNotFoundException e) {
	        System.out.println(e + "\nfile is not found");
	      }
	    }
	  }

	/**
	 * Adds or deletes user relationships based on instructions
	 * @param graph
	 * @param instruction
	*/
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
	
	    //add user
	    if (instructionSet[operator].compareTo("a") == 0 && instructionSet[operand2] == null) {
	    	//
	    	try {
	    		Profile first_user_profile = profile_manager.findProfile(instructionSet[operand1]);
	    	}catch(Exception E) {
	    		Profile first_user_profile = new Profile(instructionSet[operand1]);
	    		//FileControl.graph.addUser(first_user_profile);
	    		graph.addUser(first_user_profile);
	    	}
	    	
	        return;
	    }
	
	    //add relationship
	    if (instructionSet[operator].compareTo("a") == 0 && instructionSet[operand2] != null) {
	    	Profile first_user_profile, second_user_profile;
	    	//if first user is already in network get user from network to add relation ship, else create new user
	    	//
	    	try {
	    		first_user_profile = profile_manager.findProfile(instructionSet[operand1]);
	    	}catch(Exception E) {
	    		first_user_profile = new Profile(instructionSet[operand1]);
	    	}
	    	
	    	try {
	    		second_user_profile = profile_manager.findProfile(instructionSet[operand2]);
	    	}catch(Exception E) {
	    		second_user_profile = new Profile(instructionSet[operand2]);
	    	}
	    	
	        //add relationship
	    	graph.addFriend(first_user_profile, second_user_profile);
	    	
	      return;
	    }
	
	    //remove user
	    if (instructionSet[operator].compareTo("r") == 0 && instructionSet[operand2] == null) {
	    	try {
	    		Profile remove_profile = profile_manager.findProfile(instructionSet[operand1]);
	    		graph.deleteUser(remove_profile);
	    	}catch(Exception E) {
	    	}
	    	
	      return;
	    }
	
	    //remove relationship
	    if (instructionSet[operator].compareTo("r") == 0 && instructionSet[operand2] != null) {
	    	////if relationship is already in network get users and remove relationship
	    	try {
	    		Profile first_user_profile = profile_manager.findProfile(instructionSet[operand1]);
	    		Profile second_user_profile = profile_manager.findProfile(instructionSet[operand2]);
	    		graph.deleteFriend(first_user_profile, second_user_profile);
	    	}catch(Exception E) {
	    	}
	
	      return;
	    }
	
	    // set center user
	    if (instructionSet[operator].compareTo("s") == 0 && instructionSet[operand2] == null) {
	    	
	    	//if user is already in network get user from network and set user to center
	    	try {
	    		Profile set_user_center = profile_manager.findProfile(instructionSet[operand1]);
	    		set_user_center.setUserCenter(true);
	    	}catch(Exception E) {
	    	}
	
	      return;
	    }
	
	    throw new InvalidInstructionException();
	
	  }


  	/**
  	 * Write graph relationship output to a text file
  	 * @param filePath
  	*/
	public static void writeLog(String filePath) {
	    try {
	      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
	      writer.write(log);
	      writer.close();
	    } catch(IOException e) {
	      
	    }
	  }

//  public static void main(String args[]) {
//    String userDir = System.getProperty("user.dir");
//    FileControl.constructGraph(userDir + "/friends_003.txt");
//    FileControl.writeLog(userDir + "/log.txt");
//  }
}
