package ale.kod.flighttracker.activities

import ale.kod.flighttracker.R
import ale.kod.flighttracker.adapter.MarkerInfoAdapter
import ale.kod.flighttracker.dto.routes.FlightRouteInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.GsonBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    var coordinatesOfPlanes = arrayListOf<LatLng>()
    var aeroplaneInfo = arrayListOf<String>()
    var snippedInfo = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_activity)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val jsonList = intent.getStringExtra("list")
        val gson = GsonBuilder().setLenient().create()
        val flights: FlightRouteInfo = gson.fromJson(jsonList, FlightRouteInfo::class.java)

        for(i in flights){
            coordinatesOfPlanes.add(LatLng(i.flightInfoItem.geography.latitude!!,i.flightInfoItem.geography.longitude!!))
            aeroplaneInfo.add(
                "Flight nr: ${i.flightInfoItem.flight.number}"
            )
            snippedInfo.add(
                "Model: ${i.flightInfoItem.aircraft.iataCode}\n"+
                        "Arrival time: ${i.flightModel.arrivalTime}\n"
                        +"Departure time: ${i.flightModel.departureTime}"
            )
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //create marker for every flight in loop
        for (i in 0..aeroplaneInfo.size - 1) {
            mMap.addMarker(
                MarkerOptions()
                    .position(coordinatesOfPlanes[i])
                    .title(aeroplaneInfo[i]).visible(true)
                    .snippet(snippedInfo[i])
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.aeroplane)))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinatesOfPlanes.get(i)))
        }

        var adapter = MarkerInfoAdapter(this)
        mMap.setInfoWindowAdapter(adapter)
        setMapProperties()
    }

    fun setMapProperties(){
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        var center = CameraUpdateFactory.newLatLng(LatLng(coordinatesOfPlanes[0].latitude,coordinatesOfPlanes[0].longitude)) as CameraUpdate
        var zoom = CameraUpdateFactory.zoomTo(4f)
        mMap.moveCamera(center)
        mMap.animateCamera(zoom)
    }

}