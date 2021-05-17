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
import com.crispspice.cblah.traycards.pojo.FacilityPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by cblah on 4/2/2018.
 * This dialogue fragment will handle creating a new facility in the application
 */

public class FacilityDialog extends DialogFragment {

    EditText mFacilityNameEditText;
    EditText mFacilityAddressEditText;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;

    public static FacilityDialog NewInstance(){
        FacilityDialog facilityDialog = new FacilityDialog();
        Bundle bundle = new Bundle();
        facilityDialog.setArguments(bundle);
        return facilityDialog;
    }

    /**
     * onCreate will set up an instance of a firebase database and handle the usual oncreate funcionality
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("facility");
        super.onCreate(savedInstanceState);
    }

    /**
     * onActivityCreated will get a window
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * This will inflate a layout for the dialogue window and populate it with the info and editable text fields.
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_facility, null);
        mFacilityNameEditText = rootView.findViewById(R.id.facilityEditText);
        mFacilityAddressEditText = rootView.findViewById(R.id.facilityAddressEditText);


        mFacilityAddressEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    addFacility();
                }
                return true;
            }
        });

        builder.setView(rootView).setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addFacility();
            }
        });

        return builder.create();
    }

    /**
     * addFacility will push a new facility to the firebase database
     */
    private void addFacility(){
        String enteredFacilityName = mFacilityNameEditText.getText().toString();
        if (!enteredFacilityName.equals("")){
            FacilityPojo facilityPojo = new FacilityPojo(enteredFacilityName,
                    mFacilityAddressEditText.getText().toString(),
                    0);
            mFirebaseDatabaseReference.push().setValue(facilityPojo);
        }
        FacilityDialog.this.getDialog().cancel();
    }
}
