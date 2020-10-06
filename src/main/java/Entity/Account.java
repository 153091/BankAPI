package Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private int id;
    private User user;
    private String number;

}
