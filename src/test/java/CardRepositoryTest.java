import Entity.Account;
import Entity.Card;
import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AccountRepositoryImpl;
import repository.CardRepositoryImpl;
import repository.UserRepositoryImpl;

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

        Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlCreate)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void testAddCard() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(dataSource);
        Account savedAccount = accountRepository.addAccount(tempAccount);

        Card tempCard = new Card();
        tempCard.setAccountId(savedAccount.getId());
        tempCard.setNumber("111 222 555");
        tempCard.setBalance(200);

        final Card saved = cardRepository.addCard(tempCard);

        final Card actual = cardRepository.getCardById(saved.getId());
        assertEquals(saved.getNumber(), actual.getNumber());
        assertEquals(saved.getBalance(), actual.getBalance());
        assertEquals(saved.getAccountId(), actual.getAccountId());
    }

    @Test
    public void testGetCardById() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(dataSource);
        Account savedAccount = accountRepository.addAccount(tempAccount);

        Card tempCard = new Card();
        tempCard.setAccountId(savedAccount.getId());
        tempCard.setNumber("111 222 555");
        tempCard.setBalance(200);

        final Card saved = cardRepository.addCard(tempCard);
        final Card actual = cardRepository.getCardById(saved.getId());
        assertEquals(saved.getNumber(), actual.getNumber());
        assertEquals(saved.getBalance(), actual.getBalance());
        assertEquals(saved.getAccountId(), actual.getAccountId());
    }

    @Test
    public void testGetAllCards() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(dataSource);
        Account savedAccount = accountRepository.addAccount(tempAccount);

        Card tempCard = new Card();
        tempCard.setAccountId(savedAccount.getId());
        tempCard.setNumber("111 222 555");
        tempCard.setBalance(200);

        final Card saved = cardRepository.addCard(tempCard);

        List<Card> allCards = cardRepository.getAllCards(savedAccount);
        assertEquals(allCards.size(), 1);


            final Card actual = cardRepository.getCardById(saved.getId());
            assertEquals(allCards.get(0).getNumber(), actual.getNumber());
            assertEquals(allCards.get(0).getBalance(), actual.getBalance());
            assertEquals(allCards.get(0).getAccountId(), actual.getAccountId());

    }

    @Test
    public void testDeleteCard() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(dataSource);
        Account savedAccount = accountRepository.addAccount(tempAccount);

        Card tempCard = new Card();
        tempCard.setAccountId(savedAccount.getId());
        tempCard.setNumber("111 222 555");
        tempCard.setBalance(200);

        final Card saved = cardRepository.addCard(tempCard);

        cardRepository.deleteCardById(saved.getId());

        boolean result = false;

        try {
            cardRepository.getCardById(1);
        } catch (SQLException ex) {
            result = true;
        } finally {
            assertEquals(result, true);
        }
    }

    @Test
    public void testUpdateCard() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(dataSource);
        Account savedAccount = accountRepository.addAccount(tempAccount);

        Card tempCard = new Card();
        tempCard.setAccountId(savedAccount.getId());
        tempCard.setNumber("111 222 555");
        tempCard.setBalance(200);

        Card saved = cardRepository.addCard(tempCard);

        saved.setBalance(10000);
        cardRepository.updateCard(saved);

        final Card actual = cardRepository.getCardById(saved.getId());
        assertEquals(saved.getNumber(), actual.getNumber());
        assertEquals(saved.getBalance(), actual.getBalance());
    }
}