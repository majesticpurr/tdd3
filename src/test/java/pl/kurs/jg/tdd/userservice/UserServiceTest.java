package pl.kurs.jg.tdd.userservice;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserServiceTest {

    @Test
    public void shouldFindUser() {

        //given

        String login = "jdoe";
        String name = "John";
        String lastName = "Doe";

        User user = new User(login, name, lastName);

        UserStorage userStorage = Mockito.mock(UserStorage.class);
        BDDMockito.given(userStorage.read(ArgumentMatchers.eq(login))).willReturn(user);

        LoginValidator loginValidator = Mockito.mock(LoginValidator.class);

        UserService userService = new UserService(userStorage, loginValidator);

        // when
        User result = userService.find(login);

        // then
        Assert.assertEquals(user, result);

    }

    @Test
    public void shouldAddUser() {

        // given

        String login = "jdoe";
        String name = "John";
        String lastName = "Doe";

        User user = new User(login, name, lastName);

        UserStorage userStorage = Mockito.mock(UserStorage.class);
        LoginValidator loginValidator = Mockito.mock(LoginValidator.class);
        BDDMockito.given(loginValidator.isValid(login)).willReturn(true);

        UserService userService = new UserService(userStorage, loginValidator);
        // when
        userService.add(login, name, lastName);

        // then
        Mockito.verify(userStorage).create(user);
    }

    @Test
    public void shouldNotAddUserWhenLoginValidationFails() {

        // given

        String login = "jdoe";
        String name = "John";
        String lastName = "Doe";

        User user = new User(login, name, lastName);

        UserStorage userStorage = Mockito.mock(UserStorage.class);

        LoginValidator loginValidator = Mockito.mock(LoginValidator.class);
        BDDMockito.given(loginValidator.isValid(ArgumentMatchers.any())).willReturn(false);

        UserService userService = new UserService(userStorage, loginValidator);

        // when
        userService.add(login, name, lastName);

        // then
        Mockito.verify(userStorage, Mockito.never()).create(user);
    }

}
