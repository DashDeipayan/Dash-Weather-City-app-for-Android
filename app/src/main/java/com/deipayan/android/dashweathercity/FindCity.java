package com.deipayan.android.dashweathercity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FindCity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_city);
        final EditText changeCityName = findViewById(R.id.CityNameText);



        changeCityName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String cityname = changeCityName.getText().toString();
                Intent intent = new Intent(FindCity.this,MainActivity.class);
                intent.putExtra("Cityname",cityname);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }
}
