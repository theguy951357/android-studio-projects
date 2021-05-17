package com.crispspice.cblah.traycards.pojo;

import com.crispspice.cblah.traycards.utils.Utils;

import org.parceler.Parcel;

/**
 * This is a plain old java object for the firebase database. it has a basic constructor with getters and setters for each variable.
 * Created by cblah on 2/22/2018.
 */
@Parcel
public class RoomPojo {

    private String roomNumber;
    private String alergy;
    private String plateType;
    private String chemicalDiet;
    private String textureDiet;
    private String liquidDiet;
    private String likes;
    private String dislikes;
    private String notes;
    private String meal;
    private String dessert;
    private String beverage;
    private int roomInt;
    private String facilityKey;
    private String cardColor;
    private boolean delivered;


    public RoomPojo() {
    }



    public RoomPojo(String roomNumber,
                    String alergy,
                    String plateType,
                    String chemicalDiet,
                    String textureDiet,
                    String liquidDiet,
                    String likes,
                    String dislikes,
                    String notes,
                    String meal,
                    String dessert,
                    String beverage,
                    String facilityKey) {
        this.roomNumber = roomNumber;
        this.alergy = alergy;
        this.plateType = plateType;
        this.chemicalDiet = chemicalDiet;
        this.textureDiet = textureDiet;
        this.liquidDiet = liquidDiet;
        this.likes = likes;
        this.dislikes = dislikes;
        this.notes = notes;
        this.meal = meal;
        this.dessert = dessert;
        this.beverage = beverage;
        this.roomInt = Utils.convertRoomNumberToInt(roomNumber);
        this.facilityKey = facilityKey;
        this.delivered = false;





    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getFacilityKey() {
        return facilityKey;
    }

    public void setFacilityKey(String facilityKey) {
        this.facilityKey = facilityKey;
    }

    public int getRoomInt() {
        return roomInt;
    }

    public void setRoomInt(int roomInt) {
        this.roomInt = roomInt;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAlergy() {
        return alergy;
    }

    public void setAlergy(String alergy) {
        this.alergy = alergy;
    }

    public String getChemicalDiet() {
        return chemicalDiet;
    }

    public void setChemicalDiet(String chemicalDiet) {
        this.chemicalDiet = chemicalDiet;
    }

    public String getTextureDiet() {
        return textureDiet;
    }

    public void setTextureDiet(String textureDiet) {
        this.textureDiet = textureDiet;
    }

    public String getLiquidDiet() {
        return liquidDiet;
    }

    public void setLiquidDiet(String liquidDiet) {
        this.liquidDiet = liquidDiet;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    public String getBeverage() {
        return beverage;
    }

    public void setBeverage(String beverage) {
        this.beverage = beverage;
    }
}
