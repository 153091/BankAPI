import Entity.Account;
import Entity.Card;
import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CardRepositoryImpl;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardRepositoryTest {
    private static final DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final CardRepositoryImpl cardRepository = new CardRepositoryImpl(dataSource);


    @BeforeEach
    public void beforeAll() throws Exception {
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

    @Test
    public void getAllCardsTest() throws SQLException {
        Card tempCard = new Card();
        tempCard.setAccountId(0);
        tempCard.setNumber("111 222 333");
        tempCard.setBalance(200);
        cardRepository.addCard(tempCard);

        Account account = new Account();
        account.setId(0);

        List<Card> allCards = cardRepository.getAllCards(account);
        assertEquals(allCards.size(), 2);

        for (int i = 0; i < allCards.size(); i++) {
            final Card actual = cardRepository.getCardById(i);
            assertEquals(allCards.get(i).getNumber(), actual.getNumber());
            assertEquals(allCards.get(i).getBalance(), actual.getBalance());
            assertEquals(allCards.get(i).getAccountId(), actual.getAccountId());
        }
    }

    @Test
    public void updateCardTest() throws SQLException {
        Card tempCard = new Card();
        tempCard.setAccountId(0);
        tempCard.setNumber("111 222 333");
        tempCard.setBalance(200);
        tempCard.setId(0);

        cardRepository.updateCard(tempCard);

        final Card actual = cardRepository.getCardById(0);
        assertEquals(tempCard.getNumber(), actual.getNumber());
        assertEquals(tempCard.getBalance(), actual.getBalance());
    }
}