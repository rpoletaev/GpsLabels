package com.example.losaped.gpslabels.;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by losaped on 12.12.14. sfds
 */
public class MyGPSService {
    private static MyGPSService ourInstance = new MyGPSService();
    //private boolean isGPSEnabled = false;
    //private boolean isNetworkEnabled = false;
    //private boolean canGetLocation = false;
    //Location location;
    LocationManager locationManager;

    public static MyGPSService getInstance() {
        return ourInstance;
    }

    private MyGPSService() {
        Context context = getApplicationContext();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private boolean CanGetLocation()
    {
        return (isGPSEnabled() || isNetworkEnabled());
    }

    public Location GetLocation()
    {
        if (CanGetLocation())
        {
            if(isNetworkEnabled())
            {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, );
            }
        }
    }

    private boolean isGPSEnabled()
    {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isNetworkEnabled()
    {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
