package com.jullae.ui.tut;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.ui.loginscreen.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutFragment extends Fragment {

    private int mPage = 0;


    public TutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt("page");
        }
    }
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mPage == 1)
           view=  inflater.inflate(R.layout.fragment_tut1, container, false);
        else if (mPage == 2)
           view= inflater.inflate(R.layout.fragment_tut2, container, false);
        else view= inflater.inflate(R.layout.fragment_tut3, container, false);


        if(view.findViewById(R.id.button)!=null)
            view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
                    getActivity().finish();
                }
            });
        return view;
    }


}
