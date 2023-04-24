package lk.ijse.preschool.model;

import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.Teacher;
import lk.ijse.preschool.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherModel {
    public static boolean save(String teachId, String name, String address,String DOB,String contact) throws SQLException {

        String sql = "INSERT INTO teacher(teachId,name,address,DOB,contact) VALUES (?,?,?,?,?)";

        return CrudUtil.execute(sql, teachId, name, address, DOB,contact);

    }

/*
    public static Teacher searchById(String code) {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Item WHERE code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }
*/

    public static List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT teachId FROM teacher";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static Teacher search(String teachId) throws SQLException {
        String sql = "SELECT * FROM teacher WHERE teachId = ?";
        ResultSet resultSet = CrudUtil.execute(sql,teachId);

        if(resultSet.next()) {
            String teacher_teachId = resultSet.getString(1);
            String teacher_name = resultSet.getString(2);
            String teacher_address = resultSet.getString(3);
            String teacher_DOB = resultSet.getString(4);
            String teacher_contact = resultSet.getString(5);


            return new Teacher(teacher_teachId, teacher_name, teacher_address, teacher_DOB,teacher_contact);
        }
        return null;
    }

    public static boolean deleteStudent(String code) throws SQLException {
        String sql = "DELETE FROM teacher WHERE teachId = ?";
        return CrudUtil.execute(sql,code);
    }

    public static boolean update(String teachId, String name, String address, String DOB, String contact) throws SQLException {
        String sql = "UPDATE teacher SET name = ?, address = ?,DOB=?,contact=? WHERE teachId = ?";
        return CrudUtil.execute(sql,name, address,DOB, contact,teachId);
    }

    public static List<Teacher> getAll() throws SQLException {
        String sql = "SELECT * FROM teacher";

        List<Teacher> teaData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            teaData.add(new Teacher(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return teaData;
    }
}
