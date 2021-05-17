package com.crispspice.cblah.traycards.pojo;

import org.parceler.Parcel;

/**
 * This is a pojo for the Facility part of the database. it has a constructor and a getter and setter for each instance variable.
 * Created by cblah on 2/22/2018.
 */
@Parcel
public class FacilityPojo {

    private String facility;
    private String address;
    private int census;

    public FacilityPojo() {
    }

    public FacilityPojo(String facility, String address, int census) {
        this.facility = facility;
        this.address = address;
        this.census = census;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCensus() {
        return census;
    }

    public void setCensus(int census) {
        this.census = census;
    }
}
