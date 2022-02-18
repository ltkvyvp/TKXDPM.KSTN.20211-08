package controller;

import accessor.BikeAccessor;
import accessor.DockAccessor;
import entity.Bike;
import entity.Dock;

import java.util.List;

public class DockController extends BaseController{
    /**
     * DockAccessor object for accessing to dock in database
     */
    private DockAccessor dockAccessor;
    /**
     * BikeAccessor object for accessing to bike in database
     */
    private BikeAccessor bikeAccessor;

    public DockController(){
        this.dockAccessor = new DockAccessor();
        this.bikeAccessor = new BikeAccessor();
    }

    /**
     * Get dock from database by its id
     * @param dockId
     * @return Dock
     */
    public Dock getDock(int dockId) {
        return this.dockAccessor.get(dockId);
    }

    /**
     * Get list of bikes in a dock by dock's id
     * @param dockId
     * @return list of bikes
     */
    public List<Bike> getBikeByDockId(int dockId){
        return this.bikeAccessor.getBikeByDockId(dockId);
    }

    /**
     * Search a list of bikes in a dock by searched option and searched information
     * @param option: select from 4 options {Barcode, BikeName, Status, Category}
     * @param info: the information needs to search
     * @param dockId: the dock's id
     * @return list of bikes
     */
    public List<Bike> searchBike(String option, String info, int dockId){
        return this.bikeAccessor.searchBike(option, info, dockId);
    }
}