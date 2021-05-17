package com.crispspice.cblah.traycards.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.clickListener.ItemClickListener;

/**
 * this is a simple POJO for the view holder for the Facility recyclerview
 * Created by cblah on 4/2/2018.
 */

public class FacilityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView mFacilityNameTv;
    public final TextView mFacilityAddress;
    public final TextView mFacilityCensusTv;

    private ItemClickListener mItemClickListener;

    public FacilityViewHolder(View itemView, ItemClickListener listener) {
        super(itemView);
        mFacilityNameTv =(TextView) itemView.findViewById(R.id.facility_name_textview);
        mFacilityAddress = (TextView) itemView.findViewById(R.id.facility_address_textview);
        mFacilityCensusTv = (TextView) itemView.findViewById(R.id.facility_census_textview);

        this.mItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null){
            mItemClickListener.onItemClick(view, getLayoutPosition());
        }

    }
}
