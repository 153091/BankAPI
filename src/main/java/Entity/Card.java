package Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {
    private int id;
    private int accountId;
    private String number;
    private double balance;

}
