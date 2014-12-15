package com.example.losaped.gpslabels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private String AgentName = "";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mApiClient, mLocationRequest, this);//(mApiClient, mLocationRequest, this)
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("App", "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView coordinatesLbl = (TextView)findViewById(R.id.CoordinatesTxt);
        coordinatesLbl.setText("Lat: " + Double.toString(location.getLatitude()) + " " + "Long: " + Double.toString(location.getLongitude()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton getCoordinatesBtn = (ImageButton)findViewById(R.id.GetCoordinatesBtn);
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        getCoordinatesBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView coordinatesLbl = (TextView)findViewById(R.id.CoordinatesTxt);
                        Context context = getApplicationContext();
                        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
                        if (status == ConnectionResult.SUCCESS)
                        {
                            coordinatesLbl.setText("Success");
                        }
                        else
                        {
                            coordinatesLbl.setText("");
                        }
                        TextView timeLbl = (TextView)findViewById(R.id.TimeTxt);
                        timeLbl.setText(DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date()));
                    }
                }
        );

        ImageButton sendCoordinatesBtn = (ImageButton)findViewById(R.id.SendCoordinatesBtn);
        sendCoordinatesBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAgentNameRequest();
                        //
                    }
                }
        );
    }

    private void showAgentNameRequest()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Укажите контрагента");
        final EditText inputBox = new EditText(this);
        alert.setView(inputBox);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AgentName = inputBox.getText().toString();
                if (AgentName.equals("") || AgentName == null)
                {
                    Toast.makeText(getApplicationContext(),
                            "Координаты не отправлены! Необходимо указать контрагента!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Координаты " + AgentName + " отправлены", Toast.LENGTH_LONG).show();
                }
            }
        });

        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        "Координаты не отправлены! Необходимо указать контрагента!", Toast.LENGTH_LONG).show();
            }
        });

        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
