package com.crispspice.cblah.traycards.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.clickListener.ItemClickListener;
import com.crispspice.cblah.traycards.pojo.HallPojo;
import com.crispspice.cblah.traycards.utils.Utils;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * This is an adapter for the hall recyclerview
 * Created by cblah on 3/31/2018.
 */

public class HallRecyclerviewAdapter extends RoomFirebaseAdapter<HallViewHolder, HallPojo> {

    public final String TAG = HallRecyclerviewAdapter.class.getSimpleName();

    private ItemClickListener mItemClickListener;

    public HallRecyclerviewAdapter(Query query, @Nullable ArrayList<HallPojo> items, @Nullable ArrayList<String> keys) {
        super(query, items, keys);
    }

    /**
     * This will inflate the viewholder for the recyclerview list item
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public HallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.hall_recview_holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new HallViewHolder(view, mItemClickListener);
    }

    /**
     * This will set the hall name to the view in the list item and count the number of rooms
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HallViewHolder holder, int position) {
        HallPojo hallPojo = getItem(position);
        holder.mHallNameTv.setText(hallPojo.getHall());
        //holder.mNumRoomsTv.setText(hallPojo.getNumRoomsFilled());
        //holder.mNumRegsTv.setText(hallPojo.getNumRegulars());
        //holder.mNumMechsTv.setText(hallPojo.getNumMechSoft());
        //holder.mNumPurTv.setText(hallPojo.getNumPuree());
        //holder.mNumDiabeticTv.setText(hallPojo.getNumDiab());
        //holder.mNumHeartTv.setText(hallPojo.getNumHeartHealthy());
        //holder.mNumRenalTv.setText(hallPojo.getNumRenal());
        //holder.mNumNectarTv.setText(hallPojo.getNumNectar());
        //holder.mNumHoneyTv.setText(hallPojo.getNumHoney());
        Utils.countNumRoomsFilled(getKeys().get(holder.getAdapterPosition()), hallPojo.getHallStart(),hallPojo.getHallEnd());

    }

    @Override protected void itemAdded(HallPojo item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter.");
    }

    @Override protected void itemChanged(HallPojo oldItem, HallPojo newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item.");
    }

    @Override protected void itemRemoved(HallPojo item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter.");
    }

    @Override protected void itemMoved(HallPojo item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }

    public void setOnItemClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }
}
