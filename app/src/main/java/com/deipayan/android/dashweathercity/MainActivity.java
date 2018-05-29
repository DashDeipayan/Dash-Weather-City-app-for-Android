package com.deipayan.android.dashweathercity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import org.json.JSONObject;

public class MainActivity extends Activity {

    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    final int REQUEST_CODE = 123; // Request Code for permission request callback

    final String APP_ID = "6378965dda5cfa0facfc6a887956c9c8";

    final long MIN_TIME = 5000;

    final float MIN_DISTANCE = 1000;

    final String LOGCAT_TAG = "Clima";

    EditText city;
    TextView mTemperatureLabel;
    ImageView mWeatherImage;
    TextView mCityLabel;
    Button refresh;
    ImageButton backbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCityLabel = (TextView) findViewById(R.id.locationTV);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);
        mWeatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FindCity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent myintent = getIntent();
        String city = myintent.getStringExtra("Cityname");
        if(city != null)
            getWeatherForCity(city);
        else
            getWeatherForCity("London");
    }

    private void getWeatherForCity(String city){


        RequestParams params = new RequestParams();
        params.put("q",city);
        params.put("appid",APP_ID);
        letsDoSomeNetworking(params);
    }
    private void letsDoSomeNetworking(RequestParams params) {

        // AsyncHttpClient belongs to the loopj dependency.
        AsyncHttpClient client = new AsyncHttpClient();

        // Making an HTTP GET request by providing a URL and the parameters.
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(LOGCAT_TAG, "Success! JSON: " + response.toString());
                WeatherDataModel weatherDataModel = WeatherDataModel.fromJson(response);
                updateUI(weatherDataModel);



            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

                Log.e(LOGCAT_TAG, "Fail " + e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();

                Log.d(LOGCAT_TAG, "Status code " + statusCode);
                Log.d(LOGCAT_TAG, "Here's what we got instead " + response.toString());
            }

        });
    }
    private void updateUI(WeatherDataModel weatherDataModel){

        mTemperatureLabel.setText(weatherDataModel.getTemperature());
        mCityLabel.setText(weatherDataModel.getCity());
        mWeatherImage.setImageResource(getResources().getIdentifier(weatherDataModel.getIconName(),"drawable",getPackageName()));

    }

}






