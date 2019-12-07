package application;

import java.util.List;

/**
 * Constructs graph of users, gets mutual friends between users, finds shortest
 * path between users
 *
 */
public class ProfileManager {

	private Graph graph; // creates graph instance

	/**
	 * No-arg constructor, initializes instance of graph
	 */
	public ProfileManager() {
		graph = new Graph();
	}

	/**
	 * Returns a list of mutual friends between two users
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public List<Profile> getMutualFriends(Profile profileA, Profile profileB) {
//		List<Profile> listA = profileA.getListOfUsersFriends(); // holds profileA's friends
//		List<Profile> listB = profileB.getListOfUsersFriends(); // holds profileB's friends
//		List<Profile> common = listA; // creates temp to hold A
//		common.retainAll(listB); // compares A with B and stores common friends
//		return common;
	  return null;
	}

	/**
	 * Gets shortest path between two users
	 * 
	 * @param profileA
	 * @param profileB
	 * @return
	 */
	public List getShortestPath(Profile profileA, Profile profileB) {

		return null;
	}

  public Graph getGraph() {
    return graph;
  }

  public void setGraph(Graph graph) {
    this.graph = graph;
  }

}
