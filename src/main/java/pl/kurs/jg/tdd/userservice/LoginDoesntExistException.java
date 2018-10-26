package pl.kurs.jg.tdd.userservice;

public class LoginDoesntExistException extends Throwable {

    LoginDoesntExistException(String login) {
        super("Login \"" + login + "\" doesn't exist!");
    }
}
