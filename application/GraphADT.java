package application;

import java.util.ArrayList;

public interface GraphADT<T> {
	
  /**
     * Add new user to the graph.
     *
     * If user is null return false
     * 
     *  
     * If user is already exists return true
     * and without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. user is non-null
     * 2. user is not already in the graph 
     * 
     * @param profile the user to be added
     * @return true if addUser, otherwise false
   */
  boolean addUser(T profile);

  /**
	 * Add the user from profileA to profileB
	 * to this graph.  (edge is directed and weighted)
	 * 
	 * If either user does not exist,
	 * VERTEX IS ADDED and then edge is created.
	 * No exception is thrown.
	 *
	 * If the edge exists in the graph,
	 * no edge is added and no exception is thrown.
	 * 
	 * Valid argument conditions:
	 * 1. neither user is null
	 * 2. both users are in the graph 
	 * 3. the edge is not in the graph
	 *  
	 * @param profileA the first user (src)
	 * @param profileB the second user (dst)
	 * @return true if successful adding a friend, otherwise false 
	 */
  boolean addFriend(T profileA, T profileB);

  /**
	 * Remove a user and all associated 
	 * edges from the graph.
	 * 
	 * If user is null or does not exist,
	 * method ends without removing a user, edges, 
	 * or throwing an exception. and return false.
	 * 
	 * Valid argument conditions:
	 * 1. user is non-null
	 * 2. user is not already in the graph 
	 *  
	 * @param profile the user to be removed
	 * @return true if delete friend, otherwise false
  */
  boolean deleteUser(T profile);

  /**
	 * Remove the edge from profileA to profileB
	 * from this graph.  (edge is directed and weighted)
	 * If either user does not exist,
	 * or if an edge from profileA to profileB does not exist,
	 * return false no edge is removed and no exception is thrown.
	 * 
	 * 
	 * Valid argument conditions:
	 * 1. neither vertex is null
	 * 2. both vertices are in the graph 
	 * 3. the edge from profileA to profileB is in the graph
	 *  
	 * @param profileA the first user
	 * @param profileB the second user
	 * @return return false if vertex doesn't exist
  */
  boolean deleteFriend(T profileA, T profileB);

  /**
	 * Deleting all the users and relations
	 *  
	 * if no user and relationship in the graph return true
	 *
	 * @param boolean if delete successfully return true
	 *  otherwise return false
  */
  boolean clearNetwork();

  /**
	 * Returns the number of vertices in this graph.
	 * @return number of vertices in graph.
  */
  int order();

  /**
	 * Returns the number of edges in this graph.
	 * @return number of edges in the graph.
  */
  int size();
  
 /**
 * @return a list of all users in the graph
 */
ArrayList<Profile> getVertexList();
  
}
