package org.tobap.com.locationmanager

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity(), LocationListener{
    override fun onLocationChanged(location: Location?) {
        // 위치 정보 (위도 , 경도)
        println(location!!.latitude)
        println(location!!.longitude)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // 연결 변경됬을때
    }

    override fun onProviderEnabled(provider: String?) {
        // 연결 됬을때
    }

    override fun onProviderDisabled(provider: String?) {
        // 연결이 끊겼음
    }

    var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        }
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun getLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100f, this)
            }
            else
            {
                locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 100f,this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100){
            getLocation()
        }
    }
}
