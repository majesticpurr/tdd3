package pl.kurs.jg.tdd.userservice;

public class LoginDoesntExistException extends RuntimeException {

    LoginDoesntExistException(String login) {
        super("Login \"" + login + "\" doesn't exist!");
    }
}
