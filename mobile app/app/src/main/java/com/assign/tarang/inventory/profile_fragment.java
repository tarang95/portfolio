package com.assign.tarang.inventory;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile_fragment extends Fragment {


    public profile_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v4=inflater.inflate(R.layout.fragment_profile, container, false);;
        // Inflate the layout for this fragment
        Button save=(Button)v4.findViewById(R.id.btSave);
        Button cancel=(Button)v4.findViewById(R.id.btCancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changing fragment
                FragmentManager fm = getFragmentManager();// create fragment manager
                FragmentTransaction ft = fm.beginTransaction(); // start the transaction
                Home_fragment hfrag1 = new Home_fragment();
                ft.replace(R.id.fgMain, hfrag1);
                ft.commit();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changing fragment
                FragmentManager fm = getFragmentManager();// create fragment manager
                FragmentTransaction ft = fm.beginTransaction(); // start the transaction
                Home_fragment hfrag1 = new Home_fragment();
                ft.replace(R.id.fgMain, hfrag1);
                ft.commit();
            }
        });
        return v4;
    }

}
