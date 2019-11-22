package application;

public interface GraphADT<T> {
  boolean addUser(T user);
  boolean addFriend(T user, T friend);
  boolean deleteUser(T user);
  boolean deleteFriend(T user, T friend);
}
