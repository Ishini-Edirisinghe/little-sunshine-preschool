package lk.ijse.preschool.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentTM {
    private String stId;
    private String name;
    private String address;
    private String DOB;
    private String contact;
    private String parentsName;
}
