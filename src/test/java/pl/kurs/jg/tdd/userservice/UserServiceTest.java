package pl.kurs.jg.tdd.userservice;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserServiceTest {

    private final String login = "jdoe";
    private final String name = "John";
    private final String lastName = "Doe";

    private final User user = new User(login, name, lastName);

    private UserStorage userStorage = Mockito.mock(UserStorage.class);
    private LoginValidator loginValidator = Mockito.mock(LoginValidator.class);

    private UserService userService = new UserService(userStorage, loginValidator);

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

    @Test
    public void shouldDeleteUser() {

        //given
        BDDMockito.given(userStorage.read(login)).willReturn(user);
        // when
        userService.delete(login);

        // then
        Mockito.verify(userStorage, Mockito.times(1)).delete(login);
    }

    @Test
    public void shouldNotDeleteNotExistingUser() {

        //given

        BDDMockito.given(userStorage.read(login)).willReturn(null);

        // when
        try {
            userService.delete(login);
        } catch (LoginDoesNotExistException e) {

        }

        // then
        Mockito.verify(userStorage, Mockito.never()).delete(login);
    }

    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenDeletingNotExistingUser() {

        //given
        BDDMockito.given(userStorage.read(login)).willReturn(null);

        // when
        userService.delete(login);

        // then

    }

    @Test
    public void shouldNotUpdateNameOfNotExistingUser() {

        //given

        String newName = "Jane";

        BDDMockito.given(userStorage.read(login)).willReturn(null);

        // when
        try {
            userService.updateName(login, newName);
        } catch (LoginDoesNotExistException e) {

        }
        // then
        Mockito.verify(userStorage, Mockito.never()).update(login, new User(login, newName, lastName));
    }

    @Test
    public void shouldNotUpdateLastNameOfNotExistingUser() {

        //given

        String newLastName = "Demolition";

        BDDMockito.given(userStorage.read(login)).willReturn(null);

        // when
        try {
            userService.updateLastName(login, newLastName);
        } catch (LoginDoesNotExistException e) {

        }
        // then
        Mockito.verify(userStorage, Mockito.never()).update(login, new User(login, name, newLastName));
    }


    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenUpdatingNameOfNotExistingUser() {
        //given

        String newName = "Jane";

        BDDMockito.given(userStorage.read(login)).willReturn(null);

        // when
        userService.updateName(login, newName);

        // then
    }

    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenUpdatingLastNameOfNotExistingUser() {

        //given

        String newLastName = "Demolition";

        BDDMockito.given(userStorage.read(login)).willReturn(null);

        // when
        userService.updateLastName(login, newLastName);

        // then
    }

    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenUserIsNotFound() {

        //given
        BDDMockito.given(userStorage.read(login)).willReturn(null);

        // when
        userService.find(login);

        // then

    }

}
