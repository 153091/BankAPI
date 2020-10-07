import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.UserRepositoryImpl;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private static final DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);


    @BeforeAll
    static void beforeAll() throws Exception {
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
        tempUser.setName("Alex");
        tempUser.setAge(28);
        userRepository.addUser(tempUser);
        final User actual = userRepository.getUserById(0);
        assertEquals(tempUser.getName(), actual.getName());
        assertEquals(tempUser.getAge(), actual.getAge());
    }
}