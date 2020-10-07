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
    public void testSave() throws SQLException {
        Card expectedCard = new Card();
        expectedCard.setCardNumber(213124214);
        expectedCard.setCardBalance(121123);
        expectedCard.setAccountId(12);

        final Card saved = cardRepository.save(expectedCard);
        final Card actual = cardRepository.getById(saved.getId());
        assertEquals(expectedCard.getCardNumber(), actual.getCardNumber());
        assertEquals(expectedCard.getCardBalance(), actual.getCardBalance());
        assertEquals(expectedCard.getAccountId(), actual.getAccountId());
    }
}