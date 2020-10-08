package repository;

import Entity.Account;
import Entity.Card;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private static final String ADD_CARD = "insert into cards (account_id, number, balance) values (?, ?, ?)";
    private static final String GET_ALL_CLIENT_CARDS = "select * from cards where account_id = ?";
    private static final String GET_CARD_BY_ID = "select * from cards where id = ?";
    private static final String UPDATE_CARD = "update cards set balance = ?, number = ? where id = ?";
    private final static String DELETE_CARD = "delete from cards where id = ?";

    private DataSource dataSource;

    public CardRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Card addCard(Card card) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(ADD_CARD, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, card.getAccountId());
            ps.setString(2, card.getNumber());
            ps.setDouble(3, card.getBalance());
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating card failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getInt(1));
                    return card;
                } else {
                    throw new SQLException("Creating card failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Card> getAllCards(Account account) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_CLIENT_CARDS)) {
            stmt.setInt(1, account.getId());
            ResultSet rs = stmt.executeQuery();
            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                Card card = Card.builder()
                        .id(rs.getInt("id"))
                        .accountId(rs.getInt("account_id"))
                        .number(rs.getString("number"))
                        .balance(rs.getDouble("balance"))
                        .build();
                cards.add(card);
            }
            if (cards.isEmpty()) {
                throw new SQLException("Not found any account!");
            } else {
                return cards;
            }
        }
    }

    @Override
    public Card getCardById(Integer id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_CARD_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Card card = Card.builder()
                        .id(rs.getInt("id"))
                        .accountId(rs.getInt("account_id"))
                        .number(rs.getString("number"))
                        .balance(rs.getDouble("balance"))
                        .build();
                return card;
            }
            else {
                throw new SQLException("User not found.");
            }
        }
    }

    @Override
    public void updateCard(Card card) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(UPDATE_CARD)) {
            stmt.setDouble(1, card.getBalance());
            stmt.setString(2, card.getNumber());
            stmt.setInt(3, card.getId());
            stmt.execute();
        } catch (SQLException throwabl) {
            throwabl.printStackTrace();
        }
    }

    @Override
    public boolean deleteCardById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_CARD)) {
            stmt.setInt(1, id);
            int affectedRow = stmt.executeUpdate();

            return affectedRow != 0;
        }
    }
}
