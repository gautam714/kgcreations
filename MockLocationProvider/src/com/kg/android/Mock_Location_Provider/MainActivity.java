package com.kg.android.Mock_Location_Provider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by kgautam on 4/9/2014.
 */
public class MainActivity extends Activity implements LocationListener{
    private String filePath = "";
    private static final int REQUEST_CODE = 10;
    private Integer mockLocationProviderIndex = 0;
    private MockLocationProviderTask mockLocationProviderTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /****test that mock locations are allowed so a more descriptive error message can be logged****/
        if (Settings.Secure.getInt(getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION, 0) == 0) {
            Toast.makeText(this, "MockLocations needs to be enabled",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        /**********************************************************************************************/

        /****Edit Texts****/
        final EditText minAccuracyEdit = (EditText)findViewById(R.id.edit_min_accuracy_range);
        final EditText maxAccuracyEdit = (EditText)findViewById(R.id.edit_max_accuracy_range);
        final EditText minTimeRangeEdit = (EditText)findViewById(R.id.edit_min_time_range);
        final EditText maxTimeRangeEdit = (EditText)findViewById(R.id.edit_max_time_range);
        /*****************/

        /****Buttons****/
        Button loadGpxFileButton = (Button)findViewById(R.id.load_gpx_file);
        Button startButton = (Button)findViewById(R.id.start_button);
        Button stopButton = (Button)findViewById(R.id.stop_button);
        Button helpButton = (Button)findViewById(R.id.help_Button);
        startButton.setBackgroundColor(Color.GREEN);
        stopButton.setBackgroundColor(Color.RED);
        /***************/

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                    minAccuracyEdit.getText().toString().trim().equalsIgnoreCase("")
                    ||maxAccuracyEdit.getText().toString().trim().equalsIgnoreCase("")
                    ||minTimeRangeEdit.getText().toString().trim().equalsIgnoreCase("")
                    ||maxTimeRangeEdit.getText().toString().trim().equalsIgnoreCase("")
                )
                {
                    Toast.makeText(MainActivity.this, "Please fill all values",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(filePath.trim().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Please load a gpx file",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                /****Location Manager****/
                LocationManager mockLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                String mockLocationProvider = LocationManager.GPS_PROVIDER;
                mockLocationManager.addTestProvider(mockLocationProvider, false, false, false, false, true, false, false, 0, 5);
                mockLocationManager.setTestProviderEnabled(mockLocationProvider, true);
                mockLocationManager.requestLocationUpdates(mockLocationProvider, 0, 0, MainActivity.this);
                /************************/

                mockLocationProviderTask = new MockLocationProviderTask();
                mockLocationProviderTask.execute(
                        minAccuracyEdit.getText().toString(),
                        maxAccuracyEdit.getText().toString(),
                        minTimeRangeEdit.getText().toString(),
                        maxTimeRangeEdit.getText().toString()
                );
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loadGpxFileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FileChooser.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("RETURN_FILE_PATH")){
                filePath = data.getExtras().getString("RETURN_FILE_PATH");
            }
            if(data.hasExtra("RETURN_FILE_NAME")){
                TextView textView = (TextView)findViewById(R.id.loaded_file);
                textView.setText("Filename : " + data.getExtras().getString("RETURN_FILE_NAME")
                + "\nFilepath : " + filePath);
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        try{
            mockLocationProviderTask.cancel(true);
            mockLocationProviderTask = null;
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            LocationManager mockLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            mockLocationManager.removeTestProvider(MockLocationProviderTask.MOCK_LOCATION_PROVIDER);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView textView = (TextView)findViewById(R.id.location_update_view);
        textView.setText("\n\nLatitude : " + location.getLatitude()
        + "\nLongitude : " + location.getLongitude());
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

    private class MockLocationProviderTask extends AsyncTask<String, Integer, Void> {
        public static final String MOCK_LOCATION_PROVIDER = "MockLocationProvider";
        public Integer index = 0;

        @Override
        protected Void doInBackground(String... params) {
            File gpxFile = new File(filePath);
            List<Location> gpxList = GPXParser.decodeGPX(gpxFile);

            /****Generating time interval range****/
            int minInterval = Integer.parseInt(params[2]);
            long minTimeGap = minInterval * 1000;
            int maxInterval = Integer.parseInt(params[3]);
            long maxTimeGap = maxInterval * 1000;
            /*************************************/

            for(Location location : gpxList){
                if(index < mockLocationProviderIndex){
                    index++;
                    continue;
                }
                publishProgress(index);
                Location mockLocation = new Location(LocationManager.GPS_PROVIDER);
                mockLocation.setLatitude(location.getLatitude());
                mockLocation.setLongitude(location.getLongitude());
                mockLocation.setAccuracy(16F);
                mockLocation.setTime(System.currentTimeMillis());
                mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                mockLocation.setAltitude(0D);
                mockLocation.setBearing(0F);

                LocationManager mockLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                mockLocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation);

                /****Generate random time interval****/
                Random random = new Random();
                long randomTimeInterval = minTimeGap + (long)(random.nextDouble()*(maxTimeGap-minTimeGap));
                /*************************************/

                try{
                    Thread.sleep((randomTimeInterval));
                    if(Thread.currentThread().isInterrupted())
                        throw new InterruptedException("Thread is interrupted");
                }catch(InterruptedException e){
                    break;
                }
                index++;
            }
            return null;
        }

        @Override
        protected  void onProgressUpdate(Integer... values){
            mockLocationProviderIndex = values[0];
        }
    }
}


