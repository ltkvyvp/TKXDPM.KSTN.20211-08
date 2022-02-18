package entity;

public class User {

    private int userId;
    private String userName;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
