package com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.adapters.Vehiculo;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.dialogo.DialogoAgregarVehiculo;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.dialogo.DialogoExportarDatos;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.fragments.AccountFragment;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.fragments.ParkingFragment;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.preference.PreferenceConstant;
import com.innovagenesis.aplicaciones.android.proyectofinalunidadseisv2.preference.ServicioVehiculos;
import java.io.IOException;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DialogoAgregarVehiculo.OnAgregarVehiculoListener, DrawerLayout.DrawerListener{


    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Fragment fragment;

    FloatingActionButton fab;


    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        int escribir = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int leer = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (escribir != PackageManager.PERMISSION_GRANTED || leer != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISOS, REQUEST_CODE);
        }



        /**
         * Instancia pantalla incial
         * */

        fragment = new ParkingFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_main, fragment)
                .commit();

        /** Se instancia el toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Se instancian el drawer y el navigation*/

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        /** Elemento selecionado del drawer*/

        navigationView.getMenu().getItem(0).setChecked(true);


        /**
         * Boton Flotante
         * */

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Ejecuta el dialogo que guarda el elemento */

                DialogoAgregarVehiculo dialogo = new DialogoAgregarVehiculo();
                dialogo.show(getSupportFragmentManager(), DialogoAgregarVehiculo.TAG);

                Snackbar.make(view, "Guardando elemento....", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void updateView(String title, String subTitle) {
        /** Actualiza el titulo y subtitulo del toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setSubtitle(subTitle);
        }

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawerLayout.addDrawerListener(toggle);
        drawerLayout.addDrawerListener(this);

        toggle.syncState();
        /**
         *  Muestra y oculta el boton flotante
         * */

        if (!subTitle.equals(getString(R.string.cuenta))) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exportar_registros) {
            DialogoExportarDatos dialogo = new DialogoExportarDatos();

            dialogo.show(getSupportFragmentManager(), DialogoExportarDatos.TAG);
        }
        if (id == R.id.logout) {

           borrarPreference();

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /**
         * Ejecuta las opciones del menu del Drawer
         * */
        int id = item.getItemId();

        switch (id) {
            case R.id.parking: {
                /** Carga parking*/
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new ParkingFragment())
                        .commit();
                break;
            }
            case R.id.account: {
                /** Carga las configuraciones*/
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new AccountFragment())
                        .commit();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onAgregarVehiculo(Vehiculo vehiculo) {
        /**
         * Agrega el vehiculo
         * */
        try {
            ServicioVehiculos.getInstance(MainActivity.this).guardarVehiculo(vehiculo);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error al actualizar el archivo", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(MainActivity.this, "Error al guardar elemento en la lista", Toast.LENGTH_SHORT).show();
        }
    }


    public void borrarPreference() {

        SharedPreferences pref = getSharedPreferences(PreferenceConstant.PREFERENCE_LOGIN, MODE_PRIVATE);

        /** Borra las preferencias */
        SharedPreferences.Editor edit = pref.edit();
        edit.remove(PreferenceConstant.PREF_KEY_USERNAME);
        edit.apply();

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }


    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {
        onChangeTitleDrawer();

    }

    private void onChangeTitleDrawer() {
        /**
         *  Cambia titulo del drawer, es necesario implementar el
         * drawerLayout.addDrawerListener(this)
         * */

        SharedPreferences configuraciones = PreferenceManager.getDefaultSharedPreferences(this);
        String nombreUsuario = configuraciones.getString("nombreUsuario", null);

        View hView  = navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.textUserDrawer);

        if (nav_user != null)
            nav_user.setText(nombreUsuario);
    }


}
