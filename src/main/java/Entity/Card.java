package Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {
    private int id;
    private Account account;
    private String number;
    private double balance;
    private User user;

}
