package com.example.clairenoble.pebbleapp20;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.getpebble.android.kit.Constants;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private final static UUID PEBBLE_APP_UUID = UUID.fromString("4130a2db-70a8-4fc8-8f06-be12dbddc261");

    public String longitude = null;
    public String latitude = null;

    EditText edittext;
    String phoneNo = "911";

    String regexStr = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
    Pattern pattern = Pattern.compile(regexStr);
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        edittext = (EditText) findViewById(R.id.phoneNumEdit);

        // restore previous state
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if (sharedPref.contains("phoneNo")) {
            phoneNo = sharedPref.getString("phoneNo", "");
        } else {
            phoneNo = "911";
        }
        edittext.setText(phoneNo);

        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // done button pressed
                    // make edittext uneditable
                    edittext.setEnabled(false);
                    // call check fn -> if good, save number
                    String num = edittext.getText().toString().trim();
                    if (checkNumValid(num) == true) {
                        // is valid number, save
                        phoneNo = num;
                    } else {
                        showMessage("Bad phone number format");
                    }
                    edittext.setText(phoneNo);
                    return true;
                }
                return false;
            }
        });

        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    // make edittext uneditable
                    edittext.setEnabled(false);
                    // close keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    // call check fn -> trim before calling
                    String num = edittext.getText().toString().trim();
                    if (checkNumValid(num) == true) {
                        // is valid number, save
                        phoneNo = num;
                    } else {
                        showMessage("Bad phone number format");
                    }
                    edittext.setText(phoneNo);
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the number
                // set edittext to editable, set focus to it.
                edittext.setEnabled(true);
                edittext.requestFocus();
                // get keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.

        // See https://g.co/AppIndexing/AndroidStudio for more information.

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        MyCurrentLocationListener locationListener = new MyCurrentLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        //Toast.makeText(this, "Longitude: " + locationListener.getLongitude() + "Latitude: "+locationListener.getLatitude());
        //final String lati = locationListener.getLatitude();
        //final String longi = locationListener.getLongitude();
        //System.out.println(lati);
        Location loca = locationManager.getLastKnownLocation(locationManager.getAllProviders().get(0));
        latitude = "" + loca.getLongitude() + "";
        longitude = "" + loca.getLatitude() + "";

        //latitude = locationListener.getLatitude();
        //longitude = locationListener.getLongitude();
                PebbleKit.registerReceivedDataHandler(this, new PebbleKit.PebbleDataReceiver(PEBBLE_APP_UUID) {


                    @Override
                    public void receiveData(final Context context, final int transactionId, final PebbleDictionary data) {
                        //showMessage("pebble");
                        //System.out.println("Got this shit:" + data.contains(0));
                        showMessage(latitude.getClass().getName() + ", " + longitude.getClass().getName());
                        String la = new String(latitude);
                        String lo = new String(longitude);
                        //sendSMS(formatSMS("" + la + "", "" + lo + ""));
                        sendSMS(formatSMS("51.5034070","-0.1275920"));
                        showMessage("lat: " + la + ", long: " + lo);
                        PebbleKit.sendAckToPebble(getApplicationContext(), transactionId);
                    }
                });

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

            public void gotLocation(Location location) {
                if (location != null) {
                    latitude  = location.getLatitude() + "";
                    longitude  = location.getLongitude() + "";
                }

            }

            public String getLatitude() {
                return latitude;
            }
            public String getLongitude() {
                return longitude;
            }
        };
        //location = new String (locationResult.getLatitude() + " " + locationResult.getLongitude());

    }



    public String formatSMS(String x, String y){ // 0 is lattitude

        String location = x + ", " + y;
        String SMSMessage ="Pebble distress call at " + location
                + ". Please carefully assess the situation and send help if necessary:"
                + " http://maps.google.com/?ll=" + x + "," + y;
        return SMSMessage;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //showMessage("back key pressed");
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean checkNumValid(String num){
        if(num.equals("911")) return true;
        // regex any other #
        matcher = pattern.matcher(num);
        if (matcher.matches()) return true;

        return false;
    }

    private void sendSMS(String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null,""+ message +"", null, null);
        showMessage("SUCCESSFUL SMS: " + message);
    }

    public void showMessage(String m) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show();
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
/* ??? has to do with settings on action bar
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isConnected = PebbleKit.isWatchConnected(this);
        Toast.makeText(this, "Pebble " + (isConnected ? "is" : "is not") + " connected!", Toast.LENGTH_LONG).show();

        if (PebbleKit.areAppMessagesSupported(getApplicationContext())) {
            Log.i(getLocalClassName(), "App Message is supported!");
        } else {
            Log.i(getLocalClassName(), "App Message is not supported");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.clairenoble.pebbleapp20/http/host/path")
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        // save phoneNo
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("phoneNo", phoneNo);
        editor.commit();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.clairenoble.pebbleapp20/http/host/path")
        );
    }
}
