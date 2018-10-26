package pl.kurs.jg.tdd.userservice;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserServiceWithRealStorageTest {
    private final String login = "jdoe";
    private final String name = "John";
    private final String lastName = "Doe";

    private final User user = new User(login, name, lastName);

    private UserStorage userStorage = new UserStorageMap();
    private LoginValidator loginValidator = Mockito.mock(LoginValidator.class);

    private UserService userService = new UserService(userStorage, loginValidator);

    @Test
    public void shouldFindUser() {

        //given
        BDDMockito.given(loginValidator.isValid(login)).willReturn(true);
        userStorage.delete(login);
        userStorage.create(user);
        User result = null;

        // when

        try {
            result = userService.find(login);
        } catch (LoginDoesNotExistException e) {

        }

        // then
        Assert.assertEquals(user, result);

    }

    @Test
    public void shouldAddUser() {

        // given
        userStorage.delete(login);
        BDDMockito.given(loginValidator.isValid(login)).willReturn(true);

        // when
        userService.add(login, name, lastName);

        // then
        Assert.assertEquals(user, userStorage.read(login));
    }

    @Test
    public void shouldNotAddUserWhenLoginValidationFails() {

        // given
        userStorage.delete(login);
        BDDMockito.given(loginValidator.isValid(ArgumentMatchers.any())).willReturn(false);

        // when
        userService.add(login, name, lastName);

        // then
        Assert.assertEquals(null, userStorage.read(login));
    }

    @Test
    public void shouldNotAddUserWhenLoginAlreadyExists() {

        // given

        userStorage.delete(login);
        User existingUser = new User(login, name + "XX", lastName + "XX");
        userStorage.create(existingUser);
        BDDMockito.given(loginValidator.isValid(ArgumentMatchers.any())).willReturn(true);

        // when
        try {
            userService.add(login, name, lastName);
        } catch (LoginExistsException e) {

        }

        // then
        Assert.assertEquals(existingUser, userStorage.read(login));
        ;
    }

    @Test(expected = LoginExistsException.class)
    public void shouldThrowExceptionWhileAddingWhenLoginAlreadyExists() {

        // given
        userStorage.delete(login);
        userStorage.create(user);
        BDDMockito.given(loginValidator.isValid(ArgumentMatchers.any())).willReturn(true);

        // when
        userService.add(login, name, lastName);

        // then

    }

    @Test
    public void shouldUpdateUserName() {

        //given
        userStorage.delete(login);
        userStorage.create(user);
        String newName = "Jane";


        // when
        userService.updateName(login, newName);

        // then
        Assert.assertEquals(new User(login, newName, lastName), userStorage.read(login));
    }

    @Test
    public void shouldUpdateUserLastName() {

        //given
        userStorage.delete(login);
        userStorage.create(user);
        String newLastName = "Demolition";


        // when
        userService.updateLastName(login, newLastName);

        // then
        Assert.assertEquals(new User(login, name, newLastName), userStorage.read(login));

    }

    @Test
    public void shouldDeleteUser() {

        //given
        userStorage.create(user);

        // when
        userService.delete(login);

        // then
        Assert.assertEquals(null, userStorage.read(login));
    }

    @Test
    public void shouldNotDeleteNotExistingUser() {

        //given
        userStorage.delete(login);

        // when
        try {
            userService.delete(login);
        } catch (LoginDoesNotExistException e) {

        }

        // then
        Assert.assertEquals(null, userStorage.read(login));
    }

    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenDeletingNotExistingUser() {

        //given
        userStorage.delete(login);

        // when
        userService.delete(login);

        // then

    }

    @Test
    public void shouldNotUpdateNameOfNotExistingUser() {

        //given
        userStorage.delete(login);
        String newName = "Jane";


        // when
        try {
            userService.updateName(login, newName);
        } catch (LoginDoesNotExistException e) {

        }
        // then
        Assert.assertEquals(null, userStorage.read(login));
    }

    @Test
    public void shouldNotUpdateLastNameOfNotExistingUser() {

        //given
        userStorage.delete(login);
        String newLastName = "Demolition";


        // when
        try {
            userService.updateLastName(login, newLastName);
        } catch (LoginDoesNotExistException e) {

        }
        // then
        Assert.assertEquals(null, userStorage.read(login));
    }


    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenUpdatingNameOfNotExistingUser() {
        //given
        userStorage.delete(login);
        String newName = "Jane";

        // when
        userService.updateName(login, newName);

        // then
    }

    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenUpdatingLastNameOfNotExistingUser() {

        //given
        userStorage.delete(login);
        String newLastName = "Demolition";

        // when
        userService.updateLastName(login, newLastName);

        // then
    }

    @Test(expected = LoginDoesNotExistException.class)
    public void shouldThrowExceptionWhenUserIsNotFound() {

        //given
        userStorage.delete(login);

        // when
        userService.find(login);

        // then

    }
}
