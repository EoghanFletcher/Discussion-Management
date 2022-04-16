package dao;

public interface EmployeeAttendanceDaoInterface {

    public boolean confirmAttendance(String uId, String username, String databaseCollection);

    public String getDate();
}
