package com.crispspice.cblah.traycards.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.clickListener.ItemClickListener;
import com.crispspice.cblah.traycards.pojo.FacilityPojo;
import com.crispspice.cblah.traycards.utils.Utils;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * this is the recyclerview adapter for the facility activity
 * Created by cblah on 4/2/2018.
 */

public class FacilityRecyclerviewAdapter extends RoomFirebaseAdapter<FacilityViewHolder, FacilityPojo> {

    private ItemClickListener mItemClickListener;


    public FacilityRecyclerviewAdapter(Query query, @Nullable ArrayList<FacilityPojo> items, @Nullable ArrayList<String> keys) {
        super(query, items, keys);
    }

    /**
     * inflates the layout for the recyclerview list item
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public FacilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.facility_recview_holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new FacilityViewHolder(view, mItemClickListener);
    }

    /**
     * Binds the variables from the viewholder to the adapter
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(FacilityViewHolder holder, int position) {
        FacilityPojo facilityPojo = getItem(position);
        holder.mFacilityNameTv.setText(facilityPojo.getFacility());
        holder.mFacilityAddress.setText(facilityPojo.getAddress());
        holder.mFacilityCensusTv.setText(String.valueOf(facilityPojo.getCensus()));
        Utils.countCensus(getKeys().get(holder.getAdapterPosition()));

    }

    @Override protected void itemAdded(FacilityPojo item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter.");
    }

    @Override protected void itemChanged(FacilityPojo oldItem, FacilityPojo newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item.");
    }

    @Override protected void itemRemoved(FacilityPojo item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter.");
    }

    @Override protected void itemMoved(FacilityPojo item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }

    public void setOnItemClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }
}
