package pl.kurs.jg.tdd.userservice;

public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User find(String login) {
        return null;
    }

}
