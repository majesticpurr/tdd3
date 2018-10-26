package pl.kurs.jg.tdd.userservice;

import java.util.HashMap;
import java.util.Map;

class UserStorageMap extends UserStorage {

    private final Map<String, User> userMap;

    UserStorageMap(Map<String, User> userMap) {
        if (userMap == null) {
            this.userMap = new HashMap<>();
        } else {
            this.userMap = userMap;
        }
    }

    UserStorageMap() {
        this(null);
    }


    User read(String login) {
        return userMap.get(login);
    }

    void create(User user) {
        userMap.put(user.getLogin(), user);
    }

    void update(String login, User newUserData) {
        userMap.put(login, newUserData);
    }

    void delete(String login) {
        userMap.remove(login);
    }
}
