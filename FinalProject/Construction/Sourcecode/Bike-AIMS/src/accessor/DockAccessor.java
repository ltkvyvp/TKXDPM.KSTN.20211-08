package accessor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Dock;

public class DockAccessor extends DataAccessor<Dock>{

    /**
     * Query a list of docks in the database with parameter which is a query statement
     * @param q query statement
     * @return list of docks
     */
    public List<Dock> getByQuery(String q) {
        List<Dock> docks = new ArrayList<Dock>();
        try{
            ResultSet rs = query(q);
            if (rs == null)
                return docks;
            while(rs.next()){
                Dock dock = new Dock(rs.getInt("dockId"), rs.getString("dockName"), rs.getString("address"),
                        rs.getFloat("dockArea"), rs.getInt("availableBikes"),
                        rs.getInt("emptyDockingPoints"), rs.getFloat("distance"),
                        rs.getFloat("walkingTime"), rs.getString("imagePath"));
                docks.add(dock);
            }
        } catch(Exception se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return docks;
    }

    @Override
    public Dock get(int id) {
        String q = "SELECT * FROM dock WHERE dockId = " + id;
        List<Dock> docks = getByQuery(q);
        if (docks.size() != 1)
            return null;
        return docks.get(0);
    }


    @Override
    public List<Dock> getAll() {
        String q = "SELECT * FROM dock" ;
        return this.getByQuery(q);
    }

    /**
     * Search a list of docks in the database by searched option and searched information
     * @param searchOption select from 2 options {0, 1}, 0 is option "dockName" and 1 is option "address"
     * @param info the information needs to search
     * @return list of docks
     */
    public List<Dock> searchDock(int searchOption, String info){
        String type;
        if (searchOption == 0) {
            type = "dockName";
        } else {
            type = "address";
        }
        if (info.equals(""))
            return new ArrayList<Dock>();
        String q = String.format("SELECT * FROM dock WHERE %s LIKE \"%s%s%s\"", type, "%", info, "%");
        return this.getByQuery(q);
    }

    @Override
    public void update(Dock dock) {
        String q = "UPDATE dock SET " +
                " dockName = " + "\"" +  dock.getDockName() + "\"" +
                " , address = " + "\"" +  dock.getAddress() + "\"" +
                " , dockArea = " + dock.getDockArea() +
                " , availableBikes = " +  dock.getAvailableBikes() +
                " , emptyDockingPoints = " + dock.getEmptyDockingPoints() +
                " , distance = " + dock.getDistance() +
                " , walkingTime = " + dock.getWalkingTime() +
                " , imagePath = " + "\"" +  dock.getImagePath() + "\"" +
                " WHERE dockId = " + dock.getDockId();
        System.out.println(q);
        try{
            executeUpdate(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public void save(Dock dock) {
        String q = "insert into bike_rental.dock(dockName, address, dockArea, availableBikes, emptyDockingPoints, distance, walkingTime, imagePath)\n" +
                "values (" +
                "\"" + dock.getDockName() + "\", " +
                "\"" + dock.getAddress() + "\", " +
                dock.getDockArea() + ", " +
                dock.getAvailableBikes() + ", " +
                dock.getEmptyDockingPoints() + ", " +
                dock.getDistance() + ", " +
                dock.getWalkingTime() + ", " +
                "\"" + dock.getImagePath() + "\"" +
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
    public void delete(int dockId) {
        String q = "DELETE FROM dock WHERE dockId = " + dockId;
        System.out.println(q);
        try{
            execute(q);
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
