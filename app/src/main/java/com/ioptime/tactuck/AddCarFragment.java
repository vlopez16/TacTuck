package com.ioptime.tactuck;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Umair Saeed on 3/29/2016.
 */
public class AddCarFragment extends Fragment {
    TextView colorTV;
    Button SaveBtn;
    EditText plateNoET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_car, container,
                false);
        MainActivity.titleTV.setText("Add Car");
        colorTV = (TextView) rootView.findViewById(R.id.colorTV);
        SaveBtn = (Button) rootView.findViewById(R.id.SaveBtn);
        plateNoET = (EditText) rootView.findViewById(R.id.plateNoET);
        colorTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorsList();
            }
        });
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern mPattern = Pattern.compile("[a-zA-Z]{3}\\d{3}");

                Matcher matcher = mPattern.matcher(plateNoET.getText().toString());
                if (!matcher.find()) {
                    plateNoET.setError("Invalid Plate Number");
                } else {
                    Toast.makeText(getActivity(), "Valid Plate Number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return rootView;
    }

    public void colorsList() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.color_list);
        final ListView listView = (ListView) dialog.findViewById(R.id.colorsLV);
        String[] supportArray = getResources().getStringArray(R.array.colors);
        dialog.show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, supportArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView
                        .getItemAtPosition(position);
                colorTV.setText(itemValue + "");

                dialog.cancel();

            }

        });
    }
}
