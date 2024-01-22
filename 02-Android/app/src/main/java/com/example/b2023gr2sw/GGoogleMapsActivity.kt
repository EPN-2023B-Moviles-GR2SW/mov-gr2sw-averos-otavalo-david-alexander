package com.example.b2023gr2sw

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class GGoogleMapsActivity : AppCompatActivity() {

    private lateinit var mapa: GoogleMap
    var permisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
    }
    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            // with(X) => if(X != null)
            with(googleMap) {
                mapa = googleMap
                establecerConfiguracionMapa()
            }
        }
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa) {

            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    //chequeamos si los permisos estan activados
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation ==
                    PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true //  tenemos permisos
                //activamos el boton de mi ubicacion
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true // Defecto
        }

    }
    //aca vemos si los permisos estan activados
    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermiso = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                // permiso que van a checkear
                nombrePermiso
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            //recibe un arreglo de permisos
            ActivityCompat.requestPermissions(
                this, // Contexto
                arrayOf(  // Arreglo Permisos
                    nombrePermiso, nombrePermisoCoarse
                ),
                1  // Codigo de peticion de los permisos
            )
        }
    }
}