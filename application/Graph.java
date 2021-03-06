package application;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author zanzhang
 *
 */
public class Graph implements GraphADT<Profile> {

	private int numOfUser = 0;
	private int numOfEdges = 0;

	private Map<Profile, ArrayList<Profile>> myMap;
	private ArrayList<Profile> vertexList;

	/*
	 * Default no-argument constructor
	 */
	public Graph() {
		vertexList = new ArrayList<>();
		myMap = new HashMap<Profile, ArrayList<Profile>>();
	}

	/**
	 * Add new user to the graph.
	 *
	 * If user is null return false
	 * 
	 * 
	 * If user is already exists return true and without adding a vertex or throwing
	 * an exception.
	 * 
	 * Valid argument conditions: 1. user is non-null 2. user is not already in the
	 * graph
	 * 
	 * @param profile the user to be added
	 * @return true if addUser, otherwise false
	 */
	@Override
	public boolean addUser(Profile profile) {
		if (profile == null)
			return false;

		else if (myMap.containsKey(profile))
			return true;

		// if user is not null and not already in the graph
		else {
			myMap.put(profile, new ArrayList<Profile>());
			vertexList.add(profile);
			numOfUser++;
			return true;
		}
	}

	/**
	 * Remove a user and all associated edges from the graph.
	 * 
	 * If user is null or does not exist, method ends without removing a user,
	 * edges, or throwing an exception. and return false.
	 * 
	 * Valid argument conditions: 1. user is non-null 2. user is not already in the
	 * graph
	 * 
	 * @param profile the user to be removed
	 * @return true if delete friend, otherwise false
	 */
	@Override
	public boolean deleteUser(Profile profile) {
		if (profile == null || !myMap.containsKey(profile))
			return false;

		else {
			myMap.remove(profile);
			vertexList.remove(profile);
			//iterates through vertex list and removes user from other profile's friend list
			for (int i = 0; i < vertexList.size(); i++) {
				if (vertexList.get(i).list_of_user_friends.contains(profile))
					vertexList.get(i).list_of_user_friends.remove(profile);
			}
			numOfUser--;
			return true;
		}

	}

	/**
	 * Add the user from profileA to profileB to this graph. (edge is directed and
	 * weighted)
	 * 
	 * If either user does not exist, VERTEX IS ADDED and then edge is created. No
	 * exception is thrown.
	 *
	 * If the edge exists in the graph, no edge is added and no exception is thrown.
	 * 
	 * Valid argument conditions: 1. neither user is null 2. both users are in the
	 * graph 3. the edge is not in the graph
	 * 
	 * @param profileA the first user (src)
	 * @param profileB the second user (dst)
	 * @return true if successful adding a friend, otherwise false
	 */
	@Override
	public boolean addFriend(Profile profileA, Profile profileB) {
		boolean success = false;
		if (profileA != null && profileB != null) {
			// condition 1:
			// if both vertex doesn't exist
			// add vertex and add edge
			if (!myMap.containsKey(profileA) && !myMap.containsKey(profileB)) {
				// add vertex
				addUser(profileA);
				addUser(profileB);
				// adds each user to the other's friend list
				profileA.list_of_user_friends.add(profileB);
				profileB.list_of_user_friends.add(profileA);
				// add edge
				numOfEdges++;
				success = true;
			}
			// condition2:
			// if vertex1 exist, but vertex2 not
			else if (myMap.containsKey(profileA) && !myMap.containsKey(profileB)) {
				addUser(profileB);
				// adds each user to the other's friend list
				profileA.list_of_user_friends.add(profileB);
				profileB.list_of_user_friends.add(profileA);
				// add edge
				numOfEdges++;
				success = true;
			}
			// condition3:
			// if vertex2 exist, but vertex1 not
			else if (!myMap.containsKey(profileA) && myMap.containsKey(profileB)) {
				addUser(profileA);
				// adds each user to the other's friend list
				profileA.list_of_user_friends.add(profileB);
				profileB.list_of_user_friends.add(profileA);
				// add edge
				numOfEdges++;
				success = true;
			}
			// condition4:
			// if contain both key in the graph
			// no edge in the graph
			else {
				// check if already has the edge
				if (profileA.list_of_user_friends.contains(profileB)) {
					success = false;
				}
				// if no edge exist
				else {
					// adds each user to the other's friend list
					profileA.list_of_user_friends.add(profileB);
					profileB.list_of_user_friends.add(profileA);
					numOfEdges++;
					success = true;
				}
			}
		}
		return success;
	}

	/**
	 * Remove the edge from profileA to profileB from this graph. (edge is directed
	 * and weighted) If either user does not exist, or if an edge from profileA to
	 * profileB does not exist, return false no edge is removed and no exception is
	 * thrown.
	 * 
	 * 
	 * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
	 * the graph 3. the edge from profileA to profileB is in the graph
	 * 
	 * @param profileA the first user
	 * @param profileB the second user
	 * @return return false if vertex doesn't exist
	 */
	@Override
	public boolean deleteFriend(Profile profileA, Profile profileB) {
		boolean success = false;
		// condition1: the vertex doesn't exist
		if (!myMap.containsKey(profileA) || !myMap.containsKey(profileB)) {
			success = false;
			// the vertex are exist
		} else {
			// condition2: the edge is not exist
			if (!profileA.list_of_user_friends.contains(profileB)) {
				success = false;
			}
			// codition3: the edge is exist, remove it.
			else if (profileA.list_of_user_friends.contains(profileB)) {
				profileA.list_of_user_friends.remove(profileB);
				profileB.list_of_user_friends.remove(profileA);
				numOfEdges--;
				success = true;
			}
		}
		return success;
	}

	/**
	 * Deleting all the users and relations
	 * 
	 * if no user and relationship in the graph return true
	 *
	 * @param boolean if delete successfully return true otherwise return false
	 */

	@Override
	public boolean clearNetwork() {
		myMap = new HashMap<Profile, ArrayList<Profile>>();
		vertexList = new ArrayList<>();
		return true;

	}

	/**
	 * Returns the list of vertexes
	 * 
	 * @return
	 */
	public ArrayList<Profile> getVertexList() {
		return vertexList;
	}

	/**
	 * Returns the number of edges in this graph.
	 * 
	 * @return number of edges in the graph.
	 */
	@Override
	public int size() {
		return numOfEdges;
	}

	/**
	 * Returns the number of vertices in this graph.
	 * 
	 * @return number of vertices in graph.
	 */
	@Override
	public int order() {
		return numOfUser;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
