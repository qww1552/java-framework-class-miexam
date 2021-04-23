import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    String name = "hulk";
    String password = "1234";

    static UserDao userDao;

    @BeforeAll
    public static void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = applicationContext.getBean("userDao", UserDao.class);
    }


    @Test
    public void testGet() throws SQLException, ClassNotFoundException {
        Integer id = 1;

        User user = userDao.get(id);

        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insert() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        userDao.insert(user);

        User insertedUser = userDao.get(user.getId());

        assertThat(user.getId(), greaterThan(0));
        assertThat(insertedUser.getId(), is(user.getId()));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void update() throws SQLException {
        User user = new User();
        user.setName("minsung");
        user.setPassword("1111");

        userDao.insert(user);

        user.setName(name);
        user.setPassword(password);

        userDao.update(user);
        User updatedUser = userDao.get(user.getId());

        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getName(), is(name));
        assertThat(updatedUser.getPassword(), is(password));
    }

    @Test
    public void delete() throws SQLException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        userDao.insert(user);

        userDao.delete(user.getId());
        User deletedUser = userDao.get(user.getId());

        assertThat(deletedUser, nullValue());
    }
}