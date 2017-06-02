package com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.MainActivity;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de vehiculo
 * Crea el recycleView
 * Created by alexi on 09/01/2017.
 */

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder> {



    public interface OnItemClickListener {
        /** Interface que espera el evento precionar*/
        void onItemClick(VehiculoViewHolder holder, int position);
    }

    private OnItemClickListener listener;

    public class VehiculoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitulo, txtDescripcion;
        ImageView imgTipo;


        public VehiculoViewHolder(View itemView) {
            /** Desplieg los items del recycleView, es el adaptar de cada elemento*/
            super(itemView);

            txtTitulo = (TextView) itemView.findViewById(R.id.txt_titulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txt_descripcion);
            imgTipo = (ImageView) itemView.findViewById(R.id.img_tipo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(this, getAdapterPosition());
        }
    }

    private List<Vehiculo> vehiculo;
    private Activity activity;

    public VehiculoAdapter(Activity activity, List<Vehiculo> vehiculo, OnItemClickListener listener) {
        /** Constructor*/
        this.activity = activity;
        this.vehiculo = vehiculo;
        this.listener = listener;
    }


    public List<Vehiculo> getVehiculo() {
        return vehiculo;
    }

    @Override
    public VehiculoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /** Instancia el cardView, utiliza el fragment template*/
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehiculo_item_template, parent, false);

        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VehiculoViewHolder holder, int position) {

        /** Cambia los Colores y las imagenes de los cardview*/

        holder.txtTitulo.setText(getVehiculo().get(position).getNombre());
        holder.txtDescripcion.setText(getVehiculo().get(position).getDescripcion());
        CardView item = (CardView) holder.itemView.findViewById(R.id.list_item);

        switch (getVehiculo().get(position).getTipo()) {
            case SEDAN:
                item.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.colorSedan));
                holder.imgTipo.setImageResource(R.drawable.sedan);
                break;
            case PICKUP:
                item.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.colorPickup));
                holder.imgTipo.setImageResource(R.drawable.pickup);
                break;
            case OFFROAD:
                item.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.colorOffroad));
                holder.imgTipo.setImageResource(R.drawable.offroad);
                break;
            case COUPE:
                item.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.colorCoupe));
                holder.imgTipo.setImageResource(R.drawable.coupe);
                break;
        }

    }

    @Override
    /** Cantidad de elementos del RecycleView*/
    public int getItemCount() {
        return vehiculo.size();
    }


}
