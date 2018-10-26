package pl.kurs.jg.tdd.userservice;

abstract class UserStorage {

    abstract User read(String login);

    abstract void create(User user);

    abstract void update(String login, User newUserData);

    abstract void delete(String login);

}
