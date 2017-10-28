package au.edu.usc.adm017.myactivities.fragments.util;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import au.edu.usc.adm017.myactivities.ViewActivities;

/**
 * Created by Alex on 28/10/2017.
 */

public class ActivityLocationListener implements LocationListener {

    public ActivityLocationListener() {
        LocationManager locationManager = (LocationManager) ViewActivities.getInstance().getSystemService(Context.LOCATION_SERVICE);


        try {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);
            Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

            if (loc != null) {
                Util.setLocation(loc);
            }
        } catch (SecurityException e) {
        } catch (IllegalArgumentException e) {
        }

        try {
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, this);
            Location loc = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

            if (loc != null) {
                Util.setLocation(loc);
            }

        } catch (SecurityException e) {
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void onLocationChanged(Location location) {
            Util.setLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivities.getInstance());
        builder.setMessage("prov: " + provider).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}