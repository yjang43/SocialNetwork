package application;

public interface GraphADT<Profile> {
  boolean addUser(Profile user);

  boolean addFriend(Profile user, Profile friend);

  boolean deleteUser(Profile user);

  boolean deleteFriend(Profile user, Profile friend);

  boolean clearNetwork();

  int order();

  boolean removeFriend(Profile profileA, Profile profileB);

  int size();
}
