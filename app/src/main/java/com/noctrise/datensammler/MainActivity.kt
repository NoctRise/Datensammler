package com.noctrise.datensammler

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener, LocationListener, Runnable {


    private var sensorsManager: SensorManager? = null
    private var sensorDelay = 1
    private lateinit var locationManager: LocationManager
    private lateinit var locationRequest: LocationRequest

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_intervall.setText(sensorDelay.toString())

        handler = Handler()

        sensorsManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 1000

    }

    override fun onResume() {
        super.onResume()


        btn_start.setOnClickListener {

            if (btn_start.text == getString(R.string.start)) {
                sensorDelay = et_intervall.text.toString().toInt()
                handler.post(this)

                btn_start.text = getString(R.string.stop)
            } else {
                sensorsManager?.unregisterListener(this)
                locationManager.removeUpdates(this)
                handler.removeCallbacks(this)
                btn_start.text = getString(R.string.start)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorsManager?.unregisterListener(this)
        locationManager.removeUpdates(this)
        handler.removeCallbacks(this)
        btn_start.text = "Start"
    }

    override fun onStop() {
        super.onStop()
        sensorsManager?.unregisterListener(this)
        locationManager.removeUpdates(this)
        handler.removeCallbacks(this)
        btn_start.text = "Start"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event!!.sensor.type) {
            Sensor.TYPE_ACCELEROMETER ->
                tv_accelerometer.text =
                    getString(R.string.accelerometer) + "x: ${event.values[0]}m/s2 y: ${event.values[1]}m/s2 z: ${event.values[1]}m/s2"
            Sensor.TYPE_GRAVITY ->
                tv_gravity.text =
                    getString(R.string.gravity) + "x: ${event.values[0]}m/s2 y: ${event.values[1]}m/s2 z: ${event.values[1]}m/s2"
            Sensor.TYPE_GYROSCOPE ->
                tv_gyroscope.text =
                    getString(R.string.gyroscope) + "x: ${event.values[0]}rad/s y: ${event.values[1]}rad/s z: ${event.values[1]}rad/s"
            Sensor.TYPE_LINEAR_ACCELERATION ->
                tv_linear_acceleration.text =
                    getString(R.string.linear_acceleration) + "x: ${event.values[0]}m/s2 y: ${event.values[1]}m/s2 z: ${event.values[1]}m/s2"
            Sensor.TYPE_STEP_COUNTER ->
                tv_step_counter.text =
                    getString(R.string.step_counter) + "x: ${event.values[0]}Steps"
            Sensor.TYPE_MAGNETIC_FIELD ->
                tv_magnetic_field.text =
                    getString(R.string.magnetic_field) + "x: ${event.values[0]}μT y: ${event.values[1]}μT z: ${event.values[1]}μT"
            Sensor.TYPE_PROXIMITY ->
                tv_proximity.text = getString(R.string.proximity) + "x: ${event.values[0]}cm"
            Sensor.TYPE_AMBIENT_TEMPERATURE ->
                tv_ambient_temperature.text =
                    getString(R.string.ambient_temperature) + "x: ${event.values[0]}°C"
            Sensor.TYPE_LIGHT ->
                tv_light.text = getString(R.string.light) + "x: ${event.values[0]}lx"
            Sensor.TYPE_PRESSURE ->
                tv_pressure.text = getString(R.string.pressure) + "x: ${event.values[0]}mbar"
            Sensor.TYPE_RELATIVE_HUMIDITY ->
                tv_relative_humidity.text =
                    getString(R.string.relative_humidity) + "x: ${event.values[0]}%"

        }

    }


    private fun registerSensors() {

        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_GRAVITY),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_LIGHT),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_PRESSURE),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorsManager?.registerListener(
            this,
            sensorsManager?.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    //GPS Methods
    override fun onLocationChanged(p0: Location?) {

        tv_gps.text = "Latitude: ${p0?.latitude.toString()} Longitude: ${p0?.longitude}"
        locationManager.removeUpdates(this)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }

    override fun run() {

        registerSensors()

        // Pruefe Berechtigung und lese GPS Daten
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0F,
                this
            )



        handler.postDelayed(this, (sensorDelay * 1000).toLong())

    }


}
