package com.sid.demoapp.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sid.demoapp.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TypeWriterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TypeWriterFragment extends Fragment {

    private EditText edtEnterText;
    private TypeWriter txtTypeWriter;

    public TypeWriterFragment() {
        // Required empty public constructor
    }

    public static TypeWriterFragment newInstance() {
        TypeWriterFragment fragment = new TypeWriterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_type_writer, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        final Button btnSubmit = (Button) rootView.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(v -> {
            animateText();
        });
        edtEnterText = (EditText) rootView.findViewById(R.id.edit_enter_text);
        txtTypeWriter = (TypeWriter) rootView.findViewById(R.id.text_type_writer);
    }

    private void animateText() {
        final String msgToAnimate = edtEnterText.getText().toString().trim();
        if (msgToAnimate.isEmpty()) return;

        txtTypeWriter.animateText(msgToAnimate);
    }
}
