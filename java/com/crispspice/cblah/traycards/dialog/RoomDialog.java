package com.crispspice.cblah.traycards.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.RoomActivity;
import com.crispspice.cblah.traycards.pojo.RoomPojo;
import com.crispspice.cblah.traycards.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * room dialogue will add a new room to the firebase database
 * Created by cblah on 3/26/2018.
 */

public class RoomDialog extends DialogFragment {


    EditText mRoomNumber;
    EditText mAllergy;
    EditText mPlateType;
    EditText mChemicalDiet;
    EditText mTextureDiet;
    EditText mLiquidDiet;
    EditText mLikes;
    EditText mDislikes;
    EditText mNotes;
    EditText mMeal;
    EditText mDessert;
    EditText mBeverage;
//    firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;

    public static RoomDialog NewInstance() {

        RoomDialog roomDialog = new RoomDialog();
        Bundle bundle = new Bundle();
        roomDialog.setArguments(bundle);
        return roomDialog;
    }


//    initialize instance variable with data from bundle.

    /**
     * This will get the instance to the firebase database
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("room");
        super.onCreate(savedInstanceState);
    }

    //open the keyboard automatically when the dialog is opened

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
     * Inflates a layout for the fragment and fills it with lables and edittextviews
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_room, null);
        mRoomNumber = rootView.findViewById(R.id.roomNumberEditText);
        mAllergy = rootView.findViewById(R.id.allergyEditText);
        mPlateType = rootView.findViewById(R.id.plateTypeEditText);
        mChemicalDiet = rootView.findViewById(R.id.chemicalDietEditText);
        mTextureDiet = rootView.findViewById(R.id.textureDietEditText);
        mLiquidDiet = rootView.findViewById(R.id.liquidDietEditText);
        mLikes = rootView.findViewById(R.id.likesEditText);
        mDislikes = rootView.findViewById(R.id.dislikesEditText);
        mNotes = rootView.findViewById(R.id.notesEditText);
        mMeal = rootView.findViewById(R.id.mealEditText);
        mDessert = rootView.findViewById(R.id.dessertEditText);
        mBeverage = rootView.findViewById(R.id.beverageEditText);

        mBeverage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    addRoom();
                }
                return true;
            }
        });

        //inflate and set the layout for the dialog
        //pass null as the parent view cause its going in the dialog layout.
        builder.setView(rootView).setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addRoom();
            }
        });

        return builder.create();
    }

    /**
     * pushes a new room to the database
     */
    public void addRoom(){
        String enteredRoomNumber = mRoomNumber.getText().toString();
        if (!enteredRoomNumber.equals("")){
            RoomPojo roomPojo = new RoomPojo(enteredRoomNumber,
                    mAllergy.getText().toString(),
                    mPlateType.getText().toString(),
                    mChemicalDiet.getText().toString(),
                    mTextureDiet.getText().toString(),
                    mLiquidDiet.getText().toString(),
                    mLikes.getText().toString(),
                    mDislikes.getText().toString(),
                    mNotes.getText().toString(),
                    mMeal.getText().toString(),
                    mDessert.getText().toString(),
                    mBeverage.getText().toString(),
                    RoomActivity.mFacilityKey);
            mFirebaseDatabaseReference.push().setValue(roomPojo);
            Utils.countCensus(RoomActivity.mFacilityKey);
            Utils.countNumRoomsFilled(RoomActivity.mHallKey, RoomActivity.mHallStart, RoomActivity.mHallEnd);
        }
        //close the dialog fragment
        RoomDialog.this.getDialog().cancel();
    }
}
