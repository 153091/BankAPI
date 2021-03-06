package repository;

import Entity.Account;
import Entity.Card;

import java.sql.SQLException;
import java.util.List;

public interface CardRepository {

    Card addCard(Card card) throws SQLException;

    List<Card> getAllCards(Account account) throws SQLException;

    Card getCardById(Integer id) throws SQLException;

    void updateCard(Card card) throws SQLException;

    boolean deleteCardById(int id) throws SQLException;

}
