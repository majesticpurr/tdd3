package pl.kurs.jg.tdd.userservice;

public class UserService {
    private final UserStorage userStorage;
    private final LoginValidator loginValidator;

    public UserService(UserStorage userStorage, LoginValidator loginValidator) {
        this.userStorage = userStorage;
        this.loginValidator = loginValidator;
    }

    public User find(String login) {
        return userStorage.read(login);
    }

    public void add(String login, String name, String lastName) {
        if (loginValidator.isValid(login)) {
            userStorage.create(new User(login, name, lastName));
        }
    }

}
