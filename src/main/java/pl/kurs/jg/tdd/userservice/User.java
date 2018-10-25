package pl.kurs.jg.tdd.userservice;

import java.util.Objects;

public class User {
    private String login, name, lastName;

    public User(String login, String name, String lastName) {
        this.login = login;
        this.name = name;
        this.lastName = lastName;

    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof User) {
            User user = (User) obj;
            return user.login.equals(this.login)
                    && user.name.equals(this.name)
                    && user.lastName.equals(this.lastName);

        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.login, this.name, this.lastName);
    }
}
