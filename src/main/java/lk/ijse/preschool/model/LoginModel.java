package lk.ijse.preschool.model;

import lk.ijse.preschool.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public static boolean userCheckedInDB(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE user_name= ? AND password=?";
        ResultSet resultSet = CrudUtil.execute(sql, username, password);
        if(resultSet.next()){
            return true;
        }
        return false;
    }
}
