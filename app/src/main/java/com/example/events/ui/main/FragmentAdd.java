package com.example.events.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.events.R;
import com.example.events.models.Event;
import com.example.events.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAdd extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton okButton;
    private FragmentTransfer transfer;
    private Context myContext;
    private EditText nameEdit;
    private EditText tagEdit;
    private EditText descriptionEdit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mDatabase;

    public FragmentAdd() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAdd.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAdd newInstance(String param1, String param2) {
        FragmentAdd fragment = new FragmentAdd();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentTransfer)
            myContext = context;                        //сохраняем контекст для передачи в transfer
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        okButton = view.findViewById(R.id.OkButton);
        nameEdit = view.findViewById(R.id.editName);
        tagEdit = view.findViewById(R.id.editTag);
        descriptionEdit = view.findViewById(R.id.editDescription);

        //код для фокусирования на nameEdit
        nameEdit.clearFocus();
        nameEdit.requestFocus();

        //код для открытия клавиатуры
        InputMethodManager inputMethodManager = (InputMethodManager) myContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String tag = tagEdit.getText().toString();
                String description = descriptionEdit.getText().toString();

                transfer = (FragmentTransfer) myContext;
                transfer.returnBack();  //выполняем метод, реализованный в MainActivity
                submitEvent(name, tag, description);
            }
        });
        return view;
    }

    public interface FragmentTransfer {  //интерфейс для связи с MainActivity
        public void returnBack();
    }

    private void submitEvent(String name, String tag, String description) {

        // Возможны ошибки! именно тут
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            // User is null, error out
                            Log.e("FragmentAdd", "User " + userId + " is unexpectedly null");
                            Toast.makeText(getContext(),
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewEvent(userId, user.name, name, tag, description);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("FragmentAdd", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    private void writeNewEvent(String userId, String username, String title, String tag, String description) {
        Date datetime = new Date();
        String[] dt = datetime.toString().split(" ");
        String date = dt[1] + " " + dt[2] + " " + dt[5];
        String time = dt[3];

        String key = mDatabase.child("events").push().getKey();
        Event event = new Event(title, description, tag, date, time);
        Map<String, Object> eventValues = event.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/" + key, eventValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, eventValues);

        mDatabase.updateChildren(childUpdates);
    }
}