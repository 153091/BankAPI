package repository;

import Entity.Account;
import Entity.Card;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private DataSource dataSource;

    public CardRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addCard(Account account, Card card) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_ACCOUNT)){
            ps.setInt(1, user.getId());
            ps.setString(2, account.getNumber());
            ps.execute();
        }
    }

    @Override
    public List<Card> getAllCards(Account account) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_ACCOUNTS_BY_USER_ID)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .build();
                accounts.add(account);
            }
            if (accounts.isEmpty()) {
                throw new SQLException("Not found any account!");
            } else {
                return accounts;
            }
        }
    }

    @Override
    public Card getCardById(Integer id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_ACCOUNT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .build();
                return account;
            }
            else {
                throw new SQLException("User not found.");
            }
        }
    }

    @Override
    public void updateCard(Card card) throws SQLException {

    }
}
