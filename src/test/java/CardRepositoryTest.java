import Entity.Card;
import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.CardRepositoryImpl;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardRepositoryTest {
    private static final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final CardRepositoryImpl cardRepository = new CardRepositoryImpl(jdbcConnectionPool);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(CardRepositoryTest.class.getResource("/CreateTables.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreateTables)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void addCardTest() throws SQLException {
        Card tempCard = new Card();
        tempCard.setName("Alex");
        tempCard.setAge(28);

        cardRepository.addUser(tempCard);

        final Card actual = cardRepository.getCardById(0);
        assertEquals(tempCard.getName(), actual.getName());
        assertEquals(tempCard.getAge(), actual.getAge());
    }

    @Test
    public void getCardByIdTest() throws SQLException {
        User tempCard = new User();
        tempCard.setName("Alex");
        tempCard.setAge(28);
        cardRepository.addUser(tempCard);
        final User actual = cardRepository.getUserById(0);
        assertEquals(tempCard.getName(), actual.getName());
        assertEquals(tempCard.getAge(), actual.getAge());
    }
}