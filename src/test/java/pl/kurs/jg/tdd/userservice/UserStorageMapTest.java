package pl.kurs.jg.tdd.userservice;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UserStorageMapTest {

    Map<String, User> userMap = new HashMap<>();
    UserStorageMap subject = new UserStorageMap(userMap);
    User user = new User("jdoe", "John", "Doe");

    @Test
    public void shouldCreateUser() {

        // given

        // when
        subject.create(user);

        //then
        Assert.assertEquals(user, userMap.get(user.getLogin()));
    }

    @Test
    public void shouldReadUser() {
        // given
        userMap.put(user.getLogin(), user);

        // when
        User result = subject.read(user.getLogin());

        // then
        Assert.assertEquals(user, result);
    }

    @Test
    public void shouldUpdateUser() {

        // given
        userMap.put(user.getLogin(), user);
        String newName = "Jenny";
        String newLastName = "Doppelganger";
        User updatedUser = new User(user.getLogin(), newName, newLastName);

        // when
        subject.update(user.getLogin(), updatedUser);

        // then
        Assert.assertEquals(updatedUser, userMap.get(user.getLogin()));
    }

    @Test
    public void shouldDeleteUser() {
        // given
        userMap.clear();
        userMap.put(user.getLogin(), user);

        // when
        subject.delete(user.getLogin());

        // then
        Assert.assertTrue(userMap.isEmpty());

    }
}
