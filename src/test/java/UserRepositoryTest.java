import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
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
    public void addUserTest() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);

        userRepository.addUser(tempUser);

        final User actual = userRepository.getUserById(0);
        assertEquals(tempUser.getName(), actual.getName());
        assertEquals(tempUser.getAge(), actual.getAge());
    }

    @Test
    public void getUserByIdTest() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Slava");
        tempUser.setAge(28);
        userRepository.addUser(tempUser);
        final User actual = userRepository.getUserById(0);
        assertEquals(tempUser.getName(), actual.getName());
        assertEquals(tempUser.getAge(), actual.getAge());
    }

    @Test
    public void getAllTest() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Gosha");
        tempUser.setAge(50);
        userRepository.addUser(tempUser);

        User tempUser2 = new User();
        tempUser2.setName("Misha");
        tempUser2.setAge(28);
        userRepository.addUser(tempUser2);

        List<User> allUsers = userRepository.getAll();
        assertEquals(allUsers.size(), 2);

        for (int i = 0; i < allUsers.size(); i++) {
            final User actual = userRepository.getUserById(i);
            assertEquals(allUsers.get(i).getName(), actual.getName());
            assertEquals(allUsers.get(i).getAge(), actual.getAge());
        }
    }

    @Test
    void updateUserTest() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Petya");
        tempUser.setAge(50);
        tempUser.setId(0);
        userRepository.addUser(tempUser);

        tempUser.setName("Kolya");
        tempUser.setAge(20);
        userRepository.updateUser(tempUser);
        final User actual = userRepository.getUserById(0);
        assertEquals(tempUser.getName(), actual.getName());
        assertEquals(tempUser.getAge(), actual.getAge());
    }
}