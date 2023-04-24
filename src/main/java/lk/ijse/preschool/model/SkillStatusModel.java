package lk.ijse.preschool.model;

import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.SkillStatus;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.tm.SkillStatusTM;
import lk.ijse.preschool.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillStatusModel {
    public static boolean save(String stId,String stName,String skill_name, String status) throws SQLException {

        String sql = "INSERT INTO student_skill_status(studentId,studentName,skillName,status) VALUES (?,?,?,?)";

        return CrudUtil.execute(sql,stId,stName,skill_name,status);


    }

    public static List<String> getSkill_name() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT skill_name FROM student_skill_status";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static List<String> getStatus() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT status FROM student_skill_status";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static boolean deleteSkill_name(String code) throws SQLException {
        String sql = "DELETE FROM student_skill_status WHERE skill_name = ?";
        return CrudUtil.execute(sql,code);

    }

    public static boolean update(String stId,String stName,String skill_name, String status) throws SQLException {
        String sql = "UPDATE student_skill_status SET studentName = ?, skillName = ? ,status=? WHERE studentId = ?";
        return CrudUtil.execute(sql,stName, skill_name,status,stId);

    }

    /*public static List<SkillStatus> getAll() throws SQLException {
        String sql = "SELECT * FROM student_skill_status";

        List<SkillStatus> stuData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            stuData.add(new SkillStatus(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return stuData;
    }*/

    public static SkillStatus searchByIdGetSkills(String studentId) throws SQLException {
        String sql = "SELECT * FROM student_skill_status WHERE stId = ?";

        ResultSet resultSet = CrudUtil.execute(sql,studentId);
        if (resultSet.next()) {
            return new SkillStatus(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
        }
        return null;
    }

    /*public static boolean update(SkillStatusTM selectedItem) throws SQLException {
        String sql = "UPDATE student_skill_status SET studentName = ?, skillName = ? ,status=? WHERE studentId = ?";
        return CrudUtil.execute(sql,selectedItem.getStName(),selectedItem.getSkill_name(),selectedItem.getStatus(),selectedItem.getStid());
    }*/
}
