package lk.ijse.preschool.model;

import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.SkillStatus;
import lk.ijse.preschool.dto.Student;

import java.sql.SQLException;

public class PlaceStudentModel {
    public static Boolean PlaceStudent(Student s1, SkillStatus s2) throws SQLException {
        try{
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean save = StudentModel.save(s1.getStId(), s1.getName(),s1.getAddress(),s1.getDOB(),s1.getContact(),s1.getParentName(),s1.getTeachId());
            if (save){
              //  System.out.println("Done 1");
                boolean saveSkills = SkillStatusModel.save(s2.getStid(), s2.getStName(), s2.getCounting(), s2.getCrafting(),s2.getDrawing(), s2.getReading(), s2.getSinging(), s2.getWriting());
                if (saveSkills){
               //     System.out.println("Done 2");
                    DBConnection.getInstance().getConnection().commit();
                    return true;
                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;
        }finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
