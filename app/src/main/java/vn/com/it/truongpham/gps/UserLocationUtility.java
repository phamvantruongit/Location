package vn.com.it.truongpham.gps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class UserLocationUtility extends LocationCallback {
    private static final String TAG = "UserLocationUtility";

    private boolean mIsReceivingUpdates;
    LocationCallback locationCallback;
    LocationRequest request;

    private FusedLocationProviderClient mLocationClient;

    public UserLocationUtility(Activity activity) {
        mLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        request = new LocationRequest();
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @SuppressLint("MissingPermission")
    public void getCurrentLocationOneTime(final UserLocationCallback callback) {

        if (mIsReceivingUpdates) {
            return;
        }


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    callback.onLocationResult(locationResult.getLastLocation());
                } else {
                    callback.onFailedRequest("Location request returned null");
                }
            }
        };

        mLocationClient.requestLocationUpdates(request, locationCallback, null);
        mIsReceivingUpdates = true;


    }


    public void stopLocationUpdates() {

        mLocationClient.removeLocationUpdates(locationCallback);
        mIsReceivingUpdates = false;
        Log.d(TAG, "stopLocationUpdates(): Location updates removed");

    }

    interface UserLocationCallback {
        void onFailedRequest(String message);

        void onLocationResult(Location location);
    }

}
