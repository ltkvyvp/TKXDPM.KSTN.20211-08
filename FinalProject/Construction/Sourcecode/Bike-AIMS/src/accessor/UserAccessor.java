package accessor;

import entity.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserAccessor extends DataAccessor<User>{
    @Override
    public User get(int id) {
        String q = "SELECT * FROM user WHERE userId = " + id;
        User user = null;
        try{
            ResultSet rs = query(q);
            if(!rs.next()){
                return null;
            }
            user = new User(id, rs.getString("userName"));
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        String q = "SELECT * FROM user" ;
        List<User> users = new ArrayList<User>();
        try{
            ResultSet rs = query(q);
            while(rs.next()){
                users.add(new User(rs.getInt("userId"), rs.getString("userName")));
            }
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        String q = "UPDATE user SET " +
                " userName = " + "\"" +  user.getUserName() + "\"" +
                " WHERE userId = " + user.getUserId();
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public void save(User user) {
        String q = "insert into user(userName)\n" +
                "values (" +
                "\""+ user.getUserName() + "\"" +
                ")";
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int userId) {
        String q = "DELETE FROM user WHERE userId = " + userId;
        System.out.println(q);
        try{
            execute(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
