package lk.ijse.preschool.model;

import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.Payment;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public static Payment search(String stid) throws SQLException {
        String sql = "SELECT * FROM payment WHERE ref_no = ?";
        ResultSet resultSet = CrudUtil.execute(sql, stid);

        if(resultSet.next()) {
            String payment_ref_no = resultSet.getString(1);
            String payment_date = resultSet.getString(2);
            String payment_stid = resultSet.getString(3);
            String payment_type = resultSet.getString(4);

            return new Payment(payment_ref_no, payment_date, payment_stid, payment_type);
        }
        return null;
    }

    public static boolean save(String ref_no, String date, String stid, String type) throws SQLException {
        String sql = "INSERT INTO payment(ref_no,date,stid,type) VALUES (?,?,?,?)";

        return CrudUtil.execute(sql, ref_no, date, stid, type);

    }


    public static boolean deletePayment(String code) throws SQLException {
        String sql = "DELETE FROM payment WHERE ref_no = ?";
        return CrudUtil.execute(sql,code);
    }

    public static boolean update(String ref_no, String date, String stid, String type) throws SQLException {
        String sql = "UPDATE payment SET  date = ?, stid = ?,type=? WHERE ref_no = ?";
        return CrudUtil.execute(sql,date,stid, type,ref_no);
    }

    public static List<Payment> getAll() throws SQLException { String sql = "SELECT * FROM payment";

        List<Payment> payData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            payData.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }
        return payData;
    }

    public static List<String> getType() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT type FROM payment";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static Payment getPaymentType(String ref_no) throws SQLException {
        String sql = "SELECT * FROM payment WHERE ref_no = ?";

        ResultSet resultSet = CrudUtil.execute(sql,ref_no);
        if (resultSet.next()) {
            return new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;

    }
}