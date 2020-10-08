import Entity.Account;
import Entity.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AccountRepositoryImpl;
import repository.UserRepositoryImpl;


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

        Connection connection = dataSource.getConnection();
          try (PreparedStatement statement = connection.prepareStatement(sqlCreate)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void testAddAccount() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());


        final Account saved = accountRepository.addAccount(tempAccount);
        final Account actual = accountRepository.getAccountById(saved.getId());
        assertEquals(saved.getNumber(), actual.getNumber());
        assertEquals(saved.getUserId(), actual.getUserId());
    }

    @Test
    public void testGetAccountById() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());


        final Account saved = accountRepository.addAccount(tempAccount);
        final Account actual = accountRepository.getAccountById(saved.getId());
        assertEquals(saved.getNumber(), actual.getNumber());
        assertEquals(saved.getUserId(), actual.getUserId());
    }

    @Test
    public void testGetAllUserAccounts() throws SQLException {
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());


        final Account saved = accountRepository.addAccount(tempAccount);
        final Account actual = accountRepository.getAccountById(saved.getId());

        List<Account> allAccounts = accountRepository.getAllUserAccounts(savedUser);
        assertEquals(allAccounts.size(), 1);

            assertEquals(allAccounts.get(0).getNumber(), actual.getNumber());
            assertEquals(allAccounts.get(0).getUserId(), actual.getUserId());

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

        final Account saved = accountRepository.addAccount(tempAccount);
        accountRepository.deleteAccountById(saved.getId());

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
        User tempUser = new User();
        tempUser.setName("Alex");
        tempUser.setAge(28);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(dataSource);
        User savedUser = userRepository.addUser(tempUser);

        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 55");
        tempAccount.setUserId(savedUser.getId());

        final Account saved = accountRepository.addAccount(tempAccount);

        saved.setNumber("234");
        accountRepository.updateAccount(saved);

        final Account actual = accountRepository.getAccountById(saved.getId());
        assertEquals(tempAccount.getNumber(), actual.getNumber());
        assertEquals(tempAccount.getUserId(), actual.getUserId());
    }
}