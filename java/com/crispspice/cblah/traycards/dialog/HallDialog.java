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

import com.crispspice.cblah.traycards.HallActivity;
import com.crispspice.cblah.traycards.R;
import com.crispspice.cblah.traycards.pojo.HallPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This dialogue fragment will handle adding a new hall to the database
 * Created by cblah on 3/30/2018.
 */

public class HallDialog extends DialogFragment {

    EditText mHallNameEditText;
    EditText mHallStartEditText;
    EditText mHallEndEditText;
    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;

    public static HallDialog NewInstance(){
        HallDialog hallDialog = new HallDialog();
        Bundle bundle = new Bundle();
        hallDialog.setArguments(bundle);
        return hallDialog;
    }

    /**
     * gets the instance of the firebase database.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("hall");
        super.onCreate(savedInstanceState);
    }

    /**
     * gets a window for the dialogue fragment.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * inflates a layout in the window and fills it with the lables and edittext fields
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.hall_dialog, null);
        mHallNameEditText = rootView.findViewById(R.id.hallEditText);
        mHallStartEditText = rootView.findViewById(R.id.hallBeginEditText);
        mHallEndEditText = rootView.findViewById(R.id.hallEndEditText);

        mHallEndEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    addHall();
                }
                return true;
            }
        });

        builder.setView(rootView).setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addHall();
            }
        });

        return builder.create();

    }

    /**
     * This will push a new hall to the database
     */
    private void addHall(){
        String enteredHallName = mHallNameEditText.getText().toString();
        String enteredHallStart = mHallStartEditText.getText().toString();
        String enteredHallEnd = mHallEndEditText.getText().toString();
        if (!enteredHallName.equals("")) {
            try {
                int hallStartInt = Integer.parseInt(enteredHallStart);
                int hallEndInt = Integer.parseInt(enteredHallEnd);



                    HallPojo hallPojo = new HallPojo(enteredHallName,
                            0,
                            hallStartInt,
                            hallEndInt,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            HallActivity.mFacilityKey);
                    mFirebaseDatabaseReference.push().setValue(hallPojo);

            }catch (NumberFormatException num) {
                throw new NumberFormatException("Please put a number in the field");
            }
        }

        HallDialog.this.getDialog().cancel();

    }

}
