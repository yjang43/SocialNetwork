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
	 private static Graph graph = new Graph();
	 private static ArrayList<String> list_of_friends_and_users = new ArrayList<String>();
	 private static List<Profile> list_of_profiles = new ArrayList<Profile>();
	 private static Profile first_user_profile;
	 private static Profile second_user_profile;

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
	          translateInstruction(graph, instruction);
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
	    	if (!list_of_friends_and_users.contains(instructionSet[operand1])) {
	    		//
	        	FileControl.graph.addUser(new Profile(instructionSet[operand1]));
	        	list_of_friends_and_users.add(instructionSet[operand1]);
	    	}
	    	
	        return;
	    }
	
	    //add relationship
	    if (instructionSet[operator].compareTo("a") == 0 && instructionSet[operand2] != null) {
	    	//if first user is already in network get user from network to add relation ship, else create new user
	    	if (list_of_friends_and_users.contains(instructionSet[operand1])) {
	    		list_of_profiles = FileControl.graph.getVertexList();
	    		Iterator<Profile> iterate = list_of_profiles.iterator();
	            while (iterate.hasNext()) {
	                first_user_profile = iterate.next();
	                if (first_user_profile.getUserName().equals(instructionSet[operand1])) {
	                    break;
	                }
	            }
	    	}else {
	    		first_user_profile = new Profile(instructionSet[operand1]);
	    		list_of_friends_and_users.add(instructionSet[operand1]);
	    	}
	    	//if second user is already in network get user from network to add relation ship, else create new user
	    	if (list_of_friends_and_users.contains(instructionSet[operand2])) {
	    		list_of_profiles = FileControl.graph.getVertexList();
	    		Iterator<Profile> iterate = list_of_profiles.iterator();
	            while (iterate.hasNext()) {
	                second_user_profile = iterate.next();
	                if (second_user_profile.getUserName().equals(instructionSet[operand2])) {
	                    break;
	                }
	            }
	    	}else {
	    		second_user_profile = new Profile(instructionSet[operand2]);
	    		list_of_friends_and_users.add(instructionSet[operand2]);
	    	}
	        //add relationship
	    	FileControl.graph.addFriend(first_user_profile, second_user_profile);
	    	
	      return;
	    }
	
	    //remove user
	    if (instructionSet[operator].compareTo("r") == 0 && instructionSet[operand2] == null) {
	    	Profile user_profile = null;
	    	//if user is already in network get user from network and remove user
	    	if (list_of_friends_and_users.contains(instructionSet[operand1])) {
	    		list_of_profiles = FileControl.graph.getVertexList();
	    		Iterator<Profile> iterate = list_of_profiles.iterator();
	            while (iterate.hasNext()) {
	                user_profile = iterate.next();
	                if (user_profile.getUserName().equals(instructionSet[operand1])) {
	                    break;
	                }
	            }
	    		//
	        	FileControl.graph.deleteUser(user_profile);
	        	list_of_friends_and_users.remove(instructionSet[operand1]);
	    	}
	    	
	      return;
	    }
	
	    //remove relationship
	    if (instructionSet[operator].compareTo("r") == 0 && instructionSet[operand2] != null) {
	    	
	    	Profile first_user_profile = null;
	    	Profile second_user_profile = null;
	    	////if relationship is already in network get users and remove relationship
	    	if (list_of_friends_and_users.contains(instructionSet[operand1]) && list_of_friends_and_users.contains(instructionSet[operand2])) {
	    		list_of_profiles = FileControl.graph.getVertexList();
	    		Iterator<Profile> first_iterate = list_of_profiles.iterator();
	            while (first_iterate.hasNext()) {
	            	first_user_profile = first_iterate.next();
	                if (first_user_profile.getUserName().equals(instructionSet[operand1])) {
	                    break;
	                }
	            }
	            
	            Iterator<Profile> second_iterate = list_of_profiles.iterator();
	            while (second_iterate.hasNext()) {
	            	second_user_profile = second_iterate.next();
	                if (second_user_profile.getUserName().equals(instructionSet[operand2])) {
	                    break;
	                }
	            }
	    		//Remove relationship
	        	FileControl.graph.deleteFriend(first_user_profile, second_user_profile);
	    	}
	
	      return;
	    }
	
	    // set center user
	    if (instructionSet[operator].compareTo("s") == 0 && instructionSet[operand2] == null) {
	    	
	    	Profile set_user_center = null;
	    	//if user is already in network get user from network and set user to center
	    	if (list_of_friends_and_users.contains(instructionSet[operand1])) {
	    		list_of_profiles = FileControl.graph.getVertexList();
	    		Iterator<Profile> iterate = list_of_profiles.iterator();
	            while (iterate.hasNext()) {
	            	set_user_center = iterate.next();
	                if (set_user_center.getUserName().equals(instructionSet[operand1])) {
	                    break;
	                }
	            }
	    		//
	            set_user_center.is_User_Center = true;
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
