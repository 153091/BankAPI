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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private static final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final UserRepositoryImpl userRepository = new UserRepositoryImpl(jdbcConnectionPool);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/TablesInit.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreate)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void () throws SQLException {
        User expectedUser = new User();
        expectedUser.setName("Nikita");
        expectedUser.setSurname("Bazhenov");
        expectedUser.setAge(32);

        final User saved = userRepository.save(expectedUser);
        final User actual = userRepository.getById(saved.getId());
        assertEquals(expectedUser.getName(), actual.getName());
        assertEquals(expectedUser.getSurname(), actual.getSurname());
        assertEquals(expectedUser.getAge(), actual.getAge());
    }

    @Test
    public void testGetById() throws SQLException {
        User expectedUser = new User();
        expectedUser.setName("Nikita");
        expectedUser.setSurname("Bazhenov");
        expectedUser.setAge(32);

        final User saved = userRepository.save(expectedUser);
        final User actual = userRepository.getById(0);
        assertEquals(expectedUser.getName(), actual.getName());
        assertEquals(expectedUser.getSurname(), actual.getSurname());
        assertEquals(expectedUser.getAge(), actual.getAge());
    }
}