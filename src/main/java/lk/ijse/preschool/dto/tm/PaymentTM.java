package lk.ijse.preschool.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class PaymentTM {
    private String ref_no;
    private String date;
    private String stid;
    private String type;
    private JFXButton btn;
}
