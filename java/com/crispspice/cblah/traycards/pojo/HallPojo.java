package com.crispspice.cblah.traycards.pojo;

import org.parceler.Parcel;

/**
 * This is a POJO for the hall part of the database. it has a constructor and getters and setters for each instance variable
 * Created by cblah on 2/22/2018.
 */
@Parcel
public class HallPojo {

    private String hall;
    private int numRoomsFilled;
    private int hallStart;
    private int hallEnd;
    private int numRegulars;
    private int numMechSoft;
    private int numPuree;
    private int numDiab;
    private int numHeartHealthy;
    private int numRenal;
    private int numNectar;
    private int numHoney;
    private String facilityKey;

    public HallPojo() {
    }

    public HallPojo(String hall,
                    int numRoomsFilled,
                    int hallStart,
                    int hallEnd,
                    int numRegulars,
                    int numMechSoft,
                    int numPuree,
                    int numDiab,
                    int numHeartHealthy,
                    int numRenal,
                    int numNectar,
                    int numHoney,
                    String hallKey) {
        this.hall = hall;
        this.numRoomsFilled = numRoomsFilled;
        this.hallStart = hallStart;
        this.hallEnd = hallEnd;
        this.numRegulars = numRegulars;
        this.numMechSoft = numMechSoft;
        this.numPuree = numPuree;
        this.numDiab = numDiab;
        this.numHeartHealthy = numHeartHealthy;
        this.numRenal = numRenal;
        this.numNectar = numNectar;
        this.numHoney = numHoney;
        this.facilityKey = hallKey;
    }

    public String getFacilityKey() {
        return facilityKey;
    }

    public void setFacilityKey(String facilityKey) {
        this.facilityKey = facilityKey;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public int getNumRoomsFilled() {
        return numRoomsFilled;
    }

    public void setNumRoomsFilled(int numRoomsFilled) {
        this.numRoomsFilled = numRoomsFilled;
    }

    public int getNumRegulars() {
        return numRegulars;
    }

    public void setNumRegulars(int numRegulars) {
        this.numRegulars = numRegulars;
    }

    public int getNumMechSoft() {
        return numMechSoft;
    }

    public void setNumMechSoft(int numMechSoft) {
        this.numMechSoft = numMechSoft;
    }

    public int getNumPuree() {
        return numPuree;
    }

    public void setNumPuree(int numPuree) {
        this.numPuree = numPuree;
    }

    public int getNumDiab() {
        return numDiab;
    }

    public void setNumDiab(int numDiab) {
        this.numDiab = numDiab;
    }

    public int getNumHeartHealthy() {
        return numHeartHealthy;
    }

    public void setNumHeartHealthy(int numHeartHealthy) {
        this.numHeartHealthy = numHeartHealthy;
    }

    public int getNumRenal() {
        return numRenal;
    }

    public void setNumRenal(int numRenal) {
        this.numRenal = numRenal;
    }

    public int getNumNectar() {
        return numNectar;
    }

    public void setNumNectar(int numNectar) {
        this.numNectar = numNectar;
    }

    public int getNumHoney() {
        return numHoney;
    }

    public void setNumHoney(int numHoney) {
        this.numHoney = numHoney;
    }

    public int getHallStart() {
        return hallStart;
    }

    public void setHallStart(int hallStart) {
        this.hallStart = hallStart;
    }

    public int getHallEnd() {
        return hallEnd;
    }

    public void setHallEnd(int hallEnd) {
        this.hallEnd = hallEnd;
    }
}
