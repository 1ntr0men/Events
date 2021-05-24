package com.example.events;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.events.models.Event;
import com.example.events.models.User;
import com.example.events.ui.main.EventAdapter;
import com.example.events.ui.main.FragmentAdd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    View v;
    ListView listView;
    ConstraintLayout event;
    RecyclerView recyclerView;
    ArrayList<Event> arr = new ArrayList<>();
    EventAdapter eventAdapter;
    String url = "https://calendarband-41b42-default-rtdb.europe-west1.firebasedatabase.app/";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance(url).getReference();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child("events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Event event = snapshot.getValue(Event.class);
                Log.i("User ID = ", FirebaseAuth.getInstance().getCurrentUser().getUid());
                Log.i("even ID = ", event.getOwner_id());
                if(((String) (event.getOwner_id())).equals((String) FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Log.i("even ID match = ", "true");
                    arr.add(event);
                    eventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                if(event.getOwner_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    Log.i("LLLLLLLL", snapshot.getKey());
                    for (int i = 0; i < arr.size(); i++)
                        if (arr.get(i).getId().equals(event.getId()))
                            arr.remove(i);
                    eventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    public ArrayList<Event> getArr() {
        return arr;
    }

    public void setArr(ArrayList<Event> arr) {
        this.arr = arr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        //       listView = view.findViewById(R.id.eventList);
        //       event = view.findViewById(R.id.typicalEvent);
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //arr.add(new Event("20", "Покушать","пример 'ивента'","#tmp","20.07.21","07:00"));
        eventAdapter = new EventAdapter(arr, view.getContext(), this);
        recyclerView.setAdapter(eventAdapter);
        // eventAdapter.addEvent(new Event("20", "Покушать","пример 'ивента'","#tmp","20.07.21","07:00"));
        // ArrayAdapter<ConstraintLayout> adapter;
        //listView.addView(view.findViewById(R.id.typicalEvent));
        v = view;
        return view;
    }

    public EventAdapter getEventAdapter() {
        return eventAdapter;
    }

    public void createEvent(Event ev) {
        arr.add(ev);
        eventAdapter.notifyDataSetChanged();
    }

    public void deleteEvent(int position) {
        String idd = arr.get(position).getId();
        Log.i("QQQQQQQQQQQQQQQ", idd);
        arr.remove(position);
        eventAdapter.notifyDataSetChanged();
        mDatabase.child("events").child(idd).removeValue();
    }
}
