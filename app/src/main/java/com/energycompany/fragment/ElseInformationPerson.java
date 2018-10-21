package com.energycompany.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.energycompany.R;


public class ElseInformationPerson extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView =
                inflater.inflate(R.layout.elseenformationeerson, container, false);
        return rootView;

    }
}