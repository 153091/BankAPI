package repository;

import impl.Account;
import impl.User;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    List<Account> getAllClientAccounts(User user) throws SQLException;

    Account getAccountById(Integer id) throws SQLException;

    Account getAccountByClientId(User user) throws SQLException;

    void updateAccount(User user, Account account);

}
