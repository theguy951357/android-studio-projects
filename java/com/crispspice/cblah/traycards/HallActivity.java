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

import com.crispspice.cblah.traycards.clickListener.ItemClickListener;
import com.crispspice.cblah.traycards.dialog.HallDialog;
import com.crispspice.cblah.traycards.pojo.HallPojo;
import com.crispspice.cblah.traycards.recyclerview.HallRecyclerviewAdapter;
import com.crispspice.cblah.traycards.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

public class HallActivity extends AppCompatActivity implements ItemClickListener {

    private final String TAG = HallActivity.class.getSimpleName();
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    public final static String EXTRA_HALL_NAME = "hallName";
    public final static String EXTRA_FACILITY_KEY = "facilityKey";
    public final static String EXTRA_HALL_START = "hallStart";
    public final static String EXTRA_HALL_END = "hallEnd";
    public final static String EXTRA_HALL_KEY = "hallKey";

    private Query mQuery;
    private HallRecyclerviewAdapter mHallRecyclerviewAdapter;
    private ArrayList<HallPojo> mAdapterItems;
    private ArrayList<String> mAdapterKeys;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mRecyclerview;
    public static String mFacilityKey;


    /**
     * main method for HallActivity
     * this will handle the setup for the activity when created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String facilityName = intent.getStringExtra(MainActivity.EXTRA_FACILITY);
        mFacilityKey = intent.getStringExtra(MainActivity.EXTRA_KEY);
        setTitle(facilityName);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = HallDialog.NewInstance();
                dialog.show(HallActivity.this.getFragmentManager(), "HallDialog");

            }
        });

        handleInstanceState(savedInstanceState);
        setupFirebase();
        setUpRecyclerview();
    }

    /**
     * This will handle the instace state of the activity
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
            mAdapterItems = new ArrayList<HallPojo>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    /**
     * This will setup the instance of the firebase database for the HallActivity
     */
    private void setupFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child(Constants.HALL_DATABASE_REFERENCE);
        mQuery = mFirebaseDatabaseReference.orderByChild(Constants.HALL_START_REFERENCE);


    }

    /**
     * This sets up the recyclerview with the hall adapter and animations.
     */
    private void setUpRecyclerview(){
        mRecyclerview = (RecyclerView) findViewById(R.id.hallRecView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mHallRecyclerviewAdapter = new HallRecyclerviewAdapter(mQuery,mAdapterItems,mAdapterKeys);
        mRecyclerview.setAdapter(mHallRecyclerviewAdapter);
        mHallRecyclerviewAdapter.setOnItemClickListener(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerview.setItemAnimator(itemAnimator);
    }

    // Saving the list of items and keys of the items on rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mHallRecyclerviewAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mHallRecyclerviewAdapter.getKeys());
    }

    /**
     * this cleans up after the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHallRecyclerviewAdapter.destroy();
    }

    /**
     * Handles the onPause state
     */
    @Override
    protected void onPause() {
        super.onPause();
        mHallRecyclerviewAdapter.destroy();
    }

    /**
     * this handles the explicit intent into the room activity. it will pass the necessary info for the room activity to display the right info about the room chosen.
     * @param view
     * @param position
     */
    public void goToRoomActivity(View view, int position) {
        mHallRecyclerviewAdapter.destroy();
        Intent intent = new Intent(HallActivity.this, RoomActivity.class);
        intent.putExtra(EXTRA_HALL_NAME, mAdapterItems.get(position).getHall());
        intent.putExtra(EXTRA_FACILITY_KEY, mFacilityKey);
        intent.putExtra(EXTRA_HALL_START, mAdapterItems.get(position).getHallStart()+"");
        intent.putExtra(EXTRA_HALL_END, mAdapterItems.get(position).getHallEnd()+"");
        intent.putExtra(EXTRA_HALL_KEY, mAdapterKeys.get(position));
        startActivity(intent);

    }

    /**
     * Handles what to do when the click listener gets a click
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        if (mAdapterItems.get(position)!= null){
            goToRoomActivity(view, position);
        };

    }
}
