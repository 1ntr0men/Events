package com.example.events;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import com.example.events.models.Event;
import com.example.events.ui.main.FragmentAdd;
import com.example.events.ui.main.SignInFragment;
import com.example.events.ui.main.MenuFragment;
import com.example.events.ui.main.GroupFragment;
import com.example.events.ui.main.PlusFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FragmentAdd.FragmentTransfer {

    public ImageButton manButton;
    public ImageButton plusButton;
    public ImageButton calendarButton;
    public ImageButton signInButton;
    public ImageButton menuButton;
    private TextView openText;
    public FloatingActionButton addButton;
    private boolean ISADDFRAGMENTOPENED=false;
    private FragmentManager fragmentManager;
    private CalendarFragment _calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ActivitySecond();
        setContentView(R.layout.activity_second);

        manButton = findViewById(R.id.Man_button);
        plusButton = findViewById(R.id.Plus_button);
        calendarButton = findViewById(R.id.Calendar_button);
        signInButton = findViewById(R.id.People_button);
        menuButton = findViewById(R.id.Menu_button);
        openText = findViewById(R.id.textView3);
        addButton = findViewById(R.id.fabAdd);


        Fragment _menu = new MenuFragment();
        Fragment _man = new SignInFragment();

        _calendar = new CalendarFragment();
        Fragment _plus = new PlusFragment();
        Fragment _people = new GroupFragment();


        Touch(menuButton, _menu);Touch(manButton, _man);Touch(calendarButton, _calendar);
        Touch(plusButton, _plus);Touch(signInButton, _people);

        addButtonClick();


//          <!--Код для viewpager(на будущее)--!>
//
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


    }
    private void Touch(ImageButton butn, Fragment f){
        butn.setOnTouchListener(new View.OnTouchListener() {
            ObjectAnimator objectAnimatorx;                 //анимации для кнопок по горизонтали
            ObjectAnimator objectAnimatory;                 //анимации для кнопок по веритикали
            float startPos = butn.getScaleX();

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if( butn == plusButton || butn == calendarButton){  // появление кнопки добавления ивента в необходимых фрагментах
                    if(addButton.getVisibility() != View.VISIBLE)   //избегание дрожания анимаций
                        setAddButtonVisibility(View.VISIBLE);
                }
                else{
                    if(!ISADDFRAGMENTOPENED)    //убираем ненужные анимации
                        setAddButtonVisibility(View.GONE);
                }

                openText.setText("");                       //очищение начального текста для красоты анимаций перехода

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    butn.setColorFilter(0xFFD8D8D8);
                    objectAnimatorx= ObjectAnimator.ofFloat(butn, "ScaleX", startPos, 0.8f*startPos);
                    objectAnimatorx.setDuration(100);
                    objectAnimatory= ObjectAnimator.ofFloat(butn, "ScaleY", startPos, 0.8f*startPos);
                    objectAnimatory.setDuration(100);
                    objectAnimatorx.start();
                    objectAnimatory.start();
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    loadFragment(f);
                    highlightButton(butn);      // выделение выбранной кнопки и очищение выделения остальных
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
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.mainLayout, f);
        //TODO: fix bug with button highlight
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if(_calendar.getEventAdapter()!=null) {
            _calendar.getEventAdapter().notifyDataSetChanged();
        }
    }

    private void makeButtonsClear(){
        manButton.setColorFilter(0xFFC8C8C8);
        plusButton.setColorFilter(0xFFC8C8C8);
        calendarButton.setColorFilter(0xFFC8C8C8);
        signInButton.setColorFilter(0xFFC8C8C8);
        menuButton.setColorFilter(0xFFC8C8C8);
    }

    public void highlightButton(ImageButton btn){
        makeButtonsClear();
        btn.setColorFilter(0xFFA0A0A0);
    }

    private void addButtonClick(){

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // _calendar.createEvent(new Event("20", "Покушать","пример 'ивента'","#tmp","20.07.21","07:00"));
                ISADDFRAGMENTOPENED = true; //отслеживание открытия фрагмента
                setAddButtonVisibility(View.GONE);
                Fragment addFragment = new FragmentAdd();
                Slide slideTransition = new Slide();
                slideTransition.setSlideEdge(Gravity.BOTTOM);
                slideTransition.setDuration(100);
                addFragment.setEnterTransition(slideTransition);
                //метод loadFragment не использован, тк более тонкая настройка транзакции
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, addFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    private boolean curstate=false;
                    @Override
                    public void onBackStackChanged() { //проверка изменения back stack для правильного отображения кнопки добавления ивента
                        if(curstate == true){
                            setAddButtonVisibility(View.VISIBLE);
                            ISADDFRAGMENTOPENED = false;
                            curstate = false;
                        }
                        else{
                            setAddButtonVisibility(View.GONE);
                            curstate = true;
                        }
                    }
                });
            }
        });
    }

    private void setAddButtonVisibility(int visibility){    //метод для тонкой настройки включения/выключения кнопки добавления ивентов
        if(visibility == View.VISIBLE){                     //анимация появления
            addButton.setVisibility(View.VISIBLE);
            addButton.setAlpha(0f);
            addButton.animate()
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    })
                    .alpha(1f)
                    .start();
        }
        else {                                              //анимация затухания
            addButton.setVisibility(View.VISIBLE);
            addButton.setAlpha(1f);
            addButton.animate()
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            addButton.setVisibility(View.GONE);
                            super.onAnimationEnd(animation);
                        }
                    })
                    .alpha(0f)
                    .start();
        }

    }

    @Override
    public void returnBack() {      //имплементированный метод из FragmentAdd для реализации кнопки ок
        loadFragment(_calendar);
        setAddButtonVisibility(View.VISIBLE);
    }

    @Override
    public Fragment getCalendar() {
        return _calendar;
    }

    @Override
    public void addEvent(Event ev) {
        _calendar.createEvent(ev);
    }
}
