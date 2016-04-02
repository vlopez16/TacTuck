package com.ioptime.tactuck;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Umair Saeed on 3/29/2016.
 */
public class EditProfileFragment extends Fragment {
    Button addCarET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_profile, container,
                false);
        addCarET = (Button) rootView.findViewById(R.id.addCarET);
        addCarET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCarFragment fragment = new AddCarFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }
}

