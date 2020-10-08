package service;

import Entity.Card;
import dto.CardDTO;
import repository.CardRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardService {
    CardRepositoryImpl cardRepository;

    public CardDTO register(Card card) throws SQLException {
        return fromCardDto(cardRepository.addCard(card));
    }

    public CardDTO getCardById(int id) throws SQLException {
        return fromCardDto(cardRepository.getById(id));
    }

    public List<CardDTO> getAll() throws SQLException {
        List<Card> listCard = cardRepository.getAll();
        List<CardDTO> listCardDto = new ArrayList<>();

        for (Card card : listCard) {
            listCardDto.add(fromCardDto(card));
        }
        return listCardDto;
    }

    private CardDTO fromCardDto(Card card) {
        return new CardDTO(card.getId(), card.getCardNumber(), card.getCardBalance(), card.getAccountId());
    }
}