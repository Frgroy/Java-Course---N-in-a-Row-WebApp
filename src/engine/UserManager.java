package engine;

import javafx.util.Pair;

import java.util.*;

public class UserManager {

    private final Map<String, Pair<Boolean, Boolean>> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public void addUser(String username, boolean isComputer) {
        users.put(username, new Pair(isComputer, false));
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public Set<String> getUsers() {
        return users.keySet();
    }

    public boolean isUserExists(String username) {
        return users.containsKey(username);
    }

    public Boolean isUserIsComputer(String userName) {
        return users.get(userName).getKey();
    }

    public Boolean isUserIsPlaying(String userName) {
        return users.get(userName).getValue();
    }

    public void setUserAsPlaying(String userName) {
        Boolean isComputer = isUserIsComputer(userName);
        users.replace(userName, new Pair(isComputer ,true));
    }

    public void setUserAsNotPlaying(String userName) {
        Boolean isComputer = isUserIsComputer(userName);
        users.replace(userName, new Pair(isComputer ,false));
    }
}
