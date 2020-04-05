package ale.kod.flighttracker.activities

import FlightRecycleViewAdapter
import ale.kod.flighttracker.R
import ale.kod.flighttracker.dto.routes.Routes
import ale.kod.flighttracker.dto.flights.FlightInfo
import ale.kod.flighttracker.dto.routes.FlightRoute
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pwr.edu.flighttracking.HttpGetRequest


class ListActivity : AppCompatActivity() {
    companion object{
        val KEY = "a32eb8-8cdda7"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_list_activity)

        val jsonList = intent.getStringExtra("obj")

        val gson = GsonBuilder().setLenient().create()
        val objectList: FlightInfo = gson.fromJson(jsonList.replace("}]null","}]"), FlightInfo::class.java)
        val flightList = FlightInfo()

        for (i in objectList){
            if(i.status.trim() =="en-route")
                flightList.add(i)
        }

        val listOfRouteFlight = mutableListOf<FlightRoute>()
        val route = object : TypeToken<FlightRecycleViewAdapter>() {}.type


        flightList.forEach {

            val getRoutesURL =
                "https://aviation-edge.com/v2/public/routes?key=" + KEY + "&flightNumber=" + it.flight.number +"&airlineIata=" + it.airline.iataCode
            var getRequest = HttpGetRequest()
            var result = getRequest.execute(getRoutesURL).get()
            Log.e("LAG", result)
            if(result!="{\"error\":\"No Record Found\",\"success\":false}null"){
                val routes = gson.fromJson(result.replace("}]null","}]"), Routes::class.java)
                val route = FlightRoute(it, routes[0])
                listOfRouteFlight.add(route)
            }

        }

        val flightsRecycleView = findViewById<RecyclerView>(R.id.rvFlights)
        val adapter = FlightRecycleViewAdapter(listOfRouteFlight)
        flightsRecycleView.adapter = adapter
        flightsRecycleView.layoutManager = LinearLayoutManager(applicationContext)

        val mapButton = findViewById<Button>(R.id.mapButton)
        mapButton.setOnClickListener {
            var intent = Intent(this,  MapsActivity::class.java)
            val listAsJson = gson.toJson(listOfRouteFlight)
            intent.putExtra("list", listAsJson)
            startActivity(intent)
        }

    }
}

