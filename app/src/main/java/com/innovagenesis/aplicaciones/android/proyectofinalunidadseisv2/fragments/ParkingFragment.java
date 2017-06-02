package com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.fragments;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.MainActivity;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.R;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.adapters.Vehiculo;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.adapters.VehiculoAdapter;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.dialogo.DialogoAgregarVehiculo;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.preference.ServicioVehiculos;

import java.io.IOException;

import static com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.R.id.fab;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParkingFragment extends Fragment {

    private VehiculoAdapter adapter;

    public ParkingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_parking, container, false);
        /**
         * Cambia los titulos del toolbar
         * */

        String titulo = getString(R.string.nombre_app);
        String subTitulo = getString(R.string.parking);

        MainActivity activity = (MainActivity) getActivity();

        activity.updateView(titulo, subTitulo);

        crearRecycleView(view); /**Crea el reccycleView*/

        return view;
    }


    public void crearRecycleView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        try {
            adapter = new VehiculoAdapter(getActivity(), ServicioVehiculos.getInstance(getActivity()).cargarDatos(),
                    new VehiculoAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(VehiculoAdapter.VehiculoViewHolder holder, int position) {
                            confirmacion(position);
                        }
                    });
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error al cargar el archivo", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(getContext(), "Error al cargar la lista", Toast.LENGTH_SHORT).show();
        }

        /** Agraga el adapter(cardView) al Recycleview*/

        if (adapter != null) {
            adapter.notifyDataSetChanged();
            /** Actualiza el recycleView*/
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.clearOnScrollListeners();
        }
    }

    public void confirmacion(final int position) {
        /**
         * Metodo encargardo de eliminar elementos
         * */
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("¿Está seguro de que desea eliminar el elemento?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ServicioVehiculos.getInstance(getContext()).eliminar(position);
                        } catch (IOException e) {
                            Toast.makeText(getContext(), "Error al actualizar el archivo", Toast.LENGTH_SHORT).show();
                        } catch (ClassNotFoundException e) {
                            Toast.makeText(getContext(), "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

}
