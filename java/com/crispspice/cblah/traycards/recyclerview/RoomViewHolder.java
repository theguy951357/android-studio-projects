package com.crispspice.cblah.traycards.recyclerview;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.clickListener.ItemClickListener;

/**
 * This is a viewholder for the roomRecyclerView. its a simple POJO with a constructor.
 */
public class RoomViewHolder extends RecyclerView.ViewHolder {

    public final String TAG = RoomViewHolder.class.getSimpleName();

    public final TextView mRoomNumber;
    public final TextView mAllergy;
    public final TextView mPlateType;
    public final TextView mChemicalDiet;
    public final TextView mTextureDiet;
    public final TextView mLiquidDiet;
    public final TextView mLikes;
    public final TextView mDislikes;
    public final TextView mNotes;
    public final TextView mMeal;
    public final TextView mDessert;
    public final TextView mBeverage;
    public final Button mMealButton;
    public final Button mEditButton;
    public final Button mDeleteButton;
    public final ConstraintLayout mConstraintLayout;
    public final CardView mCardView;

    private ItemClickListener mItemClickListener;


    public RoomViewHolder(View itemView) {
        super(itemView);
        mRoomNumber = (TextView) itemView.findViewById(R.id.room_number_text_view);
        mAllergy = (TextView) itemView.findViewById(R.id.allergy_text_view);
        mPlateType = (TextView) itemView.findViewById(R.id.plate_textview);
        mChemicalDiet = (TextView) itemView.findViewById(R.id.chemical_diet_textview);
        mTextureDiet = (TextView) itemView.findViewById(R.id.texture_diet_textview);
        mLiquidDiet = (TextView) itemView.findViewById(R.id.liquid_diet_texview);
        mLikes = (TextView) itemView.findViewById(R.id.likes_textview);
        mDislikes = (TextView) itemView.findViewById(R.id.dislikes_textview);
        mNotes = (TextView) itemView.findViewById(R.id.notes_textview);
        mMeal = (TextView) itemView.findViewById(R.id.meal_textview);
        mDessert = (TextView) itemView.findViewById(R.id.dessert_textview);
        mBeverage = (TextView) itemView.findViewById(R.id.beverage_textview);
        mMealButton = (Button) itemView.findViewById(R.id.select_meal_button);
        mEditButton = (Button) itemView.findViewById(R.id.edit_button);
        mDeleteButton = (Button) itemView.findViewById(R.id.delete_button);
        mConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.room_constraint);
        mCardView = (CardView) itemView.findViewById(R.id.cardview);

    }


}
