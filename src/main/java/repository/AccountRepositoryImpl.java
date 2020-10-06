package repository;

import impl.Account;
import impl.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private final Connection conn;

    public AccountRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addAccount(User user, Account account) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("select id, number, amount, currency from accounts where clients_id = ?");

    }

    @Override
    public List<Account> getAllUserAccounts(User user) throws SQLException {
        return null;
    }
}
