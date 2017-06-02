package com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.dialogo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.R;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.adapters.Vehiculo;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.fragments.ParkingFragment;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.preference.ServicioVehiculos;

import java.io.IOException;

/**
 * Clase encargada de administrar el menu de exportar datos
 * <p>
 * Created by alexi on 14/01/2017.
 */

public class DialogoExportarDatos extends DialogFragment {

    public static final String TAG = "dialogo_expotar_datos";




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialogo_exportar_registros, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Exportar datos");
        builder.setView(view);

        final RadioButton radioLimpiar = (RadioButton) view.findViewById(R.id.radioLimpiarReg);
        final RadioButton radioExport = (RadioButton) view.findViewById(R.id.radioExportarReg);

        Button btnAceptar = (Button) view.findViewById(R.id.btnExpOk);
        Button btnCancelar = (Button) view.findViewById(R.id.btnExpCancel);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Valida la opcion selecionada*/

                ServicioVehiculos servicioVehiculos;

                if (radioLimpiar.isChecked()){
                    /** Exporta y limpia pantalla. elimina el archivo*/
                    try {

                        servicioVehiculos
                                = ServicioVehiculos.getInstance(getContext());

                        servicioVehiculos.mExportDatos();
                        servicioVehiculos.eliminarRegistros();

                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }

                if (radioExport.isChecked()){
                    try {
                        servicioVehiculos
                                = ServicioVehiculos.getInstance(getContext());

                        servicioVehiculos.mExportDatos();

                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }


                dismiss();
                /**
                 * Necesario para refrescar el recycleView
                 * cuando se limpian los elementos
                 * **/
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main,new ParkingFragment())
                        .commit();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Cancela el menu pop pup*/
                dismiss();
            }
        });

        return builder.create();

    }




}
