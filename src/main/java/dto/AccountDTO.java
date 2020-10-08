package dto;

public class AccountDTO {
    private final int id;
    private final String number;
    private final int userId;

    public AccountDTO(int id, String number, int userId) {
        this.id = id;
        this.number = number;
        this.userId = userId;
    }
}
