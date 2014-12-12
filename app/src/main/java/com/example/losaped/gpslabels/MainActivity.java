package com.example.losaped.gpslabels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends ActionBarActivity {
    String AgentName = "";
    LocationManager locationManager = LocationManager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton getCoordinatesBtn = (ImageButton)findViewById(R.id.GetCoordinatesBtn);
        getCoordinatesBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView coordinatesLbl = (TextView)findViewById(R.id.CoordinatesTxt);
                        coordinatesLbl.setText("Координаты");
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
