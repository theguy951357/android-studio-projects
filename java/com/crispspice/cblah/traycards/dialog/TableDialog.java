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
import com.crispspice.cblah.traycards.utils.Constants;
import com.crispspice.cblah.traycards.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Table dialogue will move a tray card to the dining room and set it to a table
 * Created by cblah on 4/27/2018.
 */

public class TableDialog extends DialogFragment {

    private static final String TAG = TableDialog.class.getSimpleName();

    private TextView mMoveTableTextview;
    private EditText mTableMoveEdittext;

    public static int mRoomInt;
    public static String mRoomNumber;
    public static String mKey;

    //    firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;


    public static TableDialog NewInsance(String key, String roomNumber, int roomInt){
        TableDialog tableDialog = new TableDialog();
        Bundle bundle = new Bundle();
        tableDialog.setArguments(bundle);
        mKey = key;
        mRoomInt = roomInt;
        mRoomNumber = roomNumber;
        return tableDialog;
    }

    /**
     * Gets an instance of the firebase database
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child(Constants.ROOM_DATABASE_REFERENCE).child(mKey);
        super.onCreate(savedInstanceState);
    }

    /**
     * Gets a keyboard for the dialogue fragment
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * inflates the dialogue fragment and fills it with the lables and edittext fields
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_table, null);
        mMoveTableTextview= rootView.findViewById(R.id.move_table_textview);
        mTableMoveEdittext = rootView.findViewById(R.id.tableMoveEditText);
        if (mRoomInt <= Utils.convertRoomNumberToInt("100a")){
            mMoveTableTextview.setText(R.string.move_to_room);
            mTableMoveEdittext.setVisibility(View.INVISIBLE);
        }else{
            mMoveTableTextview.setText(R.string.move_to_table);
            mTableMoveEdittext.setVisibility(View.VISIBLE);
        }



        mTableMoveEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    editCard();
                }
                return true;
            }
        });

        //inflate and set the layout for the dialog
        //pass null as the parent view cause its going in the dialog layout.
        builder.setView(rootView).setPositiveButton(R.string.button_move, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editCard();
            }
        });

        return builder.create();
    }

    /**
     * editCard will reassign the traycard to a table number
     */
    public void editCard(){
        String tableNumber = mTableMoveEdittext.getText().toString();


        HashMap<String, Object> updateCard = new HashMap<>();
        if (mRoomInt <= Utils.convertRoomNumberToInt("100a")){
            updateCard.put("roomInt", Utils.convertRoomNumberToInt(mRoomNumber));
        }else{
            updateCard.put("roomInt", Utils.convertHallStart(Integer.parseInt(tableNumber)));
        }

        mFirebaseDatabaseReference.updateChildren(updateCard);
        Utils.countNumRoomsFilled(RoomActivity.mHallKey, RoomActivity.mHallStart, RoomActivity.mHallEnd);
        TableDialog.this.getDialog().cancel();
    }
}
