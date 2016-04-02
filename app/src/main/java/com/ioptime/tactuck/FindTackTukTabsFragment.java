package com.ioptime.tactuck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Umair Saeed on 3/30/2016.
 */
public class FindTackTukTabsFragment extends Fragment {
    FragmentTabHost mTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabs, container, false);

        mTabHost = (FragmentTabHost) rootView
                .findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(),
                android.R.id.tabcontent);

        Bundle b = new Bundle();
        b.putBoolean("wish", true);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Other community", null),
                FindTackFragment.class, b);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("My community", null),
                FindTackFragment.class, b);

        return rootView;
    }
}