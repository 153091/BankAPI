import Entity.Card;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
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
    private static final DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final CardRepositoryImpl cardRepository = new CardRepositoryImpl(dataSource);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/TablesInit.sql").toURI())));
        final String sqlPopulate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/Populate.sql").toURI())));
        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlCreate)) {
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(sqlPopulate)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void addCardTest() throws SQLException {
        Card tempCard = new Card();
        tempCard.setAccountId(0);
        tempCard.setNumber("111 222 555");
        tempCard.setBalance(200);

        cardRepository.addCard(tempCard);

        final Card actual = cardRepository.getCardById(1);
        assertEquals(tempCard.getNumber(), actual.getNumber());
        assertEquals(tempCard.getBalance(), actual.getBalance());
        assertEquals(tempCard.getAccountId(), actual.getAccountId());
    }

    @Test
    public void getCardByIdTest() throws SQLException {
        Card tempCard = new Card();
        tempCard.setAccountId(0);
        tempCard.setNumber("111 222 333");
        tempCard.setBalance(200);
        cardRepository.addCard(tempCard);
        final Card actual = cardRepository.getCardById(1);
        assertEquals(tempCard.getNumber(), actual.getNumber());
        assertEquals(tempCard.getBalance(), actual.getBalance());
        assertEquals(tempCard.getAccountId(), actual.getAccountId());
    }
}