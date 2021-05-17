package com.crispspice.cblah.traycards;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crispspice.cblah.traycards.dialog.RoomDialog;
import com.crispspice.cblah.traycards.pojo.RoomPojo;
import com.crispspice.cblah.traycards.recyclerview.RoomRecyclerview;
import com.crispspice.cblah.traycards.utils.Constants;
import com.crispspice.cblah.traycards.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Class for the room activity of the app
 */
public class RoomActivity extends AppCompatActivity{

    private final String TAG = RoomActivity.class.getSimpleName();
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private RoomRecyclerview mRoomRecyclerview;
    private ArrayList<RoomPojo> mAdapterItems;
    private ArrayList<String> mAdapterKeys;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mRecyclerview;
    public static String mFacilityKey;
    public static String mHallKey;
    public static int mHallStart;
    public static int mHallEnd;


    /**
     * onCreate will set up the display on the device
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mFacilityKey = intent.getStringExtra(HallActivity.EXTRA_FACILITY_KEY);
        mHallStart = Integer.parseInt(intent.getStringExtra(HallActivity.EXTRA_HALL_START));
        mHallEnd = Integer.parseInt(intent.getStringExtra(HallActivity.EXTRA_HALL_END));
        mHallKey = intent.getStringExtra(HallActivity.EXTRA_HALL_KEY);
        setTitle(intent.getStringExtra(HallActivity.EXTRA_HALL_NAME));

        //getWindow().setBackgroundDrawable(null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dialog = RoomDialog.NewInstance();
                dialog.show(RoomActivity.this.getFragmentManager(), "RoomDialog");
            }
        });

        handleInstanceState(savedInstanceState);
        setupFirebase();
        setUpRecyclerview();
    }

    /**
     * Handles the onPause state of the device
     */
    @Override
    protected void onPause() {
        super.onPause();



    }


    /**
     * Handles the instance state
     * @param savedInstanceState
     */
    // Restoring the item list and the keys of the items: they will be passed to the adapter
    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mAdapterItems = new ArrayList<RoomPojo>();
            mAdapterKeys = new ArrayList<String>();
        }


    }

    /**
     * This sets up an instance of the firebase database listener
     */
    private void setupFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child(Constants.ROOM_DATABASE_REFERENCE);
        mQuery = mFirebaseDatabaseReference.orderByChild(Constants.ROOM_INT_REFERENCE).startAt(Utils.convertHallStart(mHallStart)).endAt(Utils.convertHallEnd(mHallEnd));
    }

    /**
     * This sets up the recyclerview with the adapter for the room activity and sets the layout and animations
     */
    private void setUpRecyclerview(){
        mRecyclerview = (RecyclerView) findViewById(R.id.roomRecView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRoomRecyclerview = new RoomRecyclerview(this,mQuery,mAdapterItems,mAdapterKeys);
        mRecyclerview.setAdapter(mRoomRecyclerview);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        itemAnimator.setChangeDuration(1000);
        itemAnimator.setMoveDuration(1000);
        mRecyclerview.setItemAnimator(itemAnimator);

    }


    /**
     * This handles what gets saved through the different states of the device.
     * @param outState
     */
    // Saving the list of items and keys of the items on rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mRoomRecyclerview.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mRoomRecyclerview.getKeys());
    }

    /**
     * Cleans up when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRoomRecyclerview.destroy();
    }



}
