package application;

import java.util.LinkedList;
import java.util.List;

public class Profile {
  String userName;
  List<Profile> friends;
  public Profile(String userName) {
    this.userName = userName;
    friends = new LinkedList<Profile>();
  }
  public void testInit() {
    friends.add(new Profile("mark"));
    friends.add(new Profile("sapan"));
    friends.add(new Profile("deb"));
    friends.add(new Profile("john"));
  }
  public List<Profile> getFriends(){
    return friends;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public void setFriends(List<Profile> friends) {
    this.friends = friends;
  }
  
  
}