package pl.kurs.jg.tdd.userservice;

public class UserService {
    private final UserStorage userStorage;
    private final LoginValidator loginValidator;

    public UserService(UserStorage userStorage, LoginValidator loginValidator) {
        this.userStorage = userStorage;
        this.loginValidator = loginValidator;
    }

    public User find(String login) {
        User user = userStorage.read(login);

        if (user == null) {
            throw new LoginDoesNotExistException(login);
        }

        return user;
    }

    public void add(String login, String name, String lastName) {


        try {
            find(login);
        } catch (LoginDoesNotExistException e) {

            if (loginValidator.isValid(login)) {
                userStorage.create(new User(login, name, lastName));
            }
            return;
        }

        throw new LoginExistsException(login);

    }

    public void updateName(String login, String name) {
        User user = find(login); // find() will throw LoginDoesNotExistException if not found
        user.setName(name);
        userStorage.update(login, user);
    }

    public void updateLastName(String login, String lastName) {

        User user = find(login); // find() will throw LoginDoesNotExistException if not found
        user.setLastName(lastName);
        userStorage.update(login, user);
    }

    public void delete(String login) {

        if (find(login) == null) {
            throw new LoginDoesNotExistException(login);
        }
        userStorage.delete(login);
    }

}
