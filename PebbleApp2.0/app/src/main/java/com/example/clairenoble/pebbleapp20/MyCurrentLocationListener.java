package com.example.clairenoble.pebbleapp20;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by clairenoble on 3/6/16.
 */
public class MyCurrentLocationListener implements LocationListener {

    public String longitude = null;
    public String latitude = null;

    @Override
    public void onLocationChanged(Location loc) {
        longitude = loc.getLongitude()+"";
        Log.e("Long", longitude);
        latitude = loc.getLatitude()+"";
        Log.e("Lati", latitude);
    }

    public String getLongitude(){
        return longitude;
    }

    public String getLatitude(){
        return latitude;
    }
    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
