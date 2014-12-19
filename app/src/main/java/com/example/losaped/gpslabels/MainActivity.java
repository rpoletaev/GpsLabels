package com.example.losaped.gpslabels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
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

import java.util.Date;

public class MainActivity extends Activity {

    private String AgentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton getCoordinatesBtn = (ImageButton) findViewById(R.id.GetCoordinatesBtn);
        ImageButton sendCoordinatesBtn = (ImageButton)findViewById(R.id.SendCoordinatesBtn);

        getCoordinatesBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TextView coordinatesLbl = (TextView) findViewById(R.id.CoordinatesTxt);
                        TextView timeLbl = (TextView) findViewById(R.id.TimeTxt);

                        try {
                            Location location = getLocation();
                            if (location != null) {
                                coordinatesLbl.setText("Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
                                //timeLbl.setText(location.getTime())
                                timeLbl.setText(DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date()));
                            }
                        }
                        catch (Exception e)
                        {
                            coordinatesLbl.setText(e.getMessage());
                        }
                    }
                }
        );

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

    public Location getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String provider = "";
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
            Log.d("PROVIDER", provider);
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            Log.d("PROVIDER", provider);
        }
        if (provider.equals(""))
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


        Intent intent = new Intent(this, LocationManager.class);
        locationManager.requestSingleUpdate(provider, PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        return locationManager.getLastKnownLocation(provider);
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
