import ale.kod.flighttracker.JsonReader
import ale.kod.flighttracker.dto.routes.Airport
import ale.kod.flighttracker.dto.routes.FlightModel
import ale.kod.flighttracker.dto.routes.FlightRoute
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class FlightRecycleViewAdapter(_fl:List<FlightRoute>) : RecyclerView.Adapter<FlightRecycleViewAdapter.ViewHolder>() {

    private var mFlights: List<FlightRoute>? = _fl
    lateinit var airportList:List<Airport>

    val jsonFinder = JsonReader()
    val gson = GsonBuilder().setLenient().create()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var departureTextView: TextView
        var arrivalTextView: TextView
        var flightTextView: TextView

        init {
            departureTextView = itemView.findViewById(ale.kod.flighttracker.R.id.departureView)
            arrivalTextView = itemView.findViewById(ale.kod.flighttracker.R.id.arrivalView)
            flightTextView = itemView.findViewById(ale.kod.flighttracker.R.id.flightView)
            var jsonFileString = jsonFinder.getJsonDataFromAsset(itemView.context, "airports.json")
            var listAirports = object : TypeToken<List<Airport>>() {}.type
            airportList = gson.fromJson(jsonFileString, listAirports)
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context: Context = parent.getContext()
        val inflater = LayoutInflater.from(context)
        val contactView: View =
            inflater.inflate(ale.kod.flighttracker.R.layout.flights_recycler_adapter, parent, false)

        // Return a new holder instance
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return this.mFlights?.size!!
    }

    override fun onBindViewHolder(holder: FlightRecycleViewAdapter.ViewHolder, position: Int) {
        val flight: FlightRoute = mFlights?.get(position)!!

        val textView: TextView = holder.departureTextView
        textView.setText(prepareDeparture(flight.flightModel))
        val textView2: TextView = holder.arrivalTextView
        textView2.setText(prepareArrival(flight.flightModel))
        val textView3: TextView = holder.flightTextView
        textView3.setText(prepareFlight(flight.flightModel))


    }


    fun prepareDeparture(route: FlightModel):String?{
        var departure = ""
        for(i in airportList){
            if(route.departureIcao == i.codeIcaoAirport){
                departure = ("DEPARTURE\n${i.nameAirport}\n${i.nameCountry}\n${route.departureTime}\nTerminal: ${route.departureTerminal}\n")
                break
            }
        }
        return departure
    }
    fun prepareArrival(route: FlightModel):String?{
        var arrival = ""
        for(i in airportList){

            if(route.arrivalIcao == i.codeIcaoAirport){
                arrival = ("ARRIVAL\n${i.nameAirport}\n${i.nameCountry}\n${route.arrivalTime}\n" +
                        "Terminal: ${route.arrivalTerminal}\n")
                break
            }
        }
        return arrival
    }
    fun prepareFlight(route: FlightModel):String?{
        return ("FLIGHT NUMBER\n${route.flightNumber}")
    }
}