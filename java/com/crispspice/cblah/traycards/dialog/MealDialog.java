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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * This will handle adding a meal choice to the tray card
 * Created by cblah on 4/4/2018.
 */

public class MealDialog extends DialogFragment {

    EditText mMeal;
    EditText mDessert;
    EditText mBeverage;

    public static String gMeal;
    public static String gDessert;
    public static String gBeverage;
    //    firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    public static String mKey;

    public static MealDialog NewInstance(String key, String meal, String dessert, String beverage) {

        MealDialog mealDialog = new MealDialog();
        Bundle bundle = new Bundle();
        mealDialog.setArguments(bundle);
        mKey = key;
        gMeal = meal;
        gDessert = dessert;
        gBeverage = beverage;
        return mealDialog;
    }


//    initialize instance variable with data from bundle.

    /**
     * This will get the instance of the firebase database
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference().child("room").child(mKey);
        super.onCreate(savedInstanceState);
    }

    //open the keyboard automatically when the dialog is opened

    /**
     * This will get a keyboard for the window
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    /**
     * This will inflate a dialogue fragment and fill it with the lables and edittext fields
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_meal, null);
        mMeal = rootView.findViewById(R.id.mealEditText);
        mDessert = rootView.findViewById(R.id.dessertEditText);
        mBeverage = rootView.findViewById(R.id.beverageEditText);
        mMeal.setText(gMeal);
        mDessert.setText(gDessert);
        mBeverage.setText(gBeverage);

        mBeverage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    addMeal();
                }
                return true;
            }
        });

        //inflate and set the layout for the dialog
        //pass null as the parent view cause its going in the dialog layout.
        builder.setView(rootView).setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addMeal();
            }
        });

        return builder.create();
    }

    /**
     * This will push a new meal choice to the database
     */
    public void addMeal(){

        String meal = mMeal.getText().toString();
        String dessert = mDessert.getText().toString();
        String beverage = mBeverage.getText().toString();
        HashMap<String, Object> mealUpdate = new HashMap<>();
        mealUpdate.put("meal", meal);
        mealUpdate.put("dessert", dessert);
        mealUpdate.put("beverage", beverage);
        mFirebaseDatabaseReference.updateChildren(mealUpdate);
        MealDialog.this.getDialog().cancel();


    }


}
