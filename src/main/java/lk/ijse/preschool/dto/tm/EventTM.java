package lk.ijse.preschool.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class EventTM {
    private String event_no;
    private String name;
    private String month;
    private JFXButton btn;
}
