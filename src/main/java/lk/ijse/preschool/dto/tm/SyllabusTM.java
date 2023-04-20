package lk.ijse.preschool.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class SyllabusTM {
    private String subject_id;
    private String sub_name;
    private JFXButton btn;
}
