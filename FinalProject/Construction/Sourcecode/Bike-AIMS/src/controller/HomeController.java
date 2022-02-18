package controller;

import java.sql.SQLException;
import java.util.List;

import accessor.DockAccessor;
import entity.Dock;

/**
 * This class controls the flow of events in homescreen
 * @author nguyenlm
 */
public class HomeController extends BaseController{

    /**
     * DockAccessor object for accessing to dock in database
     */
    private DockAccessor dockAccessor;

    public HomeController(){
        this.dockAccessor = new DockAccessor();
    }

    /**
     * Get all the docks
     * @return list of docks
     * @throws SQLException if accesses to database error
     */
    public List<Dock> getAllDock() throws SQLException{
        return this.dockAccessor.getAll();
    }

    /**
     * Search dock with option and respective information
     * @param searchOption select from 2 options {0, 1}, 0 is option "dockName" and 1 is option "address"
     * @param info the information needs to search
     * @return list of docks
     */
    public List<Dock> searchDock(int searchOption, String info){
        return this.dockAccessor.searchDock(searchOption, info);
    }


}
