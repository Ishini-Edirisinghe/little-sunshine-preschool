package lk.ijse.preschool.model;

import lk.ijse.preschool.dto.Event;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventModel {
    public static boolean save(String event_no, String name, String month) throws SQLException {

        String sql = "INSERT INTO event(event_no,name,month) VALUES (?,?,?)";

        return CrudUtil.execute(sql,event_no,name,month);
    }

    public static Event search(String event_no) throws SQLException {
        String sql = "SELECT * FROM event WHERE event_no = ?";
        ResultSet resultSet = CrudUtil.execute(sql, event_no);

        if(resultSet.next()) {
            String event_event_no = resultSet.getString(1);
            String event_name = resultSet.getString(2);
            String event_month = resultSet.getString(3);


            return new Event(event_event_no, event_name,event_month);
        }
        return null;
    }

    public static boolean deleteEvent(String code) throws SQLException {
        String sql = "DELETE FROM Event WHERE event_no = ?";
        return CrudUtil.execute(sql,code);
    }

    public static boolean update(String event_no, String name, String month) throws SQLException {
        String sql = "UPDATE event SET  name = ?, month = ? WHERE event_no = ?";
        return CrudUtil.execute(sql,name,month,event_no);

    }

    public static List<Event> getAll() throws SQLException {
        String sql = "SELECT * FROM event";

        List<Event> eveData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            eveData.add(new Event(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
        }
        return eveData;
    }
}
