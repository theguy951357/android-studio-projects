package com.crispspice.cblah.traycards.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.clickListener.ItemClickListener;

/**
 * This is a simple POJO for the viewholder for the hall recyclerview
 * Created by cblah on 3/31/2018.
 */

public class HallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView mHallNameTv;
//    public final TextView mNumRoomsTv;
//    public final TextView mNumRegsTv;
//    public final TextView mNumMechsTv;
//    public final TextView mNumPurTv;
//    public final TextView mNumDiabeticTv;
//    public final TextView mNumHeartTv;
//    public final TextView mNumRenalTv;
//    public final TextView mNumNectarTv;
//    public final TextView mNumHoneyTv;

    private ItemClickListener mListener;


    public HallViewHolder(View itemView, ItemClickListener listener) {
        super(itemView);
        mHallNameTv = (TextView) itemView.findViewById(R.id.hall_name_textview);
//        mNumRoomsTv = (TextView) itemView.findViewById(R.id.rooms_filled_textview);
//        mNumRegsTv = (TextView) itemView.findViewById(R.id.num_reg_textview);
//        mNumMechsTv = (TextView) itemView.findViewById(R.id.num_mechsoft_textview);
//        mNumPurTv = (TextView) itemView.findViewById(R.id.num_puree_textview);
//        mNumDiabeticTv = (TextView) itemView.findViewById(R.id.num_diabetic_textview);
//        mNumHeartTv = (TextView) itemView.findViewById(R.id.num_heart_healthy_textview);
//        mNumRenalTv = (TextView) itemView.findViewById(R.id.num_renal_textview);
//        mNumNectarTv = (TextView) itemView.findViewById(R.id.num_nectar_textview);
//        mNumHoneyTv = (TextView) itemView.findViewById(R.id.num_honey_textview);
        this.mListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(mListener != null){
            mListener.onItemClick(v,getLayoutPosition());
        }
    }

}
