package pl.kurs.jg.tdd.userservice;

public class LoginDoesNotExistException extends RuntimeException {

    LoginDoesNotExistException(String login) {
        super("Login \"" + login + "\" doesn't exist!");
    }
}
