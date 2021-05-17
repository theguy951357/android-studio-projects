package com.crispspice.cblah.traycards.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.RoomActivity;
import com.crispspice.cblah.traycards.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This will handle deleting a traycard from the database
 * Created by cblah on 4/5/2018.
 */

public class RemoveCardDialog extends DialogFragment {

    TextView mRemoveTextview;
    public static String mRoom;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    public static String mKey;

    public static RemoveCardDialog NewInstance(String key, String room){

        RemoveCardDialog removeCardDialog = new RemoveCardDialog();
        Bundle bundle = new Bundle();
        removeCardDialog.setArguments(bundle);
        mKey = key;
        mRoom = room;
        return removeCardDialog;
    }

    /**
     * gets an instance to the database
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("room").child(mKey);
        super.onCreate(savedInstanceState);
    }

    /**
     * gets a keyboard
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * inflates the layout and adds a message asking if user is sure about removing the tray card
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_remove_card, null);
        mRemoveTextview = rootView.findViewById(R.id.delete_textview);
        String removeText = getString(R.string.remove_confirmation) + mRoom;
        mRemoveTextview.setText(removeText);



        //inflate and set the layout for the dialog
        //pass null as the parent view cause its going in the dialog layout.
        builder.setView(rootView).setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeCard();
            }
        });

        return builder.create();
    }

    /**
     * this will handle deleting the tray card from the database
     */
    public void removeCard(){
        mFirebaseDatabaseReference.removeValue();
        Utils.countCensus(RoomActivity.mFacilityKey);
        Utils.countNumRoomsFilled(RoomActivity.mHallKey, RoomActivity.mHallStart, RoomActivity.mHallEnd);
        RemoveCardDialog.this.getDialog().cancel();
    }
}
