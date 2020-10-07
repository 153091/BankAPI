import org.h2.jdbcx.JdbcConnectionPool;
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
    private static final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(jdbcConnectionPool);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(AccountRepositoryTest.class.getResource("/CreateTables.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreateTables)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void testSave() throws SQLException {
        Account expectedAccount = new Account();
        expectedAccount.setAccountNumber(401817247);
        expectedAccount.setUsersId(1);

        final Account saved = accountRepository.save(expectedAccount);
        final Account actual = accountRepository.getById(saved.getId());
        assertEquals(expectedAccount.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expectedAccount.getUsersId(), actual.getUsersId());
    }
}