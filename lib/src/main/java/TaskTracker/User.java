package TaskTracker;

public class User
{
    private int userId;
    private int userType;
    private String userName;
    private String userPassword;
    private String fname;
    private String lname;
    private int manager;
    
    public User(int userId, int userType, String userName, String userPassword, String fname, String lname, int manager) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.userPassword = userPassword;
        this.fname = fname;
        this.lname = lname;
        this.manager = manager;
    }

    public User(int userType, String userName, String userPassword) {
        this.userType = userType;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }
}