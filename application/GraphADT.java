package application;

public interface GraphADT<T> {
  boolean addUser(T profile);

  boolean addFriend(T profileA, T profileB);

  boolean deleteUser(T profile);

  boolean deleteFriend(T profileA, T profileB);

  boolean clearNetwork();

  int order();

  int size();
}
