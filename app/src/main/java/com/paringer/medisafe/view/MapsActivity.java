package com.paringer.medisafe.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paringer.medisafe.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String ARG_NAME = "name";
    public static final String ARG_LONGITUDE = "longitude";
    public static final String ARG_LATITUDE = "latitude";
    private GoogleMap mMap;
    private float latitude = 50.53f;
    private float longitude = 53.56f;
    private String name = "Ukraine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(getIntent() != null && getIntent().hasExtra(ARG_NAME)){
            latitude = getIntent().getFloatExtra(ARG_LATITUDE,50.53f);
            longitude = getIntent().getFloatExtra(ARG_LONGITUDE,53.56f);
            name = getIntent().getStringExtra(ARG_NAME);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng capitalCity = new LatLng(-34, 151);
        LatLng capitalCity = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(capitalCity).title("Marker in "+ name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(capitalCity));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(8.0f));
    }
}
