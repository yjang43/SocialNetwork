package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.image.Image;

public class Profile {
	String user_Name; //user name
	Image user_Picture;
	boolean is_User_Center = false;
	boolean visited; //BFS algo in ProfileManager
	List<Profile> list_of_user_friends; //list of users friends
	//weight, totalWeight, and predecessor for Dijkstra's Algorithm in ProfileManager
	int weight; //each profile friendship automatically has a weight of 1
	int totalWeight; // total weight
	Profile predecessor; // profile's predecessor
  
	/**
	* Constructor to set users name and create a list of users friends
	* @param userName Users name.
	*/
	public Profile(String userName) {
		this.user_Name = userName;
		this.list_of_user_friends = new LinkedList<Profile>();
		this.weight = 1; // each individual profile has a weight of 1
		this.totalWeight = 1000; // each has a total weight of 1000 to start
		this.predecessor = null;
		try {
		  
		  this.user_Picture = new Image(new FileInputStream("application/bucky.png"));
		} catch(FileNotFoundException e) {
		  System.out.println("error");
		}
	}
  
	/**
	 * @return list of users friends
	 */
	public List<Profile> getListOfUsersFriends(){
		return this.list_of_user_friends;
	}	
	
	/**
	 * @return user name
	 */
	public String getUserName() {
	  return this.user_Name;
	}
	
	/**
	 * @return user name
	 */
	public Image getUserPicture() {
	  return this.user_Picture;
	}
	
	/**
	 * Sets users picture
	 */
	public void setUserPicture(Image userPicture) {
	  this.user_Picture = userPicture;
	}
	
	/**
	 * Sets user name
	 * @param userName
	 */
	public void setUserName(String userName) {
	    this.user_Name = userName;
	}
	  
	/**
	 * Adds users friends.
	 * @param friends
	 */
	public void setFriends(Profile friends) {
	   this.list_of_user_friends.add(friends);
	}
	
	/**
	 * Sets users center position to true or false to determine if user is in center position
	 * @param isUserCenter
	 */
	public void setUserCenter(boolean isUserCenter) {
	    this.is_User_Center = isUserCenter;
	}
	
	/**
	 * Returns true or false to determine if a user is in the center position
	 * @return is_User_Center
	 */
	public boolean getUserCenter() {
	    return this.is_User_Center;
	}
}