package dto;

public class CardDTO {
    private final int id;
    private final String number;
    private final double balance;
    private final int accountId;

    public CardDTO(int id, String number, double balance, int accountId) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.accountId = accountId;
    }
}