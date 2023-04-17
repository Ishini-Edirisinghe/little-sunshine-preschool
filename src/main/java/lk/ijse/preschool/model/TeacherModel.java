package lk.ijse.preschool.model;

import lk.ijse.preschool.db.DBConnection;
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
}
