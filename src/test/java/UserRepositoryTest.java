import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepositoryImpl;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private static final DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);


    @BeforeEach
    public void beforeAll() throws Exception {
        final String sqlCreate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/TablesInit.sql").toURI())));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreate)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void testAddUser() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);

        final User saved = userRepository.addUser(tempUser);
        final User actual = userRepository.getUserById(saved.getId());

        assertEquals(saved.getName(), actual.getName());
        assertEquals(saved.getAge(), actual.getAge());
    }

    @Test
    public void testGetUserById() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Slava");
        tempUser.setAge(28);

        final User saved = userRepository.addUser(tempUser);
        final User actual = userRepository.getUserById(saved.getId());

        assertEquals(saved.getName(), actual.getName());
        assertEquals(saved.getAge(), actual.getAge());
    }

    @Test
    public void testGetAll() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Gosha");
        tempUser.setAge(50);
        final User saved = userRepository.addUser(tempUser);

        List<User> allUsers = userRepository.getAll();
        assertEquals(allUsers.size(), 1);


            final User actual = userRepository.getUserById(saved.getId());
            assertEquals(allUsers.get(0).getName(), actual.getName());
            assertEquals(allUsers.get(0).getAge(), actual.getAge());

    }

    @Test
    public void testDeleteUser() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Gosha");
        tempUser.setAge(50);
        userRepository.addUser(tempUser);

        userRepository.deleteUserById(0);

        boolean result = false;

        try {
            userRepository.getUserById(0);
        } catch (SQLException ex) {
            result = true;
        } finally {
            assertEquals(result, true);
        }
    }

    @Test
    public void testUpdateUser() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Petya");
        tempUser.setAge(50);

        User saved = userRepository.addUser(tempUser);

        saved.setName("Kolya");
        saved.setAge(20);
        userRepository.updateUser(saved);
        final User actual = userRepository.getUserById(saved.getId());
        assertEquals(saved.getName(), actual.getName());
        assertEquals(saved.getAge(), actual.getAge());
    }
}