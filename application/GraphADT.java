package application;

public interface GraphADT<Profile> {
  boolean addUser(Profile user);
  boolean addFriend(Profile user, Profile friend);
  boolean deleteUser(Profile user);
  boolean deleteFriend(Profile user, Profile friend);
  boolean deleteUser(Profile profile);
  boolean addUser(Profile profile);
boolean addFriend(Profile profileA, Profile profileB);
boolean clearNetwork();
int order();
boolean removeFriend(Profile profileA, Profile profileB);
int size();
}
