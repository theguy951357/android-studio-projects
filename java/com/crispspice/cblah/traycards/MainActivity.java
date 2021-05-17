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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crispspice.cblah.traycards.clickListener.ItemClickListener;
import com.crispspice.cblah.traycards.dialog.FacilityDialog;
import com.crispspice.cblah.traycards.pojo.FacilityPojo;
import com.crispspice.cblah.traycards.recyclerview.FacilityRecyclerviewAdapter;
import com.crispspice.cblah.traycards.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Main activity for tray cards app. this version is basically unusable due to lots of deprecation in the gradle file.
 * firebase also deleted my database for this project becaus I haven't used it in a while and had no read/write permissions for the database.
 * I will eventually just make a new project with the updated gradle auto generated.
 * will keep this for the java files.
 */
public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private final String TAG = "MainActivity";
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    public final static String EXTRA_FACILITY = "mainFacilityName";
    public final static String EXTRA_KEY = "mainFacilityKey";

    private Query mQuery;
    private FacilityRecyclerviewAdapter mFacilityRecyclerviewAdapter;
    private ArrayList<FacilityPojo> mAdapterItems;
    private ArrayList<String> mAdapterKeys;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mRecyclerview;


    //TODO begin on the rest of the counting utility methods for the hall pojo.
    //TODO (maybe)finish CRUD functionality update and delete for hall and facility activities(done for room activity).
    //TODO make the portrait mode in tray_cards layout work. its glitchy, also make it so the viewholder goes back to the top when recycled.

    /**
     * onCreate acts as the main method. it will handle the instance state and set the starting methods for the main activity.
     * this will also instantiate the firebase database.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = FacilityDialog.NewInstance();
                dialogFragment.show(MainActivity.this.getFragmentManager(), "FacilityDialog");
            }
        });

        handleInstanceState(savedInstanceState);
        setupFirebase();
        setUpRecyclerview();

    }

    /**
     * handleInstanceState will handle what gets saved when the activity gets killed and restarted.
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
            mAdapterItems = new ArrayList<FacilityPojo>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    /**
     * this method gets an instance of the firebase database
     */
    private void setupFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child(Constants.FACILITY_DATABASE_REFERENCE);
        mQuery = mFirebaseDatabaseReference;


    }

    /**
     * setUpRecyclerView sets up the recyclerview for the main activity.
     * it finds the id in the xml file, sets the layout and adapter, and sets the click listener for the items on the list.
     * it also sets animators for the list to operate on when adding, moving and deleting list items.
     */
    private void setUpRecyclerview(){
        mRecyclerview = (RecyclerView) findViewById(R.id.facilityRecView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mFacilityRecyclerviewAdapter = new FacilityRecyclerviewAdapter(mQuery,mAdapterItems,mAdapterKeys);
        mRecyclerview.setAdapter(mFacilityRecyclerviewAdapter);
        mFacilityRecyclerviewAdapter.setOnItemClickListener(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        itemAnimator.setMoveDuration(1000);
        itemAnimator.setChangeDuration(1000);
        mRecyclerview.setItemAnimator(itemAnimator);
    }

    /**
     * onSaveInstanceState will handle the list of items and keys of the items on rotation
     * @param outState
     */
    // Saving the list of items and keys of the items on rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mFacilityRecyclerviewAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mFacilityRecyclerviewAdapter.getKeys());
    }

    /**
     * closes the adapter and cleans up when activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFacilityRecyclerviewAdapter.destroy();
    }

    /**
     * handles activity when paused
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * goToHallActivity sets an explicit intent to the HallActivity and carries over any necessary data that
     * the HallActivity will need do display the right info.
     * @param view
     * @param position
     */
    public void goToHallActivity(View view, int position){
        Intent intent = new Intent(MainActivity.this, HallActivity.class);
        intent.putExtra(EXTRA_FACILITY, mAdapterItems.get(position).getFacility());
        intent.putExtra(EXTRA_KEY, mAdapterKeys.get(position));
        startActivity(intent);
    }

    /**
     * Inflates the options menu when the options button is pressed
     * @param menu
     * @return returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This will handle the items selected in the menu.
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item
     * @return returns the item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This handles what happens when an item is clicked
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        if (mAdapterItems.get(position)!= null){
            goToHallActivity(view, position);
        }

    }
}
