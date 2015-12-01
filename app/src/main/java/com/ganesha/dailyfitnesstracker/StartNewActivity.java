package com.ganesha.dailyfitnesstracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StartNewActivity extends FragmentActivity implements  SensorEventListener, ConnectionCallbacks, OnConnectionFailedListener  {


    //Variable for the google map
    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    private Button NormalView;
    private Button SatelliteView;
    private Button HybridView;
    private Button TerrainView;

    private TextView StepCountsNew;
    //Variables for the Step Counter
    private TextView StepCounts;
    private TextView Miles;
    private TextView CaloriesBurn;
   private TextView Duration1;

    private TextView StartTime;
    private TextView ElapsedTime;
    private TextView CurrentTime;
    private TextView caloriesburn;
    private TextView Distance;



    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;
    private Spinner spinner;
    private GoogleApiClient mGoogleApiClient;


    //Variables for Calories burn
    Chronometer mChronometer;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

       // caloriesburn=(TextView)findViewById(R.id.txtCaloriesBurn);




      /*  ParseUser currentUser=ParseUser.getCurrentUser();
        currentUser.put("StepCounts", StepCounts.getText().toString());
        currentUser.put("CaloriesBurn", caloriesburn.getText().toString());
        currentUser.saveInBackground();*/


        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");
        FlurryAgent.setLogEnabled(false);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_new);



    // mChronometer = (Chronometer) findViewById(R.id.chronometer);

       // mChronometer.start();

        Chronometer timeElapsed  = (Chronometer) findViewById(R.id.chronometer);
        timeElapsed.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h   = (int)(time/3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                cArg.setText(hh+":"+mm+":"+ss);
            }
        });
        timeElapsed.setBase(SystemClock.elapsedRealtime());
        timeElapsed.start();

       // TextClock NewFormat=(TextClock)findViewById(R.id.textClock);







        //To Get the Around me functions



        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

       //Setting up the Different views on the Map

        NormalView=(Button)findViewById(R.id.btnNormal);
        SatelliteView=(Button)findViewById(R.id.btnSatellite);
        HybridView=(Button)findViewById(R.id.btnHybrid);
        TerrainView=(Button)findViewById(R.id.btnTerrain);

        //Start time activity
        StartTime=(TextView)findViewById(R.id.textClock1);
        CurrentTime=(TextView)findViewById(R.id.textClock);
        ElapsedTime=(TextView)findViewById(R.id.ElapsedTime1);



       // StartTime.setText(""+CurrentTime);

      //  ElapsedTime.setText(""+mChronometer);


      /*  String ST= StartTime.getText().toString().trim();
        String CT= CurrentTime.getText().toString().trim();
        String ET=ElapsedTime.getText().toString().trim();


        CT=ST;
        ET=mChronometer+CT;*/

        //Step Counter Activities
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        StepCounts = (TextView) findViewById(R.id.txtStepsCounts);
        Miles=(TextView)findViewById(R.id.txtMiles);
        CaloriesBurn=(TextView)findViewById(R.id.txtCaloriesBurn);
       // textView = (TextView) findViewById(R.id.txtStepsCounts);



        //****************************************************//
        //All the coding for the Google Map*******************//
        //***************************************************//



        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        markerPoints = new ArrayList<LatLng>();

        // Getting reference to SupportMapFragment of the activity_main
        // Getting Map for the SupportMapFragment
        map = fm.getMap();



        NormalView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        SatelliteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        HybridView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        TerrainView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);


        if(map!=null){

            // Enable MyLocation Button in the Map
            map.setMyLocationEnabled(true);

            // Setting onclick event listener for the map
            map.setOnMapClickListener(new OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    // Already two locations
                    if(markerPoints.size()>1){
                        markerPoints.clear();
                        map.clear();
                    }

                    // Adding new item to the ArrayList
                    markerPoints.add(point);

                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();

                    // Setting the position of the marker
                    options.position(point);

                    /**
                     * For the start location, the color of marker is GREEN and
                     * for the end location, the color of marker is RED.
                     */
                    if(markerPoints.size()==1){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }else if(markerPoints.size()==2){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }

                    // Add new marker to the Google Map Android API V2
                    map.addMarker(options);

                    // Checks, whether start and end locations are captured
                    if(markerPoints.size() >= 2){
                        LatLng origin = markerPoints.get(0);
                        LatLng dest = markerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);

                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    //*******************************************************************//
    //****Sensor code for Step Counts************************************//
    //*******************************************************************//

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;
        DecimalFormat new1 = new DecimalFormat("#.###");
            String weight1="70";

            double OneStepValue=(0.0004745848);

            double CaloriesBurnPerOneStepOneKilo=1.31362941;

            double ChangeType= Double.parseDouble(weight1);

            double CaloriesPerOneStep=(CaloriesBurnPerOneStepOneKilo*ChangeType)/(2111);

            if (values.length > 0) {
                value = (int) values[0];
            }
            if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                StepCounts.setText("             " + value);
                Miles.setText(" "+new1.format(OneStepValue*value));
               CaloriesBurn.setText("  "+new1.format(CaloriesPerOneStep*value));

            } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                // For test only. Only allowed value is 1.0 i.e. for step taken
                StepCounts.setText("            " + value);
               Miles.setText("     "+new1.format(OneStepValue*value));
               CaloriesBurn.setText("     "+new1.format(CaloriesPerOneStep*value));
            }
        }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mStepCounterSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onStop() {
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }



    //*********************************************//
    //** A method to download json data from url *//
    //********************************************//


    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }


        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_new, menu);
        return true;
    }




    //*******************************************************//
    //Menu Item Code for the TOP CORNER**********************//
    //*******************************************************//




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        MenuItem newitem = null;

        mChronometer = (Chronometer) findViewById(R.id.chronometer);


        switch(id)
        {


            case R.id.Stop:



             caloriesburn=(TextView) findViewById(R.id.txtCaloriesBurn);

              Distance=(TextView)findViewById(R.id.txtMiles);

                Duration1=(TextView) findViewById(R.id.chronometer);

                CurrentTime=(TextView)findViewById(R.id.textClock);

                StartTime=(TextView)findViewById(R.id.textClock1);


                String scounts=StepCounts.getText().toString();

                String cbu=caloriesburn.getText().toString();

                String dist=Distance.getText().toString();

                String durations = Duration1.getText().toString();

                String ctime=CurrentTime.getText().toString();

                String Stime=StartTime.getText().toString();

                String Etime=ElapsedTime.getText().toString();

                Intent TakeUserHomePage=new Intent(StartNewActivity.this, History.class);

                Bundle bundle=new Bundle();

                bundle.putString("StepCounts", scounts);

                bundle.putString("CaloriesBurn",cbu);

                bundle.putString("Distance",dist);

                bundle.putString("Duration", durations);

                bundle.putString("CurrentTime",ctime);

                bundle.putString("StartTime",Stime);

                bundle.putString("ElapsedTime", Etime);

                StartNewActivity.this.finish();

                TakeUserHomePage.putExtras(bundle);

                startActivity(TakeUserHomePage);

                StartNewActivity.this.finish();

               // Intent TakeUserLoginPage1=new Intent(StartNewActivity.this, LoginPage.class);
                //startActivity(TakeUserLoginPage1);
               // StartNewActivity.this.finish();
                break;

            case R.id.Logout:
                //logout the user
                ParseUser.logOut();
                Intent TakeUserLoginPage=new Intent(StartNewActivity.this, LoginPage.class);
                startActivity(TakeUserLoginPage);
                StartNewActivity.this.finish();
                break;

            case R.id.History:
                //logout the user
                Intent NewHistory=new Intent(StartNewActivity.this, History1.class);
                startActivity(NewHistory);
                //StartNewActivity.this.finish();
                break;


            case R.id.Settings:
                //take user to settings page
                Intent intent=new Intent(this, Settings.class);
                startActivity(intent);
                StartNewActivity.this.finish();
                break;




        }


        return super.onOptionsItemSelected(item);
    }



}
