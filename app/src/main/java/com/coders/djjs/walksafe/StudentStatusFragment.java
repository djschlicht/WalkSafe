package com.coders.djjs.walksafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by Student on 3/25/2018.
 */

public class StudentStatusFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View mContent;
    private ImageView image;

    public static int statusToUse;

    public static Fragment newInstance(String text, int color) {
        Fragment frag = new StudentStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_COLOR, color);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the activity_student_home for this fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Requests").child("-L8R9lQ9W1sNztGVCMxy");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("Firebase", "Value is: " + dataSnapshot);
                // get information about the user
                if(dataSnapshot.getValue() == null) {
                    statusToUse = 0;
                    return;
                }
                Map<String, Object> userMap = (Map<String, Object>) dataSnapshot.getValue();

                boolean accepted  = (boolean) userMap.get("accepted");
                if(accepted) {
                    statusToUse = R.layout.fragment_student_status_coming;
                }
                else {
                    statusToUse = R.layout.fragment_student_status_waiting;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });

        // Inflate the activity_student_home for this fragment
        //System.out.println("The statusToUse is " + statusToUse);
        //System.out.println("coming is " + R.layout.fragment_student_status_coming);
        //System.out.println("waiting is " + R.layout.fragment_student_status_waiting);
        if(statusToUse == 0) {
            return inflater.inflate(R.layout.fragment_student_status_no_requests, container, false);
        }
        else {
            return inflater.inflate(statusToUse, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mText = args.getString(ARG_TEXT);
            mColor = args.getInt(ARG_COLOR);
        } else {
            mText = savedInstanceState.getString(ARG_TEXT);
            mColor = savedInstanceState.getInt(ARG_COLOR);
        }

        // initialize views

        /*
        Something needs to happen so either these xml files can be accessed individually
        OR
        put them all in the same file and sort through them somehow
         */

        //Walking view
        //ImageView imageView = view.findViewById(R.id.walking_view);
        //imageView.setImageResource(R.drawable.walkingiconmd);
        //mContent = view.findViewById(R.id.fragment_student_status_coming);

        if(statusToUse == R.layout.fragment_student_status_coming) {
            ImageView imageView = view.findViewById(R.id.walking_view);
            imageView.setImageResource(R.drawable.walkingiconmd);
            mContent = view.findViewById(R.id.fragment_student_status_coming);
        }
        else if(statusToUse == R.layout.fragment_student_status_waiting) {
            ImageView imageView = view.findViewById(R.id.sitting_view);
            imageView.setImageResource(R.drawable.sitting);
            mContent = view.findViewById(R.id.fragment_student_status_waiting);
        }
        else {
            //Nothing should happen as of now
        }
        //mContent = view.findViewById(R.id.fragment_student_status_no_requests);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }
}
