package accessor;

import entity.Category;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryAccessor extends DataAccessor<Category>{
    @Override
    public Category get(int id) {
        String q = "SELECT * FROM category WHERE categoryid = " + id;
        Category category = null;
        try{
            ResultSet rs = query(q);
            if(!rs.next()){
                return null;
            }
            category = new Category(id, rs.getString("categoryName"), rs.getString("categoryDescription"),
                                    rs.getFloat("bikeValue"), rs.getFloat("costRatio"));
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> getAll() {
        String q = "SELECT * FROM category" ;
        List<Category> categories = new ArrayList<Category>();
        try{
            ResultSet rs = query(q);
            while(rs.next()){
                categories.add(new Category(rs.getInt("categoryId"), rs.getString("categoryName"),
                                            rs.getString("categoryDescription"), rs.getFloat("bikeValue"),
                                            rs.getFloat("costRatio")));
            }
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        return categories;
    }

    @Override
    public void update(Category category) {
        String q = "UPDATE category SET " +
                " categoryName = " + "\"" +  category.getCategoryName() + "\"" +
                " , categoryDescription = " + "\"" +  category.getCategoryDescription() + "\"" +
                " , bikeValue = " + category.getBikeValue() +
                " , costRatio = " + category.getCostRatio() +
                " WHERE categoryId = " + category.getCategoryId();
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public void save(Category category) {
        String q = "insert into category(categoryName, categoryDescription, bikeValue, costRatio)\n" +
                "values( " +
                " categoryName = " + "\"" +  category.getCategoryName() + "\"" +
                " , categoryDescription = " + "\"" +  category.getCategoryDescription() + "\"" +
                " , bikeValue = " + category.getBikeValue() +
                " , costRatio = " + category.getCostRatio() +
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
    public void delete(int categoryId) {
        String q = "DELETE FROM category WHERE categoryId = " + categoryId;
        System.out.println(q);
        try{
            execute(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
