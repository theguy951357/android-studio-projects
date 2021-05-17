package com.crispspice.cblah.traycards.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * this is a utilities class that will handle different methods that will be used throughout the entire app
 * Created by cblah on 4/6/2018.
 */

public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    public static FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();


    /**
     * This method takes either bed 'a' or bed 'b' in a given room and converts it to an int to make it countable with the room number
     * @param roomNumber
     * @return
     */
    public static int convertRoomNumberToInt(String roomNumber){

        if (roomNumber.contains("a")) {
            return Integer.parseInt(roomNumber.replace("a", "1"));

        }

        return Integer.parseInt(roomNumber.replace("b", "2"));


    }

    public static int convertHallStart(int hallStart){
        return hallStart * 10 + 1;
    }

    public static int convertHallEnd(int hallEnd){
        return hallEnd * 10 + 2;
    }

    /**
     * Counts the number of regular diets for census purposes in the facility
     */
    public static void countNumberRegulars(){
        int count = 0;
        DatabaseReference hallDatabaseReference = mFirebaseDatabase.getReference().child(Constants.HALL_DATABASE_REFERENCE);
        DatabaseReference roomDatabaseReference = mFirebaseDatabase.getReference().child(Constants.ROOM_DATABASE_REFERENCE);
        //TODO finish this method.
    }

    /**
     * This counts the number of rooms filled within the facility
     * @param key
     * @param hallStart
     * @param hallEnd
     */
    public static void countNumRoomsFilled(String key, int hallStart, int hallEnd){
        //TODO finish this method. its not entirely working yet.

        final DatabaseReference hallReference = mFirebaseDatabase.getReference().child(Constants.HALL_DATABASE_REFERENCE).child(key).child(Constants.NUM_ROOMS_FILLED_REFERENCE);
        DatabaseReference roomReference =  mFirebaseDatabase.getReference().child(Constants.ROOM_DATABASE_REFERENCE);
        Query query = roomReference.startAt(convertHallStart(hallStart)).endAt(convertHallEnd(hallEnd));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hallReference.setValue(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * This counts the census for the facility
     * @param key
     */
    public static void countCensus(String key){




        DatabaseReference roomReference = mFirebaseDatabase.getReference().child(Constants.ROOM_DATABASE_REFERENCE);
        final DatabaseReference facilityReference = mFirebaseDatabase.getReference().child(Constants.FACILITY_DATABASE_REFERENCE).child(key).child(Constants.FACILITY_CENSUS_REFERENCE);
        roomReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    facilityReference.setValue(dataSnapshot.getChildrenCount());




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
