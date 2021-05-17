package com.crispspice.cblah.traycards.recyclerview;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.dialog.EditDietDialog;
import com.crispspice.cblah.traycards.dialog.MealDialog;
import com.crispspice.cblah.traycards.dialog.RemoveCardDialog;
import com.crispspice.cblah.traycards.dialog.TableDialog;
import com.crispspice.cblah.traycards.pojo.RoomPojo;
import com.crispspice.cblah.traycards.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * This is a recyclerview adapter for the Room activity.
 * Created by cblah on 2/22/2018.
 */

public class RoomRecyclerview extends RoomFirebaseAdapter<RoomViewHolder, RoomPojo> {

    private static final String TAG = RoomRecyclerview.class.getSimpleName();

    private Context context;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference mRoomDatabaseReference;
    public String mKey ;







    public RoomRecyclerview(Context context, Query query, @Nullable ArrayList<RoomPojo> items, @Nullable ArrayList<String> keys) {
        super(query, items, keys);
        this.context = context;
    }


    /**
     * this will inflate the viewholder for the list item
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
        Context context = parent.getContext();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child(Constants.ROOM_DATABASE_REFERENCE);
        int layoutIdForListItem = R.layout.tray_cards;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RoomViewHolder(view);
    }

    /**
     * This method will bind the viewholder to the recyclerview and set the click listeners for each view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RoomViewHolder holder, final int position) {
        final RoomPojo roomPojo = getItem(position);
        mKey = getKeys().get(holder.getAdapterPosition());
        holder.mRoomNumber.setText(roomPojo.getRoomNumber());
        holder.mAllergy.setText(roomPojo.getAlergy());
        holder.mPlateType.setText(roomPojo.getPlateType());
        holder.mChemicalDiet.setText(roomPojo.getChemicalDiet());
        holder.mTextureDiet.setText(roomPojo.getTextureDiet());
        holder.mLiquidDiet.setText(roomPojo.getLiquidDiet());
        holder.mLikes.setText(roomPojo.getLikes());
        holder.mDislikes.setText(roomPojo.getDislikes());
        holder.mNotes.setText(roomPojo.getNotes());
        holder.mMeal.setText(roomPojo.getMeal());
        holder.mDessert.setText(roomPojo.getDessert());
        holder.mBeverage.setText(roomPojo.getBeverage());
        if (!roomPojo.isDelivered()){
            holder.mCardView.setCardBackgroundColor(((Activity)context).getResources().getColor(R.color.lime500));
        }else{
            holder.mCardView.setCardBackgroundColor(((Activity)context).getResources().getColor(R.color.grey500));
        }

        holder.mMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = MealDialog.NewInstance(getKeys().get(holder.getAdapterPosition()),
                        roomPojo.getMeal(),
                        roomPojo.getDessert(),
                        roomPojo.getBeverage());
                FragmentManager manager = ((Activity)context ).getFragmentManager();
                dialogFragment.show(manager,"mealDialog");

            }
        });
        holder.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = EditDietDialog.NewInstance(getKeys().get(holder.getAdapterPosition()),
                        roomPojo.getRoomNumber(),
                        roomPojo.getAlergy(),
                        roomPojo.getPlateType(),
                        roomPojo.getChemicalDiet(),
                        roomPojo.getTextureDiet(),
                        roomPojo.getLiquidDiet(),
                        roomPojo.getLikes(),
                        roomPojo.getDislikes(),
                        roomPojo.getNotes());
                FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                dialogFragment.show(fragmentManager, "editDietDialog");
            }
        });
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = RemoveCardDialog.NewInstance(getKeys().get(holder.getAdapterPosition()),roomPojo.getRoomNumber());
                FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                fragment.show(fragmentManager, "removeCardDialog");
            }
        });

        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoomDatabaseReference = mFirebaseDatabaseReference.child(getKeys().get(holder.getAdapterPosition()));
                if (!roomPojo.isDelivered()){
                    Toast.makeText(context, "delivered to " + roomPojo.getRoomNumber(), Toast.LENGTH_LONG).show();
                    mRoomDatabaseReference.child("delivered").setValue(true);
                    holder.mCardView.setCardBackgroundColor(((Activity)context).getResources().getColor(R.color.grey500));
                }else{
                    mRoomDatabaseReference.child("delivered").setValue(false);
                    holder.mCardView.setCardBackgroundColor(((Activity)context).getResources().getColor(R.color.lime500));
                }

            }
        });
        holder.mConstraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogFragment dialogFragment = TableDialog.NewInsance(getKeys().get(holder.getAdapterPosition()), roomPojo.getRoomNumber(), roomPojo.getRoomInt());
                FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                dialogFragment.show(fragmentManager, "tableDialog");
                return true;
            }
        });



    }


    @Override protected void itemAdded(RoomPojo item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter.");
    }

    @Override protected void itemChanged(RoomPojo oldItem, RoomPojo newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item.");
    }

    @Override protected void itemRemoved(RoomPojo item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter.");
    }

    @Override protected void itemMoved(RoomPojo item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }





}
