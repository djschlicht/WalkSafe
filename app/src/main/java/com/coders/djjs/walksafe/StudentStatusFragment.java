package com.coders.djjs.walksafe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        return inflater.inflate(R.layout.fragment_student_status_no_requests, container, false);
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

        //Waiting view
        //ImageView imageView = view.findViewById(R.id.sitting_view);
        //imageView.setImageResource(R.drawable.sitting);
        //mContent = view.findViewById(R.id.fragment_student_status_waiting);

        //mContent = view.findViewById(R.id.fragment_student_status_no_requests);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }
}
