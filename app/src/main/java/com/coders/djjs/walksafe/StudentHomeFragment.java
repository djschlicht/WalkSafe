package com.coders.djjs.walksafe;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link StudentHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentHomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

    //private OnFragmentInteractionListener mListener;
    private static String email;

    public StudentHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentHomeFragment newInstance(String param1, String param2) {
        StudentHomeFragment fragment = new StudentHomeFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        email = param2;
        return fragment;
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);

        Button b = (Button) view.findViewById(R.id.button3);
        b.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {

        // get information about the person
        String name = "Sarah";

        // get last known location
        Double lat = 38.033706;
        Double lon = -78.509856;

        // send message in an SMS

        String txt = "WalkSafe (Student): Sarah is requesting help. http://maps.google.com/?q=" + lat + "," + lon;
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("(650) 555-1212", null, txt, null, null);

        //store information on Firebase as a request object

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference().child("Requests");

        //String requestID = myRef.push().getKey();

        Map<String, Object> userData = new HashMap<>();
        userData.put("hair", "Blonde");
        userData.put("eye", "Green");
        userData.put("height", "5ft,2in");
        userData.put("firstName", "Sarah");
        userData.put("lastInitial", "S");
        userData.put("lat", lat);
        userData.put("lon", lon);
        userData.put("requestor", "ss4nd@virginia.edu");
        userData.put("requested", "jy2xj@virginia.edu");
        userData.put("accepted", false);
        //myRef.child(requestID).setValue(userData);
        myRef.child("-L8R9lQ9W1sNztGVCMxy").setValue(userData);
    }

}
