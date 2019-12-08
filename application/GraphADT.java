package application;

public interface GraphADT<Profile> {
  boolean addUser(Profile profile);

  boolean addFriend(Profile profileA, Profile profileB);

  boolean deleteUser(Profile profile);

  boolean deleteFriend(Profile profileA, Profile profileB);

  boolean clearNetwork();

  int order();

  int size();
}
