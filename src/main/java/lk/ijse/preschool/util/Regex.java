package lk.ijse.preschool.util;

import lk.ijse.preschool.dto.Student;

public class Regex {
    private static final String USERNAME_REGEX = "^[A-Za-z0-9]{3,}$";
    private static final String PASSWORD_REGEX = "[aA-zZ0-9]{8,20}$";
    private static final String StudentID_REGEX = "^S\\d{3}$";
    private static final String MOBILE_REGEX = "^\\+?\\d{10}$";
    private static final String TeacherID_REGEX = "^T\\d{3}$\n";
    private static final String ReferanceNo_REGEX = "^R\\d{4}$";
    private static final String ContentNo_REGEX = "^T\\d{3}$\n";

    public static boolean validateUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
    public static boolean validateStudentId(String StId){
        return StId.matches(StudentID_REGEX);
    }

    public static boolean validateContact(String contact) {
        return contact.matches(MOBILE_REGEX);
    }

    public static boolean validateTeacherId(String teachId) {
        return teachId.matches(TeacherID_REGEX);
    }

    public static boolean validateReferanceNo(String ref_no) {
        return ref_no.matches(ReferanceNo_REGEX);
    }

    public static boolean validateContentNo(String subject_id) {
        return subject_id.matches(ContentNo_REGEX);
    }
}
