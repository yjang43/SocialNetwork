package application;

import java.util.ArrayList;
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
		List<Profile> common = listA; // creates temp to hold A
		common.retainAll(listB); // compares A with B and stores common friends
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
		List<String> list = new ArrayList<String>(); //list to hold string of user names in path
		list = BFS(profileA, profileB); 
		return list;
	}

	/**
	 * Helper method to do breadth first search
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
			//checks if next profile is stopping point, if yes break from loop
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
