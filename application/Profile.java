package application;

import java.util.LinkedList;
import java.util.List;

public class Profile {
  List<Profile> friends;
  public Profile(String userName) {
    friends = new LinkedList<Profile>();
  }
  public List<Profile> getFriends(){
    friends.add(new Profile("mark"));
    friends.add(new Profile("sapan"));
    friends.add(new Profile("deb"));
    friends.add(new Profile("john"));
    friends.add(new Profile("1"));
    friends.add(new Profile("2"));
    friends.add(new Profile("3"));
    friends.add(new Profile("4"));
    friends.add(new Profile("5"));
    friends.add(new Profile("6"));
    friends.add(new Profile("7"));
    friends.add(new Profile("8"));
    return friends;
    
  }
  
}