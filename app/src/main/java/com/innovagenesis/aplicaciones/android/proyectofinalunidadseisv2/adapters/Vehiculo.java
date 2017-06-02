package com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.adapters;

import java.io.Serializable;

/**
 * Clase encargada de atrapar los valores
 * Created by alexi on 09/01/2017.
 */
public class Vehiculo implements Serializable{




    public enum Tipo {
        SEDAN, PICKUP, OFFROAD, COUPE
    }

    private String nombre;
    private String descripcion;
    private Tipo tipo;
    private String tipo2;

    public Vehiculo(String nombre, String descripcion, Tipo tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Vehiculo(String nombre, String descripcion, String tipo2) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo2 = tipo2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }
}
