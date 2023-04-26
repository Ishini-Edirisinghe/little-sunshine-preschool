package lk.ijse.preschool.model;

import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.Syllabus;
import lk.ijse.preschool.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SyllabusModel {
    public static boolean save(String subject_id, String sub_name) throws SQLException {

        String sql = "INSERT INTO syllabus(subject_id,sub_name) VALUES (?,?)";

        return CrudUtil.execute(sql, subject_id, sub_name);

    }

    public static Syllabus search(String subject_id) throws SQLException {
        String sql = "SELECT * FROM syllabus WHERE subject_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, subject_id);

        if(resultSet.next()) {
            String syllabus_id = resultSet.getString(1);
            String syllabus_sub_name = resultSet.getString(2);
            String  stid= resultSet.getString(3);




            return new Syllabus(syllabus_id,syllabus_sub_name,stid);
        }
        return null;
    }

    public static boolean update(String subject_id, String sub_name) throws SQLException {
        String sql = "UPDATE syllabus SET  sub_name=? WHERE subject_id = ?";
        return CrudUtil.execute(sql,sub_name,subject_id);

    }

    public static boolean deleteSyllabus(String code) throws SQLException {
        String sql = "DELETE FROM syllabus WHERE subject_id = ?";
        return CrudUtil.execute(sql,code);
    }

    public static List<Syllabus> getAll() throws SQLException {
        String sql = "SELECT * FROM syllabus";

        List<Syllabus> syllaData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            syllaData.add(new Syllabus(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
        }
        return syllaData;
    }
}
