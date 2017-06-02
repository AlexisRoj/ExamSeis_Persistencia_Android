package com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.MainActivity;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends PreferenceFragment {


    public AccountFragment() {
        // Required empty public constructor

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.configuracion);

        String titulo = getString(R.string.nombre_app);
        String subTitulo = getString(R.string.cuenta);

        MainActivity activity = (MainActivity) getActivity();
        activity.updateView(titulo, subTitulo);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;
        view.setBackgroundColor(ContextCompat.getColor(getContext(),android.R.color.white));
        return view;

    }
}
