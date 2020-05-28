package com.assign.tarang.inventory;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home_fragment extends Fragment {

    EditText userName;
    dataEntry_fragment dfrag1;
    DatabaseHelper myDb;
    Button btCheck;
    String uName;

    public Home_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initialize variables
        myDb = new DatabaseHelper(getActivity());
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        v.setBackgroundColor(Color.WHITE);
        userName = (EditText) v.findViewById(R.id.etUsername);
        uName = userName.getText().toString();
        btCheck = (Button) v.findViewById(R.id.btNext);
        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername();
            }
        });
        return v;
    }


    //checking username valid or not
    private void checkUsername() {
        if (userName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Must enter user Name before you can proceed. ", Toast.LENGTH_SHORT).show();
            return;
        } else {

            Bundle args = new Bundle();
            //passing value
            dfrag1 = new dataEntry_fragment();
            args.putString("userName", userName.getText().toString());

            //changing fragment
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            dfrag1.setArguments(args);
            ft.replace(R.id.fgMain, dfrag1);
            //ft.addToBackStack(null);
            ft.commit();
        }
    }

}
