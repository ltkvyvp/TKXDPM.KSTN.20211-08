package controller;

import accessor.BikeAccessor;
import accessor.DockAccessor;

public class BikeController extends BaseController{
    /**
     * DockAccessor object for accessing to dock in database
     */
    private DockAccessor dockAccessor;
    /**
     * BikeAccessor object for accessing to bike in database
     */
    private BikeAccessor bikeAccessor;

    public BikeController(){
        this.dockAccessor = new DockAccessor();
        this.bikeAccessor = new BikeAccessor();
    }
}