package lk.ijse.preschool.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data

public class Payment {
    String ref_no;
    String date;
    String stid;
    String type;
}
