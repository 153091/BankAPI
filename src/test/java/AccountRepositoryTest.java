import Entity.Account;
import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AccountRepositoryImpl;


import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountRepositoryTest {
    private static final DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(dataSource);


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
    public void testAddAccount() throws SQLException {
        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 14");
        tempAccount.setUserId(0);

        accountRepository.addAccount(tempAccount);

        final Account actual = accountRepository.getAccountById(1);
        assertEquals(tempAccount.getNumber(), actual.getNumber());
        assertEquals(tempAccount.getUserId(), actual.getUserId());
    }

    @Test
    public void testGetAccountById() throws SQLException {
        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(0);
        accountRepository.addAccount(tempAccount);
        final Account actual = accountRepository.getAccountById(1);
        assertEquals(tempAccount.getNumber(), actual.getNumber());
        assertEquals(tempAccount.getUserId(), actual.getUserId());
    }

    @Test
    public void testGetAllUserAccounts() throws SQLException {
        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(0);
        accountRepository.addAccount(tempAccount);

        User user = new User();
        user.setId(0);

        List<Account> allAccounts = accountRepository.getAllUserAccounts(user);
        assertEquals(allAccounts.size(), 2);

        for (int i = 0; i < allAccounts.size(); i++) {
            final Account actual = accountRepository.getAccountById(i);
            assertEquals(allAccounts.get(i).getNumber(), actual.getNumber());
            assertEquals(allAccounts.get(i).getUserId(), actual.getUserId());
        }
    }

    @Test
    public void testDeleteCard() throws SQLException {
        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(0);
        accountRepository.addAccount(tempAccount);

        accountRepository.deleteAccountById(1);

        boolean result = false;

        try {
            accountRepository.getAccountById(1);
        } catch (SQLException ex) {
            result = true;
        } finally {
            assertEquals(result, true);
        }
    }

    @Test
    public void testUpdateAccount() throws SQLException {
        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(0);
        tempAccount.setId(0);

        accountRepository.updateAccount(tempAccount);

        final Account actual = accountRepository.getAccountById(0);
        assertEquals(tempAccount.getNumber(), actual.getNumber());
        assertEquals(tempAccount.getUserId(), actual.getUserId());
    }
}