package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



/**
 * Constructs graph of users, gets mutual friends between users, finds shortest
 * path between users
 *
 */
public class ProfileManager {
	private Queue<Profile> queue;
	List<String> list;
	private Graph graph; // creates graph instance

	/**
	 * No-arg constructor, initializes instance of graph
	 */
	public ProfileManager() {
		graph = new Graph();
		queue = new LinkedList<Profile>();
	}

	/**
	 * Returns a list of mutual friends between two users
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public List<Profile> getMutualFriends(Profile profileA, Profile profileB) {
		List<Profile> listA = profileA.getListOfUsersFriends(); // holds profileA's friends
		List<Profile> listB = profileB.getListOfUsersFriends(); // holds profileB's friends
		List<Profile> common = new ArrayList<Profile>(); // creates temp 
		//copies profileA's friends into common list
		for(int i = 0; i < profileA.getListOfUsersFriends().size(); i++)
			common.add(listA.get(i));
		common.retainAll(listB); // compares A with B and stores common friends between two
		return common;
	}

	/**
	 * Gets shortest path between two users.
	 * 
	 * @param profileA
	 * @param profileB
	 * @return
	 */
	public List<String> getShortestPath(Profile profileA, Profile profileB) {
		list = dalgo(profileA, profileB);
		return list;
	}

	/**
	 * Dijkstra's Algorithm
	 * @param profileA
	 * @param profileB
	 * @return
	 */
	public List<String> dalgo(Profile profileA, Profile profileB) {
		list = new ArrayList<String>(); //initializes list to hold shortest path
		Profile temp = profileA; // stores profile1 to start
		profileA.totalWeight = 0; //sets start point's total weight to 0
		queue = new LinkedList<Profile>(); //initializes queue
		queue.add(profileA); //adds start profile to queue
		
		while (!queue.isEmpty()) {
			temp = queue.remove(); //remove first item from queue
			temp.visited = true; // item has now been visited
			//if the end profile has been reached break from loop
			if (temp.getUserName().equals(profileB.getUserName()))
				break;
			dhelper(temp); //call helper
		}

		temp = profileB;
		//copies predecessors of temp into list
		while (temp.predecessor != null) {
			list.add(temp.getUserName());
			temp = temp.predecessor;
		}
		list.add(profileA.getUserName()); //adds first starting profile to list
		Collections.reverse(list); //reverses list so it appears starting at A, ending at B
		return list;
	}

	/**
	 * Helper method for dijkstra's algo
	 * 
	 * @param profile1
	 */
	private void dhelper(Profile profile1) {
		Profile temp = null;
		int edgeDistance = -1;
		int newDistance = -1;
		//iterates through list of friends for given profile
		for (int i = 0; i < profile1.getListOfUsersFriends().size(); i++) {
			temp = profile1.getListOfUsersFriends().get(i);
			//if profile has not been visited yet, check if total weight can be reduced
			if (!temp.visited) {
				edgeDistance = temp.weight;
				newDistance = profile1.totalWeight + edgeDistance;
				//if weight can be reduced do it and add profile to queue
				if (newDistance < temp.totalWeight) {
					temp.totalWeight = newDistance;
					temp.predecessor = profile1;
					queue.add(temp);
				}
			}
		}

	}

	/**
	 * Helper method to do breadth first search-- doesn't always return shortest path, 
	 * not currently being used- keep in case?
	 * 
	 * @param profileA
	 * @param profileB
	 * @return
	 */
	private List<String> BFS(Profile profileA, Profile profileB) {
		List<String> list = new ArrayList<String>();
		queue.add(profileA);
		profileA.visited = true; // sets profile to visited

		while (!queue.isEmpty()) {
			Profile element = queue.poll(); // removes profile from queue
			list.add(element.getUserName()); // adds profile to list of users to be returned
			// checks if next profile is stopping point, if yes break from loop
			if (element.getUserName().equals(profileB.getUserName()))
				break;
			List<Profile> friends = element.getListOfUsersFriends();
			for (int i = 0; i < friends.size(); i++) {
				Profile temp = friends.get(i); // holds next friend
				if (temp != null && !temp.visited) {
					queue.add(temp); // adds friend to queue
					temp.visited = true; // sets friend to visited
				}
			}
		}
		return list;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
