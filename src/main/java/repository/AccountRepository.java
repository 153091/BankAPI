package repository;

import impl.Account;
import impl.User;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    // add account to given user
    void addAccount(User client, Account account) throws SQLException;

    // return all accounts of given user
    List<Account> getAllUserAccounts(User client) throws SQLException;

}
