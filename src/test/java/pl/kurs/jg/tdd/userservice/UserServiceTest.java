package pl.kurs.jg.tdd.userservice;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserServiceTest {
    final String login = "jdoe";
    final String name = "John";
    final String lastName = "Doe";

    final User user = new User(login, name, lastName);

    UserStorage userStorage = Mockito.mock(UserStorage.class);
    LoginValidator loginValidator = Mockito.mock(LoginValidator.class);

    UserService userService = new UserService(userStorage, loginValidator);

    @Test
    public void shouldFindUser() {

        //given

        BDDMockito.given(userStorage.read(ArgumentMatchers.eq(login))).willReturn(user);

        // when
        User result = userService.find(login);

        // then
        Assert.assertEquals(user, result);

    }

    @Test
    public void shouldAddUser() {

        // given

        BDDMockito.given(loginValidator.isValid(login)).willReturn(true);

        // when
        userService.add(login, name, lastName);

        // then
        Mockito.verify(userStorage).create(user);
    }

    @Test
    public void shouldNotAddUserWhenLoginValidationFails() {

        // given

        BDDMockito.given(loginValidator.isValid(ArgumentMatchers.any())).willReturn(false);

        // when
        userService.add(login, name, lastName);

        // then
        Mockito.verify(userStorage, Mockito.never()).create(user);
    }

    @Test
    public void shouldNotAddUserWhenLoginAlreadyExists() {

        // given

        BDDMockito.given(userStorage.read(login)).willReturn(user);
        BDDMockito.given(loginValidator.isValid(ArgumentMatchers.any())).willReturn(true);

        // when
        try {
            userService.add(login, name, lastName);
        } catch (LoginExistsException e) {

        }

        // then
        Mockito.verify(userStorage, Mockito.never()).create(user);
    }

    @Test(expected = LoginExistsException.class)
    public void shouldThrowExceptionWhileAddingWhenLoginAlreadyExists() {

        // given

        BDDMockito.given(userStorage.read(login)).willReturn(user);
        BDDMockito.given(loginValidator.isValid(ArgumentMatchers.any())).willReturn(true);

        // when
        userService.add(login, name, lastName);

        // then

    }

    @Test
    public void shouldUpdateUserName() {

        //given

        String newName = "Jane";

        BDDMockito.given(userStorage.read(login)).willReturn(user);

        // when
        userService.updateName(login, newName);

        // then
        Mockito.verify(userStorage, Mockito.atLeastOnce()).update(login, new User(login, newName, lastName));
    }

    @Test
    public void shouldUpdateUserLastName() {

        //given

        String newLastName = "Demolition";

        BDDMockito.given(userStorage.read(login)).willReturn(user);

        // when
        userService.updateLastName(login, newLastName);

        // then
        Mockito.verify(userStorage, Mockito.atLeastOnce()).update(login, new User(login, name, newLastName));
    }


}
