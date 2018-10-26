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
}
