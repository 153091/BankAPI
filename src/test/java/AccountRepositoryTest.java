import Entity.Account;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.AccountRepositoryImpl;


import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountRepositoryTest {
    private static final DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(dataSource);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreate = String.join("\n",Files.readAllLines(Paths.get(Server.class.getResource("/TablesInit.sql").toURI())));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreate)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void addAccountTest() throws SQLException {
        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 14");
        tempAccount.setUserId(1);

        accountRepository.addAccount(tempAccount);

        final Account actual = accountRepository.getAccountById(0);
        assertEquals(tempAccount.getNumber(), actual.getNumber());
        assertEquals(tempAccount.getUserId(), actual.getUserId());
    }

    @Test
    public void getAccountByIdTest() throws SQLException {
        Account tempAccount = new Account();
        tempAccount.setNumber("12 13 14");
        tempAccount.setUserId(1);
        accountRepository.addAccount(tempAccount);
        final Account actual = accountRepository.getAccountById(0);
        assertEquals(tempAccount.getNumber(), actual.getNumber());
        assertEquals(tempAccount.getUserId(), actual.getUserId());
    }
}