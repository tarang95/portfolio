package com.assign.tarang.inventory;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;

public class Invoice_fragment extends Fragment {
    //declaring variables
    ArrayList<DataInventoryLogs> entries;
    DatabaseHelper myDb;
    DataInventoryLogs st_dil;
    Bundle bundleData;
    String stAll;
    final ArrayList<String> tempArrayList = new ArrayList<String>();
    ProgressDialog progressDialog;

    public Invoice_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //variable initailizer called
        myDb = new DatabaseHelper(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        // Inflate the layout for this fragment
        View view3 = inflater.inflate(R.layout.fragment_invoice, container, false);
        setHasOptionsMenu(true);
        bundleData = getArguments();
        entries = (ArrayList<DataInventoryLogs>) bundleData.getSerializable("KEY_AList");
        Iterator itr = entries.iterator();

        while (itr.hasNext()) {
            DataInventoryLogs st_dil = (DataInventoryLogs) itr.next();
            //saving into single string
            stAll = "Name: " + st_dil.getName() + " " + st_dil.getDateTime() + "\nLongitude: " + st_dil.getLong() + "Latitiude: " + st_dil.getLat() + " InvNo.:" + st_dil.getInvoiceNo().toString() + " " + st_dil.getQuality();
            tempArrayList.add(stAll);


        }

        // to maintain link with data, we need Adapter
        ArrayAdapter<String> our_list_adapter = new
                ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                tempArrayList);


        ListView our_list_view = (ListView) view3.findViewById(R.id.invoiceDisplay);

        our_list_view.setAdapter(our_list_adapter); // adapter is to maintain link with data in array

        our_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "You have clicked " + tempArrayList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        Button Back_to_Frag1 = (Button) view3.findViewById(R.id.btBackToInvoices);
        // Inflate the layout for this fragment
        Back_to_Frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();// create fragment manager
                FragmentTransaction ft = fm.beginTransaction(); // start the transaction
                dataEntry_fragment dfrag1 = new dataEntry_fragment();
                ft.replace(R.id.fgMain, dfrag1);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view3;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Send: {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage(R.string.popUp);
                builder1.setCancelable(true);

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setMessage("Sending mail please wait...");
                        progressDialog.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                progressDialog.hide();
                                progressDialog.dismiss();

                            }
                        }, 3000);


                        sendMail();


                    }
                });
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
            }
            return true;
            case R.id.Save: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.backPress);
                builder.setCancelable(true);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //saving and exiting application
                        boolean isUpdate = myDb.updateData(st_dil.getName().toString(), st_dil.getLong(), st_dil.getLat(),
                                st_dil.getDateTime().toString(),
                                st_dil.getInvoiceNo().toString(), st_dil.getQuality().toString());
                        System.exit(0);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
            return true;
            case R.id.Profile: {

                profile_fragment prof1 = new profile_fragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fgMain, prof1);
                // ft.addToBackStack(null);
                ft.commit();
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendMail() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] to = {"truvkamlesh@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "New Logger Data");
        String all="";
        for(String string : tempArrayList){
           all=all+string;
        }
        intent.putExtra(Intent.EXTRA_TEXT, all);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Sending mail"));
        //deleting data when it with values
        if (st_dil != null) {
            Integer deletedRows = myDb.deleteData(st_dil.getName().toString());
            st_dil = null;
        }
    }

}