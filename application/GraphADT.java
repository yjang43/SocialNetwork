package application;

public interface GraphADT<T> {
  boolean addUser(T user);
  boolean addFriend(T user, T friend);
  boolean deleteUser(T user);
  boolean deleteFriend(T user, T friend);
  boolean deleteUser(String profile);
  boolean addUser(String profile);
boolean addFriend(String profileA, String profileB);
boolean clearNetwork();
int order();
boolean removeFriend(String profileA, String profileB);
int size();
}
