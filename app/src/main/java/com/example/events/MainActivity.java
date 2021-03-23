package com.example.events;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.events.ui.main.ManFragment;
import com.example.events.ui.main.MenuFragment;
import com.example.events.ui.main.PeopleFragment;
import com.example.events.ui.main.PlusFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

    public ImageButton manButton;
    public ImageButton plusButton;
    public ImageButton calendarButton;
    public ImageButton peopleButton;
    public ImageButton menuButton;

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

        Fragment _menu = new MenuFragment();
        Fragment _man = new ManFragment();
        Fragment _calendar = new CalendarFragment();
        Fragment _plus = new PlusFragment();
        Fragment _people = new PeopleFragment();

        Touch(menuButton, _menu);Touch(manButton, _man);Touch(calendarButton, _calendar);
        Touch(plusButton, _plus);Touch(peopleButton, _people);



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
    private void Touch(ImageButton butn, Fragment f){
        butn.setOnTouchListener(new View.OnTouchListener() {
            Animation clickAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_clicked);
            ObjectAnimator objectAnimatorx;
            ObjectAnimator objectAnimatory;
            float startPos = butn.getScaleX();

            @Override
            public boolean onTouch(View v, MotionEvent event) {



                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    butn.setColorFilter(0xFFD8D8D8);
                    objectAnimatorx= ObjectAnimator.ofFloat(butn, "ScaleX", startPos, 0.8f*startPos);
                    objectAnimatorx.setDuration(100);
                    objectAnimatory= ObjectAnimator.ofFloat(butn, "ScaleY", startPos, 0.8f*startPos);
                    objectAnimatory.setDuration(100);
                    objectAnimatorx.start();
                    objectAnimatory.start();
                  //  butn.startAnimation(clickAnim);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    loadFragment(f);
                    highlightButton(butn);
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

    public void loadFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, f);
        //TODO: fix bug with button highlight
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void makeButtonsClear(){
        manButton.setColorFilter(0xFFC8C8C8);
        plusButton.setColorFilter(0xFFC8C8C8);
        calendarButton.setColorFilter(0xFFC8C8C8);
        peopleButton.setColorFilter(0xFFC8C8C8);
        menuButton.setColorFilter(0xFFC8C8C8);
    }

    public void highlightButton(ImageButton btn){
        makeButtonsClear();
        btn.setColorFilter(0xFFA0A0A0);
    }


}
