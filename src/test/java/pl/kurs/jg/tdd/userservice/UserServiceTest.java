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

        UserService userService = new UserService(userStorage);

        // when
        User result = userService.find(login);

        // then
        Assert.assertEquals(user, result);

    }
}
