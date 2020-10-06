package repository;

import impl.Account;
import impl.User;

import java.sql.SQLException;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    @Override
    public void addAccount(User client, Account account) throws SQLException {

    }

    @Override
    public List<Account> getAllUserAccounts(User client) throws SQLException {
        return null;
    }
}
