package application;

import java.util.LinkedList;
import java.util.List;

public class Profile {
	String user_Name; //user name
	String user_Picture;
	boolean is_User_Center = false;
	List<List<Profile>> list_of_user_friends; //list of users friends
  
	/**
	* Constructor to set users name and create a list of users friends
	* @param userName Users name.
	*/
	public Profile(String userName) {
		this.user_Name = userName;
		this.list_of_user_friends = new LinkedList<List<Profile>>();
	}
  
	/**
	 * @return list of users friends
	 */
	public List<List<Profile>> getListOfUsersFriends(){
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
	public String getUserPicture() {
	  return this.user_Picture;
	}
	
	/**
	 * Sets users picture
	 */
	public void setUserPicture(String userPicture) {
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
	 * Adds users friends and all of its friends
	 * @param friends
	 */
	public void setFriends(List<Profile> friends) {
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