package com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.dialogo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.R;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.adapters.Vehiculo;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.fragments.ParkingFragment;

/**
 * Clase encargada de administrar el dialogo de captura de datos
 * Created by alexi on 10/01/2017.
 */

public class DialogoAgregarVehiculo extends DialogFragment {

    public static final String TAG = "dialogo_agregar_vehiculos";

    public interface OnAgregarVehiculoListener {
        void onAgregarVehiculo(Vehiculo vehiculo);
    }

    private OnAgregarVehiculoListener listener;

    /**
     * Capturan los datos del edit text
     */
    private TextInputLayout editNoMatricula, editIdCliente;
    private Spinner spnTiposVehiculos;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_agregar_vehiculo, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Agregar Vehiculo");
        builder.setView(view);

        editNoMatricula = (TextInputLayout) view.findViewById(R.id.edit_no_matricula);
        editIdCliente = (TextInputLayout) view.findViewById(R.id.edit_id_cliente);

        spnTiposVehiculos = (Spinner) view.findViewById(R.id.spn_tipos_vehiculos);
        Button btnAgregar = (Button) view.findViewById(R.id.btn_agregar);
        Button btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Campos de captura de datos */

                EditText editTextNoMatricula = editNoMatricula.getEditText();
                String noMatricula = null;

                EditText editTextIdClientes = editIdCliente.getEditText();
                String iDCliente = null;

                /** Validacion de no vacios **/
                if (editTextNoMatricula != null && editTextNoMatricula.getText() != null) {
                    noMatricula = editTextNoMatricula.getText().toString();
                }

                if (editTextIdClientes != null && editTextIdClientes.getText() != null) {
                    iDCliente = editTextIdClientes.getText().toString();
                }

                /** Si existe un vacio no guarda los datos*/
                Boolean validaAgregarVehiculo = true;

                if ("".equals(noMatricula)){
                    validaAgregarVehiculo = false;
                    editNoMatricula.setError(getString(R.string.emptyText));
                }

                if ("".equals(iDCliente)){
                    validaAgregarVehiculo = false;
                    editIdCliente.setError(getString(R.string.emptyText));
                }

                if (validaAgregarVehiculo) {

                    /** Agrega el nuevo vehiculo*/

                    agregarVehiculo(noMatricula, iDCliente);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void agregarVehiculo(String noMatricula, String iDCliente) {
        /**
         *  Metodo encargado de agregar los vehiculos
         * */

        Vehiculo.Tipo tipo = Vehiculo.Tipo.SEDAN;
        switch (spnTiposVehiculos.getSelectedItemPosition()) {
            case 0:
                tipo = Vehiculo.Tipo.SEDAN;
                break;
            case 1:
                tipo = Vehiculo.Tipo.PICKUP;
                break;
            case 2:
                tipo = Vehiculo.Tipo.OFFROAD;
                break;
            case 3:
                tipo = Vehiculo.Tipo.COUPE;
                break;
        }

        /** Carga vehiculo*/
        Vehiculo vehiculo = new Vehiculo(iDCliente,
                noMatricula, tipo);

        listener.onAgregarVehiculo(vehiculo);

        /**
         * Necesario para refrescar el recycleView
         * despues de agregar un nuevo elemento
         * **/
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main,new ParkingFragment())
                .commit();

        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnAgregarVehiculoListener) getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException("La activity no implementa la interfaz OnAgregarVehiculoListener\n" + e);
        }
    }


}
