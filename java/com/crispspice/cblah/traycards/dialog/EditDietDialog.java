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
import com.crispspice.cblah.traycards.utils.Constants;
import com.crispspice.cblah.traycards.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by cblah on 4/5/2018.
 * This is a dialog fragment that handles the edit functionality for the firebase database
 */

public class EditDietDialog extends DialogFragment {

    private static final String TAG = EditDietDialog.class.getSimpleName();

    EditText mEditRoom;
    EditText mEditAllergy;
    EditText mEditPlateType;
    EditText mEditChemical;
    EditText mEditTexture;
    EditText mEditLiquid;
    EditText mEditLikes;
    EditText mEditDislikes;
    EditText mEditNotes;

    public static String mRoom;
    public static String mAllergy;
    public static String mPlateType;
    public static String mChemical;
    public static String mTexture;
    public static String mLiquid;
    public static String mLikes;
    public static String mDislikes;
    public static String mNotes;
    //    firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    public static String mKey;

    public static EditDietDialog NewInstance(String key, String room, String allergy, String plateType, String chemical, String texture, String liquid, String likes, String dislikes, String notes){
        EditDietDialog editDietDialog = new EditDietDialog();
        Bundle bundle = new Bundle();
        editDietDialog.setArguments(bundle);
        mKey = key;
        mRoom = room;
        mAllergy = allergy;
        mPlateType = plateType;
        mChemical = chemical;
        mTexture = texture;
        mLiquid = liquid;
        mLikes = likes;
        mDislikes = dislikes;
        mNotes = notes;
        return editDietDialog;
    }

    /**
     * onCreate will get the instance of the database item that needs to be edited.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child(Constants.ROOM_DATABASE_REFERENCE).child(mKey);
        super.onCreate(savedInstanceState);
    }

    /**
     * This will create the dialogue window for the edit fragment
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * This will inflate the dialogue and populate it with the info from the database query.
     * This method will also set a click listener for the button that will edit the database.
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_diet_edit, null);
        mEditRoom = rootView.findViewById(R.id.roomNumberEditText);
        mEditAllergy = rootView.findViewById(R.id.allergyEditText);
        mEditPlateType = rootView.findViewById(R.id.plateTypeEditText);
        mEditChemical = rootView.findViewById(R.id.chemicalDietEditText);
        mEditTexture = rootView.findViewById(R.id.textureDietEditText);
        mEditLiquid = rootView.findViewById(R.id.liquidDietEditText);
        mEditLikes = rootView.findViewById(R.id.likesEditText);
        mEditDislikes = rootView.findViewById(R.id.dislikesEditText);
        mEditNotes = rootView.findViewById(R.id.notesEditText);
        mEditRoom.setText(mRoom);
        mEditAllergy.setText(mAllergy);
        mEditPlateType.setText(mPlateType);
        mEditChemical.setText(mChemical);
        mEditTexture.setText(mTexture);
        mEditLiquid.setText(mLiquid);
        mEditLikes.setText(mLikes);
        mEditDislikes.setText(mDislikes);
        mEditNotes.setText(mNotes);


        mEditNotes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        builder.setView(rootView).setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editCard();
            }
        });

        return builder.create();
    }

    /**
     * editCard will update the info passed to the database
     */
    public void editCard(){
        String room = mEditRoom.getText().toString();
        String alergy = mEditAllergy.getText().toString();
        String plateType = mEditPlateType.getText().toString();
        String chemical = mEditChemical.getText().toString();
        String texture = mEditTexture.getText().toString();
        String liquid = mEditLiquid.getText().toString();
        String likes = mEditLikes.getText().toString();
        String dislikes = mEditDislikes.getText().toString();
        String notes = mEditNotes.getText().toString();
        HashMap<String, Object> updateCard = new HashMap<>();
        updateCard.put("roomNumber", room);
        updateCard.put("alergy", alergy);
        updateCard.put("plateType", plateType);
        updateCard.put("chemicalDiet", chemical);
        updateCard.put("textureDiet", texture);
        updateCard.put("liquidDiet", liquid);
        updateCard.put("likes", likes);
        updateCard.put("dislikes", dislikes);
        updateCard.put("notes", notes);
        updateCard.put("roomInt", Utils.convertRoomNumberToInt(room));
        mFirebaseDatabaseReference.updateChildren(updateCard);
        EditDietDialog.this.getDialog().cancel();
    }

}
