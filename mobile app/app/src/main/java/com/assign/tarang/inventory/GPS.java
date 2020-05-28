package com.assign.tarang.inventory;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;


import static android.content.Context.LOCATION_SERVICE;

public class GPS implements LocationListener {
    private LocationManager locationManager;
    Context context;

    public GPS(Context c) {
        context = c;
    }

    //getting location on calling this method
    public Location getLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }

        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        boolean isEnabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isEnabledGPS) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
            Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        } else {
            Toast.makeText(context, "Plz enabled GPS", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
