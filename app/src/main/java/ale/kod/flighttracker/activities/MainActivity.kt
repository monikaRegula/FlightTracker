package ale.kod.flighttracker.activities

import ale.kod.flighttracker.JsonReader
import ale.kod.flighttracker.R
import ale.kod.flighttracker.dto.routes.Airport
import ale.kod.flighttracker.dto.city.City
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.main_activity.*
import pwr.edu.flighttracking.HttpGetRequest

class MainActivity : AppCompatActivity() {
    companion object{
        val KEY = "a32eb8-8cdda7"
    }

    lateinit var cityList: List<City>
    var airportsNames = mutableListOf<String>()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val jsonFinder = JsonReader()
        val jsonFileString = jsonFinder.getJsonDataFromAsset(applicationContext, "cities.json")
        val gson = GsonBuilder().setLenient().create()
        val listCities = object : TypeToken<List<City>>() {}.type
        cityList = gson.fromJson(jsonFileString, listCities)


        var nameList = mutableListOf<String>()
        for (i in cityList)
            nameList.add("${i.nameCity}, ${i.codeIso2Country}")


        val airportsJsonFile = jsonFinder.getJsonDataFromAsset(applicationContext, "airports.json")
        val listAirports = object : TypeToken<List<Airport>>() {}.type

        var airportsList: List<Airport> = gson.fromJson(airportsJsonFile, listAirports)

        val cityText = findViewById<AutoCompleteTextView>(R.id.autoTextView)

        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                nameList.sorted().distinct()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        cityText.setAdapter(adapter)
        cityText.onItemClickListener = object : AdapterView.OnItemClickListener {


            override fun onItemClick(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                airportsNames = mutableListOf()
                distanceText.isEnabled = true
                for( i in cityList){
                    if(autoTextView.text.toString() == ("${i.nameCity}, ${i.codeIso2Country}")){
                        Log.d("TAG",i.nameCity)
                        for (k in airportsList){
                            if (i.codeIataCity == k.codeIataCity ){
                                airportsNames.add(k.nameAirport)
                            }
                        }
                    }
                }

                var airportSpinner = findViewById<Spinner>(R.id.airportSpinner)
                airportSpinner.adapter = ArrayAdapter(airportSpinner.context, android.R.layout.simple_dropdown_item_1line, airportsNames )
                airportSpinner.setSelection(0)
            }
        }

        var searchBtn = findViewById<Button>(R.id.search)
        var distanceText = findViewById<EditText>(R.id.distanceText).text

        searchBtn.setOnClickListener {
            print( cityText.text.toString())
            if(inList(cityList,cityText.text.toString())) {
                if (distanceText.toString().toInt()>0 || distanceText.toString() != "."){

                var distanceText = findViewById<EditText>(R.id.distanceText)
                val text: String = airportSpinner.selectedItem.toString()
                var latitude: String = "0"
                var longitude: String = "0"

                for (airport in airportsList) {
                    if (airport.nameAirport == text) {
                        latitude = airport.latitudeAirport
                        longitude = airport.longitudeAirport
                        break
                    }
                }

                val getFlightsURL = "https://aviation-edge.com/v2/public/flights?key=$KEY&lat=${latitude}&lng=${longitude}&distance=${distanceText.text}&limit=20"
                var getRequest = HttpGetRequest()
                var result = getRequest.execute(getFlightsURL).get()

                if(result!="{\"error\":\"No Record Found\",\"success\":false}null") {
                   val intent = Intent(this, ListActivity::class.java)
                        Log.d("TAG",result)
                        intent.putExtra("obj", result)
                        startActivity(intent)
                }else{
                    val t = Toast.makeText(this,"There is no aircraft in this area.",Toast.LENGTH_LONG)
                    t. show()
                }

            }else{
                    val t = Toast.makeText(this,"Distance is incorrect.",Toast.LENGTH_LONG)
                    t. show()
                }

            }else{
                val t = Toast.makeText(this,"City name is incorrect.",Toast.LENGTH_LONG)
                t. show()
            }
        }
    }

    private fun inList(list:List<City>, city:String):Boolean{
        for(i in list){
            if (("${i.nameCity}, ${i.codeIso2Country}")==city) {
                return true
                break
            }
        }
        return false
    }

}
