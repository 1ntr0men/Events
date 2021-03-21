package com.example.events;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    ImageButton manButton;
    ImageButton plusButton;
    ImageButton calendarButton;
    ImageButton peopleButton;
    ImageButton menuButton;

    TextView debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ActivitySecond();
        setContentView(R.layout.activity_second);

        manButton = findViewById(R.id.Man_button);
        plusButton = findViewById(R.id.Plus_button);
        calendarButton = findViewById(R.id.Calendar_button);
        peopleButton = findViewById(R.id.People_button);
        menuButton = findViewById(R.id.Menu_button);



        debug = findViewById(R.id.textView2);

        Touch(menuButton);Touch(manButton);Touch(calendarButton);Touch(plusButton);Touch(peopleButton);



//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
//        FloatingActionButton fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //
    }
    private void Touch(ImageButton butn){
        butn.setOnTouchListener(new View.OnTouchListener() {
            Animation clickAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_clicked);
            ObjectAnimator objectAnimatorx;
            ObjectAnimator objectAnimatory;
            float startPos = butn.getScaleX();

            @Override
            public boolean onTouch(View v, MotionEvent event) {



                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    butn.setColorFilter(0xFFD0D0D0);
                    objectAnimatorx= ObjectAnimator.ofFloat(butn, "ScaleX", startPos, 0.8f*startPos);
                    objectAnimatorx.setDuration(100);
                    objectAnimatory= ObjectAnimator.ofFloat(butn, "ScaleY", startPos, 0.8f*startPos);
                    objectAnimatory.setDuration(100);
                    objectAnimatorx.start();
                    objectAnimatory.start();
                  //  butn.startAnimation(clickAnim);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    butn.setColorFilter(0xFFC8C8C8);
                   // butn.clearAnimation();
                    objectAnimatorx= ObjectAnimator.ofFloat(butn, "ScaleX", 0.8f*startPos, startPos);
                    objectAnimatory= ObjectAnimator.ofFloat(butn, "ScaleY", 0.8f*startPos, startPos);
                    objectAnimatorx.setDuration(100);
                    objectAnimatory.setDuration(100);
                    objectAnimatorx.start();
                    objectAnimatory.start();
                    butn.setScaleX(startPos);
                    butn.setScaleY(startPos);
                }
                return false;
            }
        });
    }


}
