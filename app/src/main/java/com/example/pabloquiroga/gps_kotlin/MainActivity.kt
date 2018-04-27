package com.example.pabloquiroga.gps_kotlin

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.media.browse.MediaBrowser
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.example.pabloquiroga.gps_kotlin.R.*
import com.google.android.gms.common.ConnectionResult
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.view.MenuItem


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var location: Location ?= null
    var apiClient: GoogleApiClient ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build()

    }

    override fun onConnected(p0: Bundle?) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("permisos", "OK")
                //return
            }
        }
        Log.e("mensaje", "hasta aca llegue")
        location = LocationServices.FusedLocationApi.getLastLocation(apiClient)
        if(location != null){
            Log.e("llego",""+location.toString())
        }else{
            Log.e("no llego",""+location.toString())
        }
        //getData()
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getData(){
        if(location?.provider != null) {
            Log.e("llego",""+location.toString())
            Log.e("latitud", ""+location?.latitude)
            lbl_lat.text = location?.latitude.toString()
        }else{
            Log.e("no llego", ""+location.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        apiClient?.connect()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == R.id.obtener) {
            getData()
        }

        return super.onOptionsItemSelected(item)
    }
}
