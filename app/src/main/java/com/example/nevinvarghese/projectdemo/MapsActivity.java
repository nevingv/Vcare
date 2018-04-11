package com.example.nevinvarghese.projectdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener{

    private GoogleMap mMap;
    TextView results;
    // URL of object to be parsed
    Location mLocation;
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;
    double Lat,Lng;
    String lats,lngs;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    LocationManager locationManager;

    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 minute


    String Gallery="";
    String Pharmacy="";
    String Park="";
    String Zoo="";
    double lat=9.5916;
    double lng=76.5222;
    double lati,longi;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    JSONObject Itemset=new JSONObject();
    JSONObject Loc=new JSONObject();
    JSONObject Loct=new JSONObject();
    String Item;
    int num=0;
    int resID;
    TextView t;
    String Result[]=new String[100];
    String Resultset1[]=new String[100];
    String Resultset2[]=new String[100];
    String Target[]=new String[100];
    String JsonURLGallery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=5000&type=hospital&key=AIzaSyACEdCb1G7wu1pa86RExAnmm5l8hg0ldks";
    String JsonURLPharmacy ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=5000&type=pharmacy&key=AIzaSyACEdCb1G7wu1pa86RExAnmm5l8hg0ldks";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                1);




    }


    public void showHosp(String JsonURL) {

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        //results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new com.android.volley.Response.Listener<JSONObject>() {



                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray records = response.getJSONArray("results");
                            //Toast.makeText(TasksActivity.this,records.toString(),Toast.LENGTH_LONG).show();
                            String str="";
                            for(int i=0;i<records.length();++i)
                            {
                                Itemset=records.getJSONObject(i);
                                //Toast.makeText(TasksActivity.this,Itemset.toString(),Toast.LENGTH_SHORT).show();
                                Loc=Itemset.getJSONObject("geometry");
                                Loct=Loc.getJSONObject("location");
                                //str=str+Itemset.getString("name")+": "+Loct.getString("lat")+"//"+Loct.getString("lng")+"\n";

                               // Item=Itemset.getString("name")+","+Loct.getDouble("lat")+","+Loct.getDouble("lng");
                                //Gallery=Gallery+Item+"\n";
                                mMap.addMarker(new MarkerOptions().position(new LatLng(Loct.getDouble("lat"),Loct.getDouble("lng"))).title(Itemset.getString("name")));

                            }
                            Result= Gallery.split("\n");
                            Target[1]=Result[0];
                            Target[2]=Result[1];
                            Resultset1=Result[0].split(",");
                            Resultset2=Result[1].split(",");
                            lati=Double.parseDouble(Resultset1[1]);
                            longi=Double.parseDouble(Resultset1[2]);
                           // Toast.makeText(MapsActivity.this,""+Resultset1[1]+" "+Resultset1[2]+" ",Toast.LENGTH_LONG).show();
                            //Toast.makeText(MapsActivity.this,""+Resultset1[0]+" ",Toast.LENGTH_LONG).show();

                            //Toast.makeText(TasksActivity.this,Result[0],Toast.LENGTH_LONG).show();

                            //num=num+1;
                            //resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                           // t=(TextView) findViewById(resID);
                            //t.setText(Resultset1[0]);
                            //num=num+1;
                            //resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            //t=(TextView) findViewById(resID);
                            //t.setText(Resultset2[0]);




                            //results.setText(Gallery);
                            //results.setText(str);
                            //JSONObject b=obj.getJSONObject(0);

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            //String color = obj.getString("colorName");
                            //String desc = obj.getString("description");

                            // Adds strings from object to the "data" string

                            //data = obj.getString(0);
                            //Toast.makeText(TasksActivity.this,"***",Toast.LENGTH_LONG).show();
                            // Adds the data string to the TextView "results"
                            //results.setText(data);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            //Toast.makeText(TasksActivity.this,"+++",Toast.LENGTH_LONG).show();
                            //results.setText(e.getMessage());
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Log.d("check",error.getMessage());
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

    public void showPharmacy(String JsonURL) {

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        //results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new com.android.volley.Response.Listener<JSONObject>() {



                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray records = response.getJSONArray("results");
                            //Toast.makeText(TasksActivity.this,records.toString(),Toast.LENGTH_LONG).show();
                            String str="";
                            for(int i=0;i<records.length();++i)
                            {
                                Itemset=records.getJSONObject(i);
                                //Toast.makeText(TasksActivity.this,Itemset.toString(),Toast.LENGTH_SHORT).show();
                                Loc=Itemset.getJSONObject("geometry");
                                Loct=Loc.getJSONObject("location");
                                //str=str+Itemset.getString("name")+": "+Loct.getString("lat")+"//"+Loct.getString("lng")+"\n";

                                // Item=Itemset.getString("name")+","+Loct.getDouble("lat")+","+Loct.getDouble("lng");
                                //Gallery=Gallery+Item+"\n";
                                mMap.addMarker(new MarkerOptions().position(new LatLng(Loct.getDouble("lat"),Loct.getDouble("lng"))).title(Itemset.getString("name")));

                            }
                            Result= Pharmacy.split("\n");
                            Target[1]=Result[0];
                            Target[2]=Result[1];
                            Resultset1=Result[0].split(",");
                            Resultset2=Result[1].split(",");
                            lati=Double.parseDouble(Resultset1[1]);
                            longi=Double.parseDouble(Resultset1[2]);
                            // Toast.makeText(MapsActivity.this,""+Resultset1[1]+" "+Resultset1[2]+" ",Toast.LENGTH_LONG).show();
                            //Toast.makeText(MapsActivity.this,""+Resultset1[0]+" ",Toast.LENGTH_LONG).show();

                            //Toast.makeText(TasksActivity.this,Result[0],Toast.LENGTH_LONG).show();

                            //num=num+1;
                            //resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            // t=(TextView) findViewById(resID);
                            //t.setText(Resultset1[0]);
                            //num=num+1;
                            //resID = getResources().getIdentifier("text"+num, "id", getPackageName());
                            //t=(TextView) findViewById(resID);
                            //t.setText(Resultset2[0]);




                            //results.setText(Gallery);
                            //results.setText(str);
                            //JSONObject b=obj.getJSONObject(0);

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            //String color = obj.getString("colorName");
                            //String desc = obj.getString("description");

                            // Adds strings from object to the "data" string

                            //data = obj.getString(0);
                            //Toast.makeText(TasksActivity.this,"***",Toast.LENGTH_LONG).show();
                            // Adds the data string to the TextView "results"
                            //results.setText(data);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            //Toast.makeText(TasksActivity.this,"+++",Toast.LENGTH_LONG).show();
                            //results.setText(e.getMessage());
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        Log.d("check",error.getMessage());
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                            getLocation();

                            // check if GPS enabled
                            if(canGetLocation()){

                                double latitude = getLatitude();
                                double longitude = getLongitude();

                                // \n is for new line
                                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                            }else{
                                // can't get location
                                // GPS or Network is not enabled
                                // Ask user to enable GPS/network in settings
                                showSettingsAlert();


                        }


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MapsActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                Log.i("test", "Hello");
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    Log.i("test", "isNetworkEnabled");
                    if (ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return null;
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, MapsActivity.this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    Log.i("test", "isGPSEnabled");

                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            Toast.makeText(MapsActivity.this,"Loc error",Toast.LENGTH_LONG);
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplicationContext().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        //alertDialog.show();
        Toast.makeText(MapsActivity.this, "Turn gps on", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        com.example.nevinvarghese.projectdemo.GPSTracker gpsTracker = new com.example.nevinvarghese.projectdemo.GPSTracker(getApplicationContext());

        location = getLocation();
        //Toast.makeText(MapsActivity.this,"Hello"+mLocation.getLatitude()+"",Toast.LENGTH_LONG).show();
        //Toast.makeText(MapsActivity.this,"Break",Toast.LENGTH_LONG);
        try {
            lat = location.getLatitude();
            lng = location.getLongitude();
            Toast.makeText(MapsActivity.this,"Lat Long Found",Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            Toast.makeText(MapsActivity.this,"Error in loc",Toast.LENGTH_LONG).show();
        }
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lng))
                .title("User")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18f));




        JsonURLGallery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=5000&type=hospital&key=AIzaSyCEA7bfJuRYCsBytreNjQGUUK-N3CEBdc0";
        Log.d("URL",JsonURLGallery);
        showHosp(JsonURLGallery);


        JsonURLPharmacy = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=5000&type=pharmacy&key=AIzaSyCEA7bfJuRYCsBytreNjQGUUK-N3CEBdc0";
        Log.d("URL",JsonURLPharmacy);
        showPharmacy(JsonURLPharmacy);



        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
