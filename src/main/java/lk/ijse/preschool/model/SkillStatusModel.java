package lk.ijse.preschool.model;

import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.SkillStatus;
import lk.ijse.preschool.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillStatusModel {
    public static boolean save(String stId, String stName, String counting, String crafting, String drawing, String reading, String singing, String writing) throws SQLException {

        String sql = "INSERT INTO student_skill_status(stId,stName,counting,crafting,drawing,reading,singing,writing) VALUES (?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql, stId, stName, counting, crafting, drawing, reading, singing, writing);


    }

    public static List<String> getStatus() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT status FROM student_skill_status";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }


    public static boolean update(String stId, String stName, String counting, String crafting, String drawing, String reading, String singing, String writing) throws SQLException {
        String sql = "UPDATE student_skill_status SET stName = ?, counting = ? ,crafting =? , drawing=?,reading=?,singing=?,writing=? WHERE stId = ?";
        return CrudUtil.execute(sql, stName, counting, crafting, drawing, reading, singing, writing, stId);

    }

    public static SkillStatus searchByIdGetSkills(String studentId) throws SQLException {
        String sql = "SELECT stName,counting,crafting,drawing,reading,singing,writing FROM student_skill_status WHERE stId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, studentId);
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

    public static SkillStatus search(String studentId) throws SQLException {
        String sql = "SELECT * FROM student_skill_status WHERE stId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, studentId);
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



    public static int getStatusCount(String subject,String status) throws SQLException {

        String sql = "SELECT COUNT(*) AS statusCount FROM student_skill_status WHERE "+subject+"=?";
        ResultSet resultSet=CrudUtil.execute(sql,status);
        String string = null;
        while (resultSet.next()) {
            string= resultSet.getString(1);
        }
        return Integer.parseInt(string);
    }
}

