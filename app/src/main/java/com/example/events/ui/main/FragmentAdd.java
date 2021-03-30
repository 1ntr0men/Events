package com.example.events.ui.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.events.MainActivity;
import com.example.events.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import static androidx.core.content.ContextCompat.getSystemService;

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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAdd() {
        // Required empty public constructor
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
        if(context instanceof FragmentTransfer)
            myContext = context;                        //сохраняем контекст для передачи в transfer
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        okButton = view.findViewById(R.id.OkButton);
        nameEdit = view.findViewById(R.id.NameEdit);

        //код для фокусирования на nameEdit
        nameEdit.clearFocus();
        nameEdit.requestFocus();

        //код для открытия клавиатуры
        InputMethodManager inputMethodManager = (InputMethodManager) myContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    transfer = (FragmentTransfer)myContext;
                    transfer.returnBack();                       //выполняем метод, реализованный в MainActivity
            }
        });
        return view;
    }

    public interface FragmentTransfer{  //интерфейс для связи с MainActivity
        public void returnBack();
    }

}