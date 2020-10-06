package Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private int id;
    private int userId;
    private String number;

}
