package lk.ijse.preschool.model;

import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    public static boolean save(String stid, String name, String address,String DOB,String contact,String parentsName) throws SQLException {

            String sql = "INSERT INTO student(stid,name,address,DOB,contact,parentsName) VALUES (?,?,?,?,?,?)";

            return CrudUtil.execute(sql, stid, name, address, DOB,contact,parentsName);

    }
    public static Student search(String stid) throws SQLException {
        String sql = "SELECT * FROM student WHERE stid = ?";
        ResultSet resultSet = CrudUtil.execute(sql, stid);

        if(resultSet.next()) {
            String student_stid = resultSet.getString(1);
            String student_name = resultSet.getString(2);
            String student_address = resultSet.getString(3);
            String student_DOB = resultSet.getString(4);
            String student_contact = resultSet.getString(5);
            String student_parentsName = resultSet.getString(6);
            String teachId = resultSet.getString(7);

            return new Student(student_stid, student_name, student_address, student_DOB,student_contact,student_parentsName,teachId);
        }
        return null;
    }

    public static boolean deleteStudent(String code) throws SQLException {
            String sql = "DELETE FROM student WHERE stid = ?";
            return CrudUtil.execute(sql,code);

    }

    public static boolean update(String id,String name,String address,String DOB,String contact,String parentName) throws SQLException {
        String sql = "UPDATE student SET  name = ?, address = ?,DOB=?,contact=?,parentsName=? WHERE stid = ?";
        return CrudUtil.execute(sql,name, address,DOB, contact,parentName,id);

    }

    public static List<Student> getAll() throws SQLException {

        String sql = "SELECT * FROM student";

        List<Student> stuData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            stuData.add(new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return stuData;
    }
}
