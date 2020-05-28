package com.assign.tarang.inventory;


import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.LOCATION_SERVICE;

public class dataEntry_fragment extends Fragment {
    //Initializing arrays
    Spinner spinner;
    DatabaseHelper myDb;
    final ArrayList<String> arrayList_quality = new ArrayList<>();
    ArrayList<DataInventoryLogs> arrayList_DataInventoryLogs = new ArrayList<DataInventoryLogs>();

    //Initializing variable of xml
    EditText invoiceNum;
    String spinnerData;
    String uName;
    String date_time;
    String longitude;
    String latitude;
    Bundle obj1;
    GPS gps;

    public dataEntry_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gps = new GPS(getActivity().getApplicationContext());
        Location l = gps.getLocation();
        if (l != null) {
            latitude = Double.toString(l.getLatitude());
            longitude = Double.toString(l.getLongitude());
        }
        myDb = new DatabaseHelper(getActivity());
        final View view2 = inflater.inflate(R.layout.fragment_data, container, false);
        obj1 = getArguments();

        //checking for bundle object null
        if (obj1 != null) {
            uName = obj1.getString("userName");
        }
        //for invoice String
        invoiceNum = (EditText) view2.findViewById(R.id.etInvNumber);
        String invNumber = invoiceNum.getText().toString();

        //adding date and time
        Calendar calendar1 = Calendar.getInstance(); // For adding data and time -----------
        SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf_date.format(calendar1.getTime());
        SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");
        String time = sdf_time.format(calendar1.getTime());
        date_time = "Date:" + date + " Time:" + time;


        Button addDataButton = (Button) view2.findViewById(R.id.btAddItem);
        Button showDataButton = (Button) view2.findViewById(R.id.btShowItem);

        spinner = (Spinner) view2.findViewById(R.id.spQuality);
        //adding spinner data
        fillSpinner(arrayList_quality, spinner);
        //add item on click listener
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(invoiceNum.getText().toString().equals(""))) {
                    if (!(spinner.getSelectedItem().toString().equals(""))) {
                        Location l = gps.getLocation();
                        if (l != null) {
                            latitude = Double.toString(l.getLatitude());
                            longitude = Double.toString(l.getLongitude());
                        }

                        DataInventoryLogs obj_Log = new DataInventoryLogs(uName, date_time, longitude, latitude, invoiceNum.getText().toString(), spinnerData);
                        arrayList_DataInventoryLogs.add(obj_Log);
                        Toast.makeText(getActivity(), "Entry Added", Toast.LENGTH_SHORT).show();
                        spinner.setSelection(0);

                        if (view2 == null)
                            fillSpinner(arrayList_quality, spinner);

                        invoiceNum.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Entry not saved as not all data entered.\n Complete all enteries and try again.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Entry not saved as not all data entered.\n Complete all enteries and try again.", Toast.LENGTH_LONG).show();
                }

            }
        });
        //show Data on click listener
        showDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle_Frag2 = new Bundle();
                bundle_Frag2.putSerializable("KEY_AList", arrayList_DataInventoryLogs);
                //changing fragment
                FragmentManager fm = getFragmentManager();// create fragment manager
                FragmentTransaction ft = fm.beginTransaction(); // start the transaction
                Invoice_fragment ifrag1 = new Invoice_fragment();  // creating the fragment2 denoted as fg2

                //passing values in bundle
                ifrag1.setArguments(bundle_Frag2);  // fg3 is now updated
                ft.replace(R.id.fgMain, ifrag1);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        //getting spinner data and selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerData = arrayList_quality.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return view2;
    }

    //spinner filling method
    private void fillSpinner(ArrayList<String> lsQuality, Spinner spnr) {

        lsQuality.add("");
        lsQuality.add("Failed");
        lsQuality.add("Poor");
        lsQuality.add("Average");
        lsQuality.add("Good");

        // to maintain link with data, we need Adapter
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                lsQuality);

        spnr.setAdapter(adapter);
    }



}
