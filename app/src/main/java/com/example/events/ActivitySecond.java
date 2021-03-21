package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.R;

public class ActivitySecond extends AppCompatActivity {

    ImageButton manButton;
    ImageButton plusButton;
    ImageButton calendarButton;
    ImageButton peopleButton;
    ImageButton menuButton;

    TextView debug;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        manButton = findViewById(R.id.Man_button);
        plusButton = findViewById(R.id.Plus_button);
        calendarButton = findViewById(R.id.Calendar_button);
        peopleButton = findViewById(R.id.People_button);
        menuButton = findViewById(R.id.Menu_button);

        debug = findViewById(R.id.textView2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  debug.setText("Button2 tapped");
               // Toast.makeText(ActivitySecond.this, "OK", Toast.LENGTH_LONG).show();
            }
        });


        manButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug.setText("Button tapped");
            }
        });

//        manButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    debug.setText("Button tapped");
//                    //manButton.setImageTintMode(PorterDuff.Mode.valueOf("add"));
//                }
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    debug.setText("Button untapped");
//                }
//                return false;
//            }
//        });
    }
}

