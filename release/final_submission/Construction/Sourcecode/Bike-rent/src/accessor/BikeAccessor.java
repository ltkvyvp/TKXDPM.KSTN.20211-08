package accessor;

import entity.Bike;
import entity.Category;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BikeAccessor extends DataAccessor<Bike>{
    @Override
    public Bike get(int id) {
        String q = "SELECT * FROM bike WHERE bikeId = " + id;
        Bike bike = null;
        try{
            ResultSet rs = query(q);
            if(!rs.next()){
                return null;
            }
            CategoryAccessor categoryAccessor = new CategoryAccessor();
            Category category = categoryAccessor.get(rs.getInt("categoryId"));
            if (category == null) {
                return null;
            }
            bike = new Bike(id, rs.getString("bikeName"), rs.getString("licensePlate"),
                            rs.getFloat("pin"), rs.getBoolean("status"),
                            rs.getFloat("initCost"), rs.getFloat("costPerQuarterHour"),
                            rs.getInt("dockId"), category, rs.getString("imagePath"));
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return bike;
    }

    /**
     * Query a list of bikes in the database with parameter which is a query statement
     * @param q query statement
     * @return list of bikes
     */
    public List<Bike> getByQuery(String q){
        List<Bike> bikes = new ArrayList<Bike>();
        try{
            ResultSet rs = query(q);
            // Có nên đặt CategoryFactory để chỉ lấy ra 1 instance global không?
            CategoryAccessor categoryAccessor = new CategoryAccessor();
            while(rs.next()){
                Category category = categoryAccessor.get(rs.getInt("categoryId"));
                Bike bike = new Bike(rs.getInt("bikeId"), rs.getString("bikeName"), rs.getString("licensePlate"),
                        rs.getFloat("pin"), rs.getBoolean("status"),
                        rs.getFloat("initCost"), rs.getFloat("costPerQuarterHour"),
                        rs.getInt("dockId"), category, rs.getString("imagePath"));
                bikes.add(bike);
            }
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return bikes;
    }

    @Override
    public List<Bike> getAll() {
        String q = "SELECT * FROM bike" ;
        return this.getByQuery(q);
    }

    /**
     * Query list of bike that belongs to a dock
     * @param dockId id of the dock
     * @return list of bikes
     */
    public List<Bike> getBikeByDockId(int dockId) {
        String q = "SELECT * FROM bike where dockId = " + dockId;
        return this.getByQuery(q);
    }

    /**
     * Search a list of bikes in a dock by searched option and searched information
     * @param option select from 4 options {Barcode, BikeName, Status, Category}
     * @param info the information needs to search
     * @param dockId the dock's id
     * @return list of bikes
     */
    public List<Bike> searchBike(String option, String info, int dockId){
        String q;
        if (option.equals("status")) {
            int temp_info;
            if (info.equals("Đang thuê") || info.equals("thuê") || info.equals("đang thuê")){
                temp_info = 1;
            } else if (info.equals("Có sẵn") || info.equals("sẵn") || info.equals("có sẵn")){
                temp_info = 0;
            } else {
                temp_info = -1;
            }
            q = "SELECT * FROM bike where dockId = " + dockId + " and " + option + " = " + temp_info;
        } else if (option.equals("barcode")){
            try {
                int barcodeInfo = Integer.parseInt(info);
                q = "SELECT * FROM bike where dockId = " + dockId + " and bikeId = " + barcodeInfo;
            } catch(NumberFormatException e) {
                return new ArrayList<Bike>();
            }
        } else {
            if (option.equals("category")){
                option = "categoryName";
            }
            q = "SELECT * FROM bike, category where bike.categoryId = category.categoryId and bike.dockId = " + dockId + " and " + option + " LIKE \"%" + info + "%\"";
        }
        System.out.println(q);
        return this.getByQuery(q);
    }

    @Override
    public void update(Bike bike) {
        String q = "UPDATE bike SET " +
                        " bikeName = " + "\"" +  bike.getBikeName() + "\"" +
                        " , licensePlate = " + "\"" +  bike.getLicensePlate() + "\"" +
                        " , pin = " + bike.getPin() +
                        " , status = " + (bike.isStatus() ? 1 : 0) +
                        " , initCost = " + bike.getInitCost() +
                        " , costPerQuarterHour = " + bike.getCostPerQuarterHour() +
                        " , dockId = " + bike.getDockId()+
                        " , categoryId = " + bike.getCategory().getCategoryId() +
                        " , imagePath = " + "\"" + bike.getImagePath() + "\"" +
                        " WHERE bikeId = " + bike.getBikeId();
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    /**
     * Changing status of a bike, from available to renting and vice versa
     * @param bikeId the bike's id
     * @param status status need to be changed
     */
    public void updateStatus(int bikeId, int status){
        String q = "UPDATE bike SET " +
                " status = " + status +
                " WHERE bikeId = " + bikeId;
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public void save(Bike bike) {
        String q = "INSERT INTO bike(bikeName, licensePlate, pin, status, initCost, costPerQuarterHour, dockId, categoryId) VALUES (" +
                    " bikeName = " + "\"" +  bike.getBikeName() + "\"" +
                    " , licensePlate = " + "\"" +  bike.getLicensePlate() + "\"" +
                    " , pin = " + bike.getPin() +
                    " , status = " + (bike.isStatus() ? 1 : 0) +
                    " , initCost = " + bike.getInitCost() +
                    " , costPerQuarterHour = " + bike.getCostPerQuarterHour() +
                    " , dockId = " + bike.getDockId()+
                    " , categoryId = " + bike.getCategory().getCategoryId() +
                    " , imagePath = " + bike.getImagePath() +
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
    public void delete(int bikeId) {
        String q = "DELETE FROM bike WHERE bikeId = " + bikeId;
        System.out.println(q);
        try{
            execute(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
